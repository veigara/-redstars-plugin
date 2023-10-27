package com.redstars.tdengine.core;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import com.redstars.tdengine.core.common.PageResult;
import com.redstars.tdengine.core.conditions.query.TdengineQueryWrapper;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author : zhouhx
 * tdengien sql
 * @date : 2023/6/12 10:09
 */
public abstract class TdengineSevice{
    /**
     *
     * 获取默认的数据源
     * @author zhuohx
     * @param
     * @return cn.hutool.db.Db
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:23
     */
    public abstract Db use() throws SQLException;

    /**
     *
     * 获取数据源
     * @author zhuohx
     * @param dsName 数据源名称
     * @return cn.hutool.db.Db
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:23
     */
    public abstract Db use(String dsName) throws SQLException;

    public boolean insertOrUpdate(String sql, Object... params) {
        return this.insertOrUpdate(null,sql,params);
    }

    /**
     * @author zhuohx
     * @description 插入或者更新 Tdengine没有更新操作语句 会根据时间戳来更新字段
     * @parms [sql, params]
     * @return boolean
     * @throws
     * @date 2023/3/3 14:50
     */
    public abstract boolean insertOrUpdate(String dbName,String sql, Object... params);

    /**
     *
     * 保存
     * @author zhuohx
     * @param   entity 数据
     * @return boolean
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:15
     */
    public  boolean save(Object entity){
        return this.save(null,entity);
    }

    /**
     *
     * 保存
     * @author zhuohx
     * @param   entity 数据
     * @param   dsName 数据源名称
     * @return boolean
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:15
     */
    public abstract  boolean save(String dsName,Object entity);

    /**
     *
     * 删除数据
     * @author zhuohx
     * @param   queryWrapper 条件
     * @return boolean
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:23
     */
    public  boolean remove(TdengineQueryWrapper<?> queryWrapper){
        return this.remove(null,queryWrapper);
    }

    /**
     *
     * 删除数据
     * @author zhuohx
     * @param   queryWrapper 条件
     * @param   dsName 数据源名称
     * @return boolean
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:23
     */
    public abstract boolean remove(String dsName, TdengineQueryWrapper<?> queryWrapper);

    /**
     *
     * 获取一行的值并映射成实体类
     * @author zhuohx
     * @param  queryWrapper 查询条件
     * @return T
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:25
     */
    public <T> T getOne(TdengineQueryWrapper<T> queryWrapper){
        return this.getOne(null,queryWrapper);
    }

    /**
     *
     * 获取一行的值并映射成实体类
     * @author zhuohx
     * @param  queryWrapper 查询条件
     * @param  dsName 数据源名称
     * @return T
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:25
     */
    public abstract <T> T getOne(String dsName,TdengineQueryWrapper<T> queryWrapper);

    /**
     *
     * 获取一行的值并映射成实体类
     * @author zhuohx
     * @param   sql sql语句
     * @param   beanClass
     * @param   params 参数
     * @return T
     * @throws
     * @version 1.0
     * @since  2023/6/14 14:54
     */
    public <T> T getOne(String sql, Class<T> beanClass, Object... params) {
        return  this.getOne(null,sql,beanClass,params);
    }

    /**
     *
     * 获取一行的值并映射成实体类
     * @author zhuohx
     * @param   dbName 数据源名称
     * @param   sql sql语句
     * @param   beanClass
     * @param   params 参数
     * @return T
     * @throws
     * @version 1.0
     * @since  2023/6/14 14:54
     */
    public abstract <T> T getOne(String dbName,String sql, Class<T> beanClass, Object... params);

    public Entity getOne(String sql, Object... params) {
        return this.getOne(null,sql,params);
    }

    public abstract Entity getOne(String dbName, String sql, Object... params);


    /**
     *
     * 获取一行的值并映射成map
     * @author zhuohx
     * @param   queryWrapper 查询条件
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:26
     */
    public  Map<String, Object> getMap(TdengineQueryWrapper<?> queryWrapper){
        return this.getMap(null,queryWrapper);
    }

    /**
     *
     * 获取一行的值并映射成map
     * @author zhuohx
     * @param   queryWrapper 查询条件
     * @param   dsName 数据源名称
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:26
     */
    public abstract Map<String, Object> getMap(String dsName,TdengineQueryWrapper<?> queryWrapper);

    /**
     *
     * 获取字段的值并映射成long
     * @author zhuohx
     * @param   queryWrapper 查询条件
     * @return long
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:27
     */
    public  long count(TdengineQueryWrapper<?> queryWrapper){
        return this.count(null,queryWrapper);
    }

    /**
     *
     * 获取字段的值并映射成long
     * @author zhuohx
     * @param   queryWrapper 查询条件
     * @param   dsName 数据源名称
     * @return long
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:27
     */
    public abstract long count(String dsName,TdengineQueryWrapper<?> queryWrapper);

    public Number getNumber(String sql, Object... params) {
        return this.getNumber(null,sql,params);
    }

    /**
     *
     * 获取一行的值并映射成Number
     * @author zhuohx
     * @param   dbName 数据源
     * @param   sql sql语句
     * @param   params 参数
     * @return java.lang.Number
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:08
     */
    public abstract Number getNumber(String dbName,String sql, Object... params);

    public  Number getNumber(TdengineQueryWrapper<?> queryWrapper){
        return this.getNumber(null,queryWrapper);
    }

    /**
     *
     * 获取一行的值并映射成Number
     * @author zhuohx
     * @param   dsName 数据源
     * @param   queryWrapper 查询语句
     * @return java.lang.Number
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:08
     */
    public abstract Number getNumber(String dsName,TdengineQueryWrapper<?> queryWrapper);

    public String getString(String sql, Object... params) throws SQLException {
        return this.getString(null,sql,params);
    }

    /**
     *
     * 获取一行的值并映射成字符串
     * @author zhuohx
     * @param   dbName 数据源
     * @param   sql sql语句
     * @param   params 参数
     * @return java.lang.String
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:16
     */
    public abstract String getString(String dbName,String sql, Object... params);

    public  String getString(TdengineQueryWrapper<?> queryWrapper){
        return this.getString(null,queryWrapper);
    }

    /**
     *
     * 获取一行的值并映射成字符串
     * @author zhuohx
     * @param   dbName 数据源
     * @param   queryWrapper 查询语句
     * @return java.lang.String
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:18
     */
    public abstract String getString(String dbName,TdengineQueryWrapper<?> queryWrapper);

    /**
     *
     * 列表查询并映射成实体类
     * @author zhuohx
     * @param   queryWrapper 查询条件
     * @return java.util.List<T>
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:43
     */
    public <T> List<T> list(TdengineQueryWrapper<T> queryWrapper){
        return this.list(null,queryWrapper);
    }

    /**
     *
     * 列表查询并映射成map类
     * @author zhuohx
     * @param   queryWrapper 查询条件
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:45
     */
    public  List<Entity> listMaps(TdengineQueryWrapper<?> queryWrapper){
        return this.listMaps(null,queryWrapper);
    }

    public List<Entity> list(String sql, Object... params) {
        return this.list(null,sql,params);
    }

    public <T> List<T> list(String sql, Class<T> beanClass, Object... params){
        return this.list(null,sql,beanClass,params);
    }

    public List<Entity> list(String sql, Map<String, Object> params) {
        return this.list(null,sql,params);
    }

    /**
     *
     * 列表查询并映射成实体类
     * @author zhuohx
     * @param   queryWrapper 查询条件
     * @param   dsName 数据源名称
     * @return java.util.List<T>
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:43
     */
    public abstract <T> List<T> list(String dsName,TdengineQueryWrapper<T> queryWrapper);

    /**
     *
     * 查询集合
     * @author zhuohx
     * @author zhuohx
     * @param   dbName 数据源名称
     * @param   sql sql语句
     * @param   beanClass
     * @param   params 参数
     * @return java.util.List<T>
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:28
     */
    public abstract <T> List<T> list(String dbName,String sql, Class<T> beanClass, Object... params);

    /**
     *
     * 查询集合
     * @author zhuohx
     * @param   dbName 数据源名称
     * @param   sql sql语句
     * @param   params 查询参数
     * @return java.util.List<cn.hutool.db.Entity>
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:32
     */
    public abstract List<Entity> list(String dbName,String sql, Map<String, Object> params);

    /**
     *
     * 查询集合
     * @author zhuohx
     * @param   dbName 数据源名称
     * @param   sql sql语句
     * @param   params 查询参数
     * @return java.util.List<cn.hutool.db.Entity>
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:34
     */
    public abstract List<Entity> list(String dbName,String sql, Object... params);

    /**
     *
     * 列表查询并映射成map类
     * @author zhuohx
     * @param   queryWrapper 查询条件
     * @param   dsName 数据源名称
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:45
     */
    public abstract List<Entity> listMaps(String dsName,TdengineQueryWrapper<?> queryWrapper);

    /**
     *
     * 分页查询
     * @author zhuohx
     * @param  page 分页参数 queryWrapper 查询条件
     * @return com.redstars.tdengine.core.common.PageResult<T>
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:48
     */
    public  <T> PageResult<T> page(Page page, TdengineQueryWrapper<T> queryWrapper){
        return this.page(null,page,queryWrapper);
    };

    public  <T> PageResult<T> page(Page page, Class<T> beanClass, String sql, Object... params) {
        return  this.page(null,page,beanClass,sql,params);
    }

    public PageResult<Entity> page(Page page, String sql, Object... params) {
        return this.page(null,page,sql,params);
    }

    /**
     *
     * 分页查询
     * @author zhuohx
     * @param  page 分页参数 queryWrapper 查询条件
     * @return com.redstars.tdengine.core.common.PageResult<T>
     * @throws
     * @version 1.0
     * @since  2023/6/12 10:48
     */
    public abstract <T> PageResult<T> page(String dsName,Page page, TdengineQueryWrapper<T> queryWrapper);

    /**
     *
     *
     * @author zhuohx
     * @param   dbName 数据源名称
     * @param   page 分页参数
     * @param   beanClass 实体类对象
     * @param   sql 不带分页的sql
     * @param   params 查询参数
     * @return com.redstars.tdengine.core.common.PageResult<T>
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:37
     */
    public abstract <T> PageResult<T> page(String dbName,Page page, Class<T> beanClass, String sql, Object... params);

    /**
     *
     *
     * @author zhuohx
     * @param   dbName 数据源名称
     * @param   page 分页参数
     * @param   sql 不带分页的sql
     * @param   params 查询参数
     * @return com.redstars.tdengine.core.common.PageResult<cn.hutool.db.Entity>
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:39
     */
    public abstract PageResult<Entity> page(String dbName,Page page, String sql, Object... params);

    /**
     *
     * 批量查询所有数据(自带分页)
     * @author zhuohx
     * @param   page 分页参数
     * @param   sql 不带分页的sql
     * @param   params 查询参数
     * @return java.util.List<T>
     * @throws
     * @version 1.0
     * @since  2023/7/24 15:19
     */
    public <T> List<T> batchList(Page page, Class<T> beanClass, String sql, Object... params){
        return this.batchList(null,page,beanClass,sql,params);
    }

    /**
     *
     * 批量查询所有数据(自带分页)
     * @author zhuohx
     * @param   dbName 数据源名称
     * @param   page 分页参数
     * @param   sql 不带分页的sql
     * @param   params 查询参数
     * @return java.util.List<T>
     * @throws
     * @version 1.0
     * @since  2023/7/24 15:19
     */
    public abstract <T> List<T> batchList(String dbName,Page page, Class<T> beanClass, String sql, Object... params);

    /**
     *
     * 批量保存数据
     * @author zhuohx
     * @param   entityList 集合数据
     * @return boolean
     * @throws
     * @version 1.0
     * @since  2023/10/26 15:19
     */
    public <E> boolean saveBatch(Collection<E> entityList) {
        return this.saveBatch(null,entityList, 1000);
    }

    /**
     *
     * 根据数据源批量保存数据
     * @author zhuohx
     * @param   dsName 数据源名称
     * @param   entityList 集合数据
     * @return boolean
     * @throws
     * @version 1.0
     * @since  2023/10/26 15:19
     */
    public <E> boolean saveBatch(String dsName,Collection<E> entityList) {
        return this.saveBatch(dsName,entityList, 1000);
    }

    /**
     *
     * 批量保存数据
     * <p> 时序数据库没有事务回滚，只保存正确插入多少条<p/>
     * @author zhuohx
     *  @param   entityList 集合数据
     *  @param   batchSize 一次性批量插入次数
     * @return boolean
     * @throws
     * @version 1.0
     * @since  2023/10/26 15:25
     */
    public <E> boolean saveBatch(Collection<E> entityList, int batchSize){
        return this.saveBatch(null,entityList,batchSize);
    }

    /**
     *
     *
     * @author zhuohx
     * @param   dsName 数据源名称
     * @param   entityList 集合数据
     * @param   batchSize 一次性批量插入次数
     * @return boolean
     * @throws
     * @version 1.0
     * @since  2023/10/26 15:26
     */
    public abstract <E> boolean saveBatch(String dsName,Collection<E> entityList, int batchSize);
}
