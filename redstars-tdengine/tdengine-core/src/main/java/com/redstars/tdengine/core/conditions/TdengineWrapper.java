package com.redstars.tdengine.core.conditions;

import com.redstars.tdengine.core.conditions.dao.TdengineISqlSegment;

/**
 * @author : zhouhx
 * 条件构造抽象类
 * @date : 2023/12/25 16:06
 */
public abstract class TdengineWrapper<T> implements TdengineISqlSegment {
    public TdengineWrapper() {

    }
    /**
     * 实体对象（子类实现）
     *
     * @return 泛型 T
     */
    public abstract T getEntity();
    /**
     * 获取查询字段
     */
    public String getSqlSelectColumn(){
        return null;
    }
    /**
     * 获取查询条件
     */

    public String getSqlWhere(){
        return null;
    }
    /**
     * 获取排序条件
     */
    public String getSqlOrder(){
        return null;
    }

    /**
     * 条件清空
     *
     * @since 3.3.1
     */
    abstract public void clear();
}
