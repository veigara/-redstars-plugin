package com.redstars.tdengine.core.conditions.segments;

import com.redstars.tdengine.core.conditions.dao.TdengineISqlSegment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.redstars.tdengine.core.conditions.dao.TdengineStringPool.EMPTY;

/**
 * @author : zhouhx
 * SQL 片段集合 处理的抽象类
 * @date : 2023/12/25 17:13
 */
@SuppressWarnings("serial")
public abstract  class TdengineAbstractISegmentList extends ArrayList<TdengineISqlSegment> implements TdengineISqlSegment{
    /**
     * 最后一个值
     */
    TdengineISqlSegment lastValue = null;
    /**
     * 刷新 lastValue
     */
    boolean flushLastValue = false;
    /**
     * 结果集缓存
     */
    private String sqlSegment = EMPTY;
    /**
     * 是否缓存过结果集
     */
    private boolean cacheSqlSegment = true;

    /**
     * 重写方法,做个性化适配
     *
     * @param c 元素集合
     * @return 是否添加成功
     */
    @Override
    public boolean addAll(Collection<? extends TdengineISqlSegment> c) {
        List<TdengineISqlSegment> list = new ArrayList<>(c);
        boolean goon = transformList(list, list.get(0), list.get(list.size() - 1));
        if (goon) {
            cacheSqlSegment = false;
            if (flushLastValue) {
                this.flushLastValue(list);
            }
            return super.addAll(list);
        }
        return false;
    }

    /**
     * 在其中对值进行判断以及更改 list 的内部元素
     *
     * @param list         传入进来的 ISqlSegment 集合
     * @param firstSegment ISqlSegment 集合里第一个值
     * @param lastSegment  ISqlSegment 集合里最后一个值
     * @return true 是否继续向下执行; false 不再向下执行
     */
    protected abstract boolean transformList(List<TdengineISqlSegment> list, TdengineISqlSegment firstSegment, TdengineISqlSegment lastSegment);

    /**
     * 刷新属性 lastValue
     */
    private void flushLastValue(List<TdengineISqlSegment> list) {
        lastValue = list.get(list.size() - 1);
    }

    /**
     * 删除元素里最后一个值</br>
     * 并刷新属性 lastValue
     */
    void removeAndFlushLast() {
        remove(size() - 1);
        flushLastValue(this);
    }

    @Override
    public String getSqlSegment() {
        if (cacheSqlSegment) {
            return sqlSegment;
        }
        cacheSqlSegment = true;
        sqlSegment = childrenSqlSegment();
        return sqlSegment;
    }

    /**
     * 只有该类进行过 addAll 操作,才会触发这个方法
     * <p>
     * 方法内可以放心进行操作
     *
     * @return sqlSegment
     */
    protected abstract String childrenSqlSegment();

    @Override
    public void clear() {
        super.clear();
        lastValue = null;
        sqlSegment = EMPTY;
        cacheSqlSegment = true;
    }
}
