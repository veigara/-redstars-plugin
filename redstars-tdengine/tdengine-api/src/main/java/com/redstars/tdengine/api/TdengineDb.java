package com.redstars.tdengine.api;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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
    public DataSource use() throws SQLException {
        /**
         * 默认数据源
         */
        String defaltDbName = TdengineDataSource.getPrimaryDsName();

        if(ObjectUtil.isEmpty(defaltDbName)){
            throw new SQLException("未配置默认数据源");
        }

        log.debug("当前激活的时序数据源："+defaltDbName);

        DataSource dataSource = TdengineDataSource.getPrimaryDataSource();
        return dataSource;
    }

    @Override
    public  DataSource use(String dsName) throws SQLException {
        if(ObjectUtil.isEmpty(dsName)){
            return use();
        }
        DataSource dataSource = TdengineDataSource.getDataSource(dsName);
        if(ObjectUtil.isEmpty(dataSource)){
            throw new SQLException(String.format("数据源:%s不存在",dsName));
        }
        log.debug("当前激活的时序数据源："+dsName);
        return dataSource;
    }

    @Override
    public Map<String,Object> getOne(String dbName, String sql, Object... params) throws RuntimeException {
        try {
            JdbcTemplate template = new JdbcTemplate(use(dbName));
            printSql(sql, params);
            Map<String, Object> res = template.queryForMap(sql, params);
            log.debug(String.format("%s <==      Total:%s", SQLNAME,ObjectUtil.isNotNull(res)?1:0));

            return res;
        } catch (SQLException e) {
            handleException(e);
        }
        return null;
    }
    /**
     *
     * 打印sql参数
     * @author zhuohx
     * @return void
     * @throws
     * @version 1.0
     * @since  2023/12/28 10:56
     */
    private static void printSql(String sql, Object[] params) {
        log.debug(String.format("%s  ==>  Preparing:%s", SQLNAME, sql));
        if(ObjectUtil.isNotEmpty(params)){
            log.debug(String.format("%s  ==> Parameters:%s", SQLNAME,JSONObject.toJSONString(params)));
        }
    }

    /**
     *
     * 处理异常
     * @author zhuohx
     * @param
     * @return void
     * @throws
     * @version 1.0
     * @since  2023/12/27 16:00
     */
    private static void handleException(SQLException e) {
        log.error(ERRFILENAME, e);
        log.error("ERROR Code: " + e.getErrorCode());
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
            JdbcTemplate template = new JdbcTemplate(use(dbName));
            printSql(sql, params);
            T res = (T) template.queryForObject(sql,params,new BeanPropertyRowMapper<T>(beanClass));
            log.debug(String.format("%s <==      Total:%s", SQLNAME,ObjectUtil.isNotNull(res)?1:0));

            return TdengineResultHelper.covertObj(res, beanClass);
        } catch (SQLException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public Number getNumber(String dbName, String sql, Object... params) {
        try {
            JdbcTemplate template = new JdbcTemplate(use(dbName));
            printSql(sql, params);
            Number res = (Number) template.queryForObject(sql,params,Number.class);
            log.debug(String.format("%s <==      Total:%s", SQLNAME,ObjectUtil.isNotNull(res)?1:0));

            return res;
        } catch (SQLException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public String getString(String dbName, String sql, Object... params)  {
        try {
            JdbcTemplate template = new JdbcTemplate(use(dbName));
            printSql(sql, params);
            String res = (String) template.queryForObject(sql,params,String.class);
            log.debug(String.format("%s <==      Total:%s", SQLNAME,ObjectUtil.isNotNull(res)?1:0));

            return res;
        } catch (SQLException e) {
            handleException(e);
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
    public boolean insertOrUpdate(String dbName, String sql, Object... params)  {
        int updateCount = 0;
        try {
            printSql(sql, params);
            JdbcTemplate template = new JdbcTemplate(use(dbName));
            updateCount = template.update(sql, params);
            log.debug(String.format("%s <==      Updates:%s", SQLNAME,updateCount));
        } catch (SQLException e) {
            handleException(e);
        }

        return updateCount > 0 ? true : false;
    }

    @Override
    public boolean save(String dsName, Object entity) {
        try {
            TdengineSqlVo tdengineSqlVo = TdengineSqlHelper.entityTosql(entity);
            return this.insertOrUpdate(dsName,tdengineSqlVo.getSql(),tdengineSqlVo.getColumnValeList().toArray());
        } catch(IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean remove(String dsName, TdengineQueryWrapper queryWrapper){

        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.DELETE, queryWrapper);
            printSql(sql, null);
            JdbcTemplate template = new JdbcTemplate(use(dsName));
            int res = template.update(sql);
            log.debug(String.format("%s <==      Deletes:%s", SQLNAME,res));

            if(res >0){
                return true;
            }
        } catch (SQLException e) {
            handleException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public <T> T getOne(String dsName, TdengineQueryWrapper<T> queryWrapper){
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);

            return this.getOne(dsName,sql,queryWrapper.getEntityClass());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> getMap(String dsName, TdengineQueryWrapper<?> queryWrapper) throws RuntimeException{
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);

            return  this.getOne(dsName, sql);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count(String dsName, TdengineQueryWrapper<?> queryWrapper) {
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.COUNT, queryWrapper);
            Number number = getNumber(dsName, sql);

            if(ObjectUtil.isEmpty(number)){
                return 0L;
            }
            return number.longValue();
        } catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Number getNumber(String dsName, TdengineQueryWrapper<?> queryWrapper){
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);

            return getNumber(dsName, sql, queryWrapper.getEntityClass());
        } catch(IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getString(String dbName, TdengineQueryWrapper<?> queryWrapper){
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);

            return getString(dbName, sql, null);
        } catch(IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> list(String dsName, TdengineQueryWrapper<T> queryWrapper) {
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);

            return list(dsName, sql, queryWrapper.getEntityClass());
        } catch(IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> listMaps(String dsName, TdengineQueryWrapper<?> queryWrapper){
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);

            return list(dsName, sql);
        }catch(IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> list(String dbName, String sql, Class<T> beanClass, Object... params)  {
        try {
            JdbcTemplate template = new JdbcTemplate(use(dbName));
            printSql(sql, params);
            List<T> res = template.query(sql, new BeanPropertyRowMapper<>(beanClass), params);
            log.debug(String.format("%s <==      Total:%s", SQLNAME,ObjectUtil.isNotNull(res)?res.size():0));

            return TdengineResultHelper.covertToList(res, beanClass);
        } catch (SQLException e) {
            handleException(e);
        }

        return null;
    }

    @Override
    public List<Map<String, Object>> list(String dbName, String sql, Object... params)  {
        try {
            JdbcTemplate template = new JdbcTemplate(use(dbName));
            printSql(sql, params);
            ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
            List<Map<String, Object>> query = template.query(sql, rowMapper);
            log.debug(String.format("%s <==      Total:%s", SQLNAME,ObjectUtil.isNotNull(query)?query.size():0));

            return query;
        } catch (SQLException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public <T> PageResult<T> page(String dsName, Page page, TdengineQueryWrapper<T> queryWrapper) {
        try {
            String sql = TdengineSqlHelper.wrapperToSql(SqlTypeEnum.SELECT, queryWrapper);

            return page(dsName, page, queryWrapper.getEntityClass(),sql);
        } catch(IllegalAccessException e){
            throw new RuntimeException(e);
        }
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
    public PageResult<Map<String, Object>> page(String dbName, Page page, String sql, Object... params)  {
        PageResult<Entity> result = new PageResult<>();
        String pageSql = TdengineSqlHelper.covertPageSql(page, sql);
        int totalSize = getTotalSize(dbName,page,sql,params);
        //查询该页数据

        List<Map<String, Object>> list = this.list(dbName,pageSql, params);
        return  TdengineResultHelper.setPageData(page, totalSize, list);
    }

    @Override
    public <T> List<T> batchList(String dbName, Page page, Class<T> beanClass, String sql, Object... params)  {
        int totalSize = 0;
        List<T> resList = new ArrayList<>();
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

        //计算总条数
        int from = sql.toLowerCase().indexOf("from");

        StringBuilder countSql= new StringBuilder("select count(*)  ").append(sql.substring(from));
        Number number = this.getNumber(countSql.toString(), params);
        if(ObjectUtil.isNotEmpty(number)){
            totalSize = number.intValue();
        }
        int pageSize = page.getPageSize();
        // 开始页数
        int pageNumber = page.getPageNumber();

        // 总页数
        int totalPage = PageUtil.totalPage(totalSize, pageSize);


        // 批量查询数据
        if(totalPage>0){
            while (pageNumber<totalPage+1){
                StringBuilder pageSql = new StringBuilder(sql).append(order.toString()).append(" limit ").append(pageSize).append(" offset ").append(TdengineResultHelper.getStart(new Page(pageNumber,pageSize)));
                log.debug("开始第{}页批量查询所有数据(自带分页)，共{}页",pageNumber,totalPage);
                //查询该页数据
                List<T> list = this.list(pageSql.toString(), beanClass, params);
                if(CollUtil.isNotEmpty(list)){
                    CollUtil.addAll(resList,list);
                }
                pageNumber ++;
            }
        }
        return resList;
    }

    @Override
    public <E> boolean saveBatch(String dsName, Collection<E> entityList, int batchSize) {
        Assert.isFalse(batchSize < 1, "batchSize must not be less than one", new Object[0]);
        if(CollUtil.isEmpty(entityList)){
            return  false;
        }
        try {
            ArrayList<Boolean> resList = new ArrayList();
            int size = entityList.size();
            int idxLimit = Math.min(batchSize, size);
            StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
            Iterator<E> iterator = entityList.iterator();
            int i =1;
            List<Object> valueList = new ArrayList<>();
            while (iterator.hasNext()){
                Object entity = iterator.next();
                TdengineSqlVo tdengineSqlVo = TdengineSqlHelper.entityTosql(entity);
                String sqlVoSql = tdengineSqlVo.getSql();
                List<Object> columnValeList = tdengineSqlVo.getColumnValeList();
                valueList.addAll(columnValeList);
                if( i < idxLimit){
                    sqlBuilder.append(sqlVoSql.replace("INSERT INTO","")) ;
                }else if(i == idxLimit){
                    sqlBuilder.append(sqlVoSql.replace("INSERT INTO","")) ;
                    boolean b = this.insertOrUpdate(dsName, sqlBuilder.toString(),valueList.toArray());
                    resList.add(b);
                    if(b){
                        log.debug(SQLNAME+" 数据索引：{},批量数量：{}条,批量保存数据成功", i,batchSize);
                        idxLimit = Math.min(i+batchSize,size);
                        sqlBuilder = new StringBuilder("INSERT INTO ");
                        valueList = new ArrayList<>();
                    }else{
                        log.info(SQLNAME+" 数据索引：{},批量数量：{}条,批量保存数据失败", i,batchSize);
                    }
                }
                i++;
            }
            // 循环完了后，看sqlBuilder是否还有没有插入的
            if(sqlBuilder.length()> new StringBuilder("INSERT INTO ").length()){
                boolean b = this.insertOrUpdate(dsName, sqlBuilder.toString(),valueList.toArray());
                resList.add(b);
                if(b){
                    log.debug(SQLNAME+" 数据索引：{},批量数量：{}条,批量保存数据成功", i,batchSize);
                }else{
                    log.info(SQLNAME+" 数据索引：{},批量数量：{}条,批量保存数据失败,", i,batchSize);
                }
            }

            // 判断结果集中全为true才成功，否则失败
            List<Boolean> failList = resList.stream().filter(item -> item == false).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(failList)){
                return false;
            }
            return true;

        } catch (Exception e) {
            throw new TdengineException(SQLNAME+" 批量保存数据异常", e);
        }
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
    private int getTotalSize(String dbName,Page page, String sql, Object... params)  {
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
