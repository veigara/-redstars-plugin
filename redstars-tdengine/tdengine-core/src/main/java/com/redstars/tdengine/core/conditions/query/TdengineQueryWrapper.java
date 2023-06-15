package com.redstars.tdengine.core.conditions.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redstars.tdengine.core.entity.TdengineTableInfo;
import lombok.SneakyThrows;

/**
 * @author : zhouhx
 * @date : 2023/6/14 14:01
 */
public class TdengineQueryWrapper<T> extends QueryWrapper<T> {
    /**
     *
     * 数据表的实体对象
     */
    private Class<T> entityClass;

    /**
     * 子表后缀
     */
    private String subTableSuffix;

    public TdengineQueryWrapper(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Class<T> getEntityClass(){
        return this.entityClass;
    }

    @SneakyThrows
    @Override
    public T getEntity(){
        return this.entityClass.newInstance();
    }
    public String getSubTableSuffix() {
        return subTableSuffix;
    }

    public void setSubTableSuffix(String subTableSuffix) {
        this.subTableSuffix = subTableSuffix;
    }

    public static void main(String[] args) {
        TdengineQueryWrapper query = new TdengineQueryWrapper(TdengineTableInfo.class);
        Class<?> entityClass = query.getEntityClass();
        System.out.println(entityClass);
    }
}
