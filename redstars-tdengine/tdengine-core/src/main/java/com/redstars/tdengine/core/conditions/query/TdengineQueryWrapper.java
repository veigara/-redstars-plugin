package com.redstars.tdengine.core.conditions.query;


import cn.hutool.core.collection.CollUtil;
import com.redstars.tdengine.core.conditions.TdengineAbstractWrapper;
import com.redstars.tdengine.core.conditions.TdengineSharedString;
import com.redstars.tdengine.core.conditions.dao.TdengineStringPool;
import com.redstars.tdengine.core.conditions.segments.TdengineMergeSegments;
import com.redstars.tdengine.core.exception.TdengineException;
import com.redstars.tdengine.core.toolkit.TdengineSqlInjectionUtils;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : zhouhx
 * @date : 2023/6/14 14:01
 */
public class TdengineQueryWrapper<T>  extends TdengineAbstractWrapper<T, String, TdengineQueryWrapper<T>> implements TdengineQuery<TdengineQueryWrapper<T>, T, String>{
    /**
     * 查询字段
     */
    protected final TdengineSharedString sqlSelect = new TdengineSharedString();

    public TdengineQueryWrapper(Class<T> entityClass) {
        super.setEntityClass(entityClass);
        super.initNeed();
    }
    /**
     * 非对外公开的构造方法,只用于生产嵌套 sql
     *
     * @param entityClass 本不应该需要的
     */
    private TdengineQueryWrapper(Class<T> entityClass, AtomicInteger paramNameSeq,
                                 Map<String, Object> paramNameValuePairs, TdengineMergeSegments mergeSegments, TdengineSharedString paramAlias,
                                 TdengineSharedString lastSql, TdengineSharedString sqlComment, TdengineSharedString sqlFirst) {
        super.setEntityClass(entityClass);
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.paramAlias = paramAlias;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }
    /**
     * 检查 SQL 注入过滤
     */
    private boolean checkSqlInjection;

    /**
     * 开启检查 SQL 注入
     */
    public TdengineQueryWrapper<T> checkSqlInjection() {
        this.checkSqlInjection = true;
        return this;
    }
    @Override
    protected String columnToString(String column) {
        // 注入验证
        if (checkSqlInjection && TdengineSqlInjectionUtils.check(column)) {
            throw new TdengineException("Discovering SQL injection column: " + column);
        }
        return column;
    }
    @Override
    public TdengineQueryWrapper<T> select(boolean condition, List<String> columns) {
        if (condition && CollUtil.isNotEmpty(columns)) {
            this.sqlSelect.setStringValue(String.join(TdengineStringPool.COMMA, columns));
        }
        return typedThis;
    }

    @Override
    public String getSqlSelect() {
        return sqlSelect.getStringValue();
    }

    /**
     * 用于生成嵌套 sql
     * <p>
     * 故 sqlSelect 不向下传递
     * </p>
     */
    @Override
    protected TdengineQueryWrapper<T> instance() {
        return new TdengineQueryWrapper<>(getEntityClass(), paramNameSeq, paramNameValuePairs, new TdengineMergeSegments(),
                paramAlias, TdengineSharedString.emptyString(), TdengineSharedString.emptyString(), TdengineSharedString.emptyString());
    }
}
