package com.redstars.tdengine.api;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.handler.BeanHandler;
import cn.hutool.db.handler.RsHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.redstars.tdengine.core.annotation.TdengineCovert;
import com.redstars.tdengine.core.common.PageResult;
import com.redstars.tdengine.core.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author : zhouhx
 * 操作时序数据库
 * @date : 2023/3/3 11:04
 */
@Slf4j
public class TdengineDb {
    private static final long serialVersionUID = -3378415769645309514L;
    private static final String ERRFILENAME = "时序数据库tdengine sql异常";
    private static final String SQLNAME = "时序数据库tdengine";

    private static TdengineDb instance;

    private TdengineDb() {
        super();
    }


    public static TdengineDb getInstance() {
        if (instance == null) {
            instance = new TdengineDb();
        }
        return instance;
    }

    /**
     * @return com.hndk.tdengine.TdengineDb
     * @throws
     * @author zhuohx
     * @description 获取数据源
     * @parms []
     * @date 2023/3/3 11:15
     */
    public static Db use() {
        return Db.use(DSFactory.get("group_tdengine"));
    }

    /**
     * @return java.util.List<T>
     * @throws
     * @author zhuohx
     * @description 查询集合
     * @parms [sql, beanClass, params]
     * @date 2023/3/3 13:47
     */
    public <T> List<T> list(String sql, Class<T> beanClass, Object... params) {
        try {
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            List<T> res = use().query(sql, beanClass, params);
            return covertToList(res, beanClass);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
            log.error("ERROR Code: " + e.getErrorCode());
        }

        return null;
    }

    public List<Entity> list(String sql, Map<String, Object> params) {
        try {
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            return use().query(sql, params);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
            log.error("ERROR Code: " + e.getErrorCode());
        }
        return null;
    }

    public List<Entity> list(String sql, Object... params) {
        try {
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            return use().query(sql, params);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
            log.error("ERROR Code: " + e.getErrorCode());
        }
        return null;
    }

    /**
     *
     * @author zhuohx
     * @description  分页
     * @parms  [page 分页条件, beanClass, sql 不带分页的sql, params 查询条件]
     * @return com.hndk.common.page.PageResult<T>
     * @throws
     * @date 2023/3/6 9:57
     */
    public  <T> PageResult<T> page(Page page, Class<T> beanClass, String sql, Object... params) {
        int totalSize = 0;
        PageResult<T> result = new PageResult<>();

        try {
            StringBuilder order = new StringBuilder("");
            if(ArrayUtil.isNotEmpty(page.getOrders())){
                order.append(" order by ");
                for (int i = 0; i < page.getOrders().length; i++) {
                    if(i != page.getOrders().length-1){
                        order.append( page.getOrders()[i].toString()).append(" ,");
                    }else{
                        order.append( page.getOrders()[i].toString());
                    }
                }
            }

            StringBuilder pageSql = new StringBuilder(sql).append(order.toString()).append(" limit ").append(page.getPageSize()).append(" offset ").append(getStart(page));
            //计算总条数
            int from = sql.toLowerCase().indexOf("from");

            StringBuilder countSql= new StringBuilder("select count(*)  ").append(sql.substring(from));

            Number number = this.getNumber(countSql.toString(), params);
            if(ObjectUtil.isNotEmpty(number)){
                totalSize = number.intValue();
            }
            //查询该页数据
            List<T> list = this.list(pageSql.toString(), beanClass, params);

            return this.setPageData(page,totalSize,list);
        } catch (Exception e) {
            log.error(SQLNAME+" 分页异常", e);
        }

        return result;
    }
    public static int getStart(Page page) {
        int firstPageNo = 1;
        int pageSize = page.getPageSize();
        int pageNo = page.getPageNumber();
        if (pageSize < 1) {
            pageSize = 1;
        }

        return (pageNo - firstPageNo) * pageSize;
    }
    public PageResult<Entity> page(Page page, String sql, Object... params) {
        int totalSize = 0;
        PageResult<Entity> result = new PageResult<>();

        try {
            StringBuilder pageSql = new StringBuilder(sql).append(" limit ").append(page.getPageSize()).append(" offset ").append(getStart(page));
            //计算总条数
            int from = sql.toLowerCase().indexOf("from");

            StringBuilder countSql= new StringBuilder("select count(*) ").append(sql.substring(from));

            Number number = this.getNumber(countSql.toString(), params);
            if(ObjectUtil.isNotEmpty(number)){
                totalSize = number.intValue();
            }
            //查询该页数据
            List<Entity> list = this.list(pageSql.toString(), params);
            return  setPageData(page, totalSize, list);
        } catch (Exception e) {
            log.error(SQLNAME+" 分页异常", e);
        }

        return result;
    }

    /**
     *
     * @author zhuohx
     * @description 设置分页数据
     * @parms  [page, totalSize 总数, list 结果集]
     * @return com.hndk.common.page.PageResult<T>
     * @throws
     * @date 2023/3/6 10:02
     */
    private <T> PageResult<T> setPageData(Page page, int totalSize, List<T> list) {
        PageResult<T> result = new PageResult<>();
        result.setList(list);
        result.setTotal(totalSize);
        result.setPageNo(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotalPage(PageUtil.totalPage(totalSize, page.getPageSize()));
        return result;
    }

    public Entity getOne(String sql, Object... params) {
        try {
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            return use().queryOne(sql, params);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
            log.error("ERROR Code: " + e.getErrorCode());
        }
        return null;
    }

    /**
     *
     * @author zhuohx
     * @description 只取一条记录
     * @parms  [sql, beanClass, params]
     * @return T
     * @throws
     * @date 2023/3/6 9:25
     */
    public <T> T getOne(String sql, Class<T> beanClass, Object... params) {
        try {
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            T res = (T) use().query(sql, (RsHandler) (new BeanHandler(beanClass)), params);
            return covertObj(res, beanClass);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
            log.error("ERROR Code: " + e.getErrorCode());
        }
        return null;
    }

    public Number getNumber(String sql, Object... params) {
        try {
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            return use().queryNumber(sql, params);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
        }
        return null;
    }

    public String getString(String sql, Object... params) throws SQLException {
        try {
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            return use().queryString(sql, params);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
            log.error("ERROR Code: " + e.getErrorCode());
        }
        return null;
    }

    /**
     * @return boolean
     * @throws
     * @author zhuohx
     * @description 插入或者更新 Tdengine没有更新操作语句 会根据时间戳来更新字段
     * @parms [sql, params]
     * @date 2023/3/3 14:50
     */
    public boolean insertOrUpdate(String sql, Object... params) {
        int updateCount = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        //  String sql="insert into service_data_station_HN0001 using service_data_station tags('HN0001') values(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = use().getConnection();
            pstmt = conn.prepareStatement(sql);
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            int length = params.length;
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            // execute
            pstmt.execute();
            updateCount = pstmt.getUpdateCount();
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
            log.error("ERROR Code: " + e.getErrorCode());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException throwables) {
                log.error(ERRFILENAME, throwables);
                log.error("ERROR Code: " + throwables.getErrorCode());
            }
        }

        return updateCount > 0 ? true : false;
    }

    /**
     * @return java.util.List<T>
     * @throws
     * @author zhuohx
     * @description 处理Tdengine数据库中的转换，byte[]类型自动转换成String
     * @parms [t]
     * @date 2023/3/3 11:24
     */
    public <T> List<T> covertToList(List<T> t, Class<T> beanClass) {
        if (CollUtil.isNotEmpty(t)) {
            t.forEach(item -> {
                covertObj(item, beanClass);
            });
        }
        ;

        return t;
    }

    /**
     * @return T
     * @throws
     * @author zhuohx
     * @description 处理Tdengine数据库中的转换，byte[]类型自动转换成String
     * @parms [t 数据, beanClass 类]
     * @date 2023/3/3 14:21
     */
    public <T> T covertObj(T t, Class<T> beanClass) {
        if (ObjectUtil.isNotEmpty(t)) {
            Field[] fields = beanClass.getDeclaredFields();
            Arrays.stream(fields).forEach(field -> {
                TdengineCovert tdengineCovert = field.getAnnotation(TdengineCovert.class);
                if (ObjectUtil.isNotEmpty(tdengineCovert)) {
                    //将byte[]json字符串解析为string
                    String name = field.getName();
                    Class<?> itemClass = t.getClass();
                    try {
                        Field classField = itemClass.getDeclaredField(name);
                        classField.setAccessible(true);
                        Object value = classField.get(t);
                        if (ObjectUtil.isNotEmpty(value)) {
                            // 将json类型的byte[]转换成string
                            try {
                                List<Byte> bytes = JSON.parseArray(value.toString(), Byte.class);
                                String s = StrUtil.ascii2Str(bytes.toArray(new Byte[bytes.size()]));
                                classField.set(t, s);
                            } catch (Exception e) {

                            }
                        }

                    } catch (Exception e) {
                        log.error("转换字段类型异常", e);
                    }
                }
            });
        }
        return t;
    }
}
