package com.redstars.tdengine.core.conditions.query;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
/**
 * @author : zhouhx
 * @date : 2023/12/26 14:28
 */
public interface  TdengineQuery<Children, T, R> extends Serializable {
    /**
     * 指定查询字段
     *
     * @param columns 字段列表
     * @return children
     */
    @SuppressWarnings("unchecked")
    default Children select(R... columns) {
        return select(true, columns);
    }

    /**
     * 指定查询字段
     *
     * @param condition 执行条件
     * @param columns   字段列表
     * @return children
     */
    @SuppressWarnings("unchecked")
    default Children select(boolean condition, R... columns) {
        return select(condition, Arrays.asList(columns));
    }

    /**
     * 指定查询字段
     *
     * @param columns   字段列表
     * @return children
     */
    default Children select(List<R> columns) {
        return select(true, columns);
    }

    /**
     * 指定查询字段
     *
     * @param condition 执行条件
     * @param columns   字段列表
     * @return children
     */
    Children select(boolean condition, List<R> columns);

    /**
     * 查询条件 SQL 片段
     */
    String getSqlSelect();
}
