package com.redstars.tdengine.api;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.handler.BeanHandler;
import cn.hutool.db.handler.RsHandler;
import com.alibaba.fastjson2.JSONObject;
import com.redstars.tdengine.core.TdengineSevice;
import com.redstars.tdengine.core.autoconfig.TdengineDataSource;
import com.redstars.tdengine.core.common.PageResult;
import com.redstars.tdengine.core.conditions.query.TdengineQueryWrapper;
import com.redstars.tdengine.core.enums.SqlTypeEnum;
import com.redstars.tdengine.core.exception.TdengineException;
import com.redstars.tdengine.core.toolkit.TdengineResultHelper;
import com.redstars.tdengine.core.toolkit.TdengineSqlHelper;
import com.redstars.tdengine.core.vo.TdengineSqlVo;
import com.taosdata.jdbc.ws.TSWSPreparedStatement;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * @author : zhouhx
 * 操作时序数据库工具类
 * @date : 2023/3/3 11:04
 */
@Slf4j
@Data
public class TdengineDb extends TdengineSevice {
    private static final long serialVersionUID = -3378415769645309514L;
    private static final String ERRFILENAME = "temporal database tdengine sql exception";
    private static final String SQLNAME = "temporal database tdengine";
    private  static int DEFAULT_BATCH_SIZE = 1000;
    public TdengineDb(){

    }

    @Override
    public Db use() throws SQLException {
        /**
         * 默认数据源
         */
        String defaltDbName = TdengineDataSource.getPrimaryDsName();

        if(ObjectUtil.isEmpty(defaltDbName)){
            throw new SQLException("未配置默认数据源");
        }

        log.debug("当前激活的时序数据源："+defaltDbName);

        DSFactory dsFactory = TdengineDataSource.getPrimaryDataSource();
        DataSource dataSource = dsFactory.getDataSource();
        dsFactory.getDataSource();
        return Db.use(dataSource);
    }

    @Override
    public  Db use(String dsName) throws SQLException {
        if(ObjectUtil.isEmpty(dsName)){
            return use();
        }
        DSFactory dsFactory = TdengineDataSource.getDataSource(dsName);
        DataSource dataSource = dsFactory.getDataSource();
        if(ObjectUtil.isEmpty(dataSource)){
            throw new SQLException(String.format("数据源:%s不存在",dsName));
        }
        log.debug("当前激活的时序数据源："+dsName);
        return Db.use(dataSource);
    }

    @Override
    public Entity getOne(String dbName, String sql, Object... params) {
        try {
            Db db = use(dbName);
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            return db.queryOne(sql, params);
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
    @Override
    public <T> T getOne(String dbName, String sql, Class<T> beanClass, Object... params) {
        try {
            Db db = use(dbName);
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            T res = (T) db.query(sql, (RsHandler) (new BeanHandler(beanClass)), params);
            return TdengineResultHelper.covertObj(res, beanClass);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
            log.error("ERROR Code: " + e.getErrorCode());
        }
        return null;
    }

    @Override
    public Number getNumber(String dbName, String sql, Object... params) {
        try {
            Db db = use(dbName);
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            return db.queryNumber(sql, params);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
        }
        return null;
    }

    @Override
    public String getString(String dbName, String sql, Object... params){
        try {
            Db db = use(dbName);
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            return db.queryString(sql, params);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
            log.error("ERROR Code: " + e.getErrorCode());
        }
        return null;
    }

    /**
     * @author zhuohx
     * @description 插入或者更新 Tdengine没有更新操作语句 会根据时间戳来更新字段
     * @parms [sql, params]
     * @return boolean
     * @throws
     * @date 2023/3/3 14:50
     */
    @Override
    public boolean insertOrUpdate(String dbName, String sql, Object... params) {
        int updateCount = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Db db = use(dbName);
            conn = db.getConnection();
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
            log.debug(SQLNAME+" influence rows:{}", updateCount);
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

    @Override
    public boolean save(String dsName, Object entity) {
        try {
            TdengineSqlVo tdengineSqlVo = TdengineSqlHelper.entityTosql(entity);
            return this.insertOrUpdate(dsName,tdengineSqlVo.getSql(),tdengineSqlVo.getColumnValeList().toArray());
        } catch (Exception e) {
            log.error("save exception",e);
        }
        return false;
    }

    @Override
    public boolean remove(String dsName, TdengineQueryWrapper queryWrapper) {

        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.DELETE, queryWrapper);
            log.debug(SQLNAME+" sql:{}", sql);
            Db db = use(dsName);
            int res = db.execute(sql);
            if(res >0){
                return true;
            }
        } catch (Exception e) {
            log.error("remove exception",e);
        }
        return false;
    }

    @Override
    public <T> T getOne(String dsName, TdengineQueryWrapper<T> queryWrapper) {
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);
            log.debug(SQLNAME+" sql:{}", sql);
            return this.getOne(dsName,sql,queryWrapper.getEntityClass());
        } catch (IllegalAccessException e) {
            log.error("getOne sql error",e);
        }
        return null;
    }

    @Override
    public Map<String, Object> getMap(String dsName, TdengineQueryWrapper<Object> queryWrapper) {
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);
            log.debug(SQLNAME+" sql:{}", sql);
            return  this.getOne(dsName, sql);
        } catch (IllegalAccessException e) {
            log.error("bulid sql error",e);
        }
        return null;
    }

    @Override
    public long count(String dsName, TdengineQueryWrapper<Object> queryWrapper) {
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);
            sql = String.format("select count(*) from (%s)a",sql);
            log.debug(SQLNAME+" sql:{}", sql);
            Number number = getNumber(dsName, sql, queryWrapper.getEntityClass());
            return number.longValue();
        } catch (Exception e) {
            log.error("count sql error",e);
        }
        return 0;
    }

    @Override
    public Number getNumber(String dsName, TdengineQueryWrapper<Object> queryWrapper) {
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);
            log.debug(SQLNAME+" sql:{}", sql);
            return getNumber(dsName, sql, queryWrapper.getEntityClass());
        } catch (Exception e) {
            log.error("getNumber sql error",e);
        }
        return 0;
    }

    @Override
    public String getString(String dbName, TdengineQueryWrapper<Object> queryWrapper) {
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);
            log.debug(SQLNAME+" sql:{}", sql);
            return getString(dbName, sql, queryWrapper.getEntityClass());
        } catch (Exception e) {
            log.error("getNumber sql error",e);
        }
        return null;
    }

    @Override
    public <T> List<T> list(String dsName, TdengineQueryWrapper<T> queryWrapper) {
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);
            log.debug(SQLNAME+" sql:{}", sql);
            return list(dsName, sql, queryWrapper.getEntityClass());
        } catch (Exception e) {
            log.error("list sql error",e);
        }
        return null;
    }

    @Override
    public List<Entity> listMaps(String dsName, TdengineQueryWrapper<Object> queryWrapper) {
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);
            log.debug(SQLNAME+" sql:{}", sql);
            return list(dsName, sql);
        } catch (Exception e) {
            log.error("list sql error",e);
        }
        return null;
    }

    @Override
    public <T> List<T> list(String dbName, String sql, Class<T> beanClass, Object... params)  {
        try {
            Db db = use(dbName);
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            List<T> res = db.query(sql, beanClass, params);
            return TdengineResultHelper.covertToList(res, beanClass);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
            log.error("ERROR Code: " + e.getErrorCode());
        }

        return null;
    }

    @Override
    public List<Entity> list(String dbName, String sql, Map<String, Object> params) {
        try {
            Db db = use(dbName);
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            return db.query(sql, params);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
            log.error("ERROR Code: " + e.getErrorCode());
        }
        return null;
    }

    @Override
    public List<Entity> list(String dbName, String sql, Object... params) {
        try {
            Db db = use(dbName);
            log.debug(SQLNAME+" sql:{},params:{}", sql, JSONObject.toJSONString(params));
            return db.query(sql, params);
        } catch (SQLException e) {
            log.error(ERRFILENAME, e);
            log.error("ERROR Code: " + e.getErrorCode());
        }
        return null;
    }

    @Override
    public <T> PageResult<T> page(String dsName, Page page, TdengineQueryWrapper<T> queryWrapper) {
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);
            log.debug(SQLNAME+" sql:{}", sql);
            return page(dsName, page, queryWrapper.getEntityClass(),sql);
        } catch (Exception e) {
            log.error("page sql error",e);
        }
        return null;
    }

    @Override
    public  <T> PageResult<T> page(String dbName, Page page, Class<T> beanClass, String sql, Object... params) {
        PageResult<T> result = new PageResult<>();

        try {
            String pageSql = TdengineSqlHelper.covertPageSql(page, sql);
            int totalSize = getTotalSize(dbName,page,sql,params);

            //查询该页数据
            List<T> list = this.list(dbName,pageSql, beanClass, params);

            return TdengineResultHelper.setPageData(page,totalSize,list);
        } catch (Exception e) {
            log.error(SQLNAME+" 分页异常", e);
        }

        return result;
    }

    @Override
    public PageResult<Entity> page(String dbName, Page page, String sql, Object... params) {
        PageResult<Entity> result = new PageResult<>();
        try {
            String pageSql = TdengineSqlHelper.covertPageSql(page, sql);
            int totalSize = getTotalSize(dbName,page,sql,params);
            //查询该页数据

            List<Entity> list = this.list(dbName,pageSql, params);
            return  TdengineResultHelper.setPageData(page, totalSize, list);
        } catch (Exception e) {
            log.error(SQLNAME+" 分页异常", e);
        }

        return result;
    }

    /**
     *
     * 获取总行数
     * @author zhuohx
     * @param  dbName 数据源名称
     * @param  page 分页参数
     * @param  sql 不带分页的sql
     * @param  params sql参数
     * @return int
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:53
     */
    private int getTotalSize(String dbName,Page page, String sql, Object... params){
        int totalSize = 0;

        //计算总条数
        int from = sql.toLowerCase().indexOf("from");

        StringBuilder countSql= new StringBuilder("select count(*) ").append(sql.substring(from));

        Number number = this.getNumber(dbName,countSql.toString(), params);
        if(ObjectUtil.isNotEmpty(number)){
            totalSize = number.intValue();
        }

        return totalSize;
    }
}
