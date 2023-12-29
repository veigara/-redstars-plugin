package com.redstars.tdengine.core.conditions.segments;

import com.redstars.tdengine.core.conditions.dao.TdengineISqlSegment;
import com.redstars.tdengine.core.conditions.dao.TdengineStringPool;
import com.redstars.tdengine.core.enums.TdengineMatchSegment;
import lombok.AccessLevel;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;

/**
 * @author : zhouhx
 *  合并 SQL 片段
 * @date : 2023/12/25 17:09
 */
public class TdengineMergeSegments implements  TdengineISqlSegment {
    private final TdengineNormalSegmentList normal = new TdengineNormalSegmentList();
    private final TdengineGroupBySegmentList groupBy = new TdengineGroupBySegmentList();
    private final TdengineHavingSegmentList having = new TdengineHavingSegmentList();
    private final TdengineOrderBySegmentList orderBy = new TdengineOrderBySegmentList();

    @Getter(AccessLevel.NONE)
    private String sqlSegment = TdengineStringPool.EMPTY;
    @Getter(AccessLevel.NONE)
    private boolean cacheSqlSegment = true;

    public void add(TdengineISqlSegment... iSqlSegments) {
        List<TdengineISqlSegment> list = Arrays.asList(iSqlSegments);
        TdengineISqlSegment firstSqlSegment = list.get(0);
        if (TdengineMatchSegment.ORDER_BY.match(firstSqlSegment)) {
            orderBy.addAll(list);
        } else if (TdengineMatchSegment.GROUP_BY.match(firstSqlSegment)) {
            groupBy.addAll(list);
        } else if (TdengineMatchSegment.HAVING.match(firstSqlSegment)) {
            having.addAll(list);
        } else {
            normal.addAll(list);
        }
        cacheSqlSegment = false;
    }

    @Override
    public String getSqlSegment() {
        if (cacheSqlSegment) {
            return sqlSegment;
        }
        cacheSqlSegment = true;
        if (normal.isEmpty()) {
            if (!groupBy.isEmpty() || !orderBy.isEmpty()) {
                sqlSegment = groupBy.getSqlSegment() + having.getSqlSegment() + orderBy.getSqlSegment();
            }
        } else {
            sqlSegment = "where "+normal.getSqlSegment() + groupBy.getSqlSegment() + having.getSqlSegment() + orderBy.getSqlSegment();
        }
        return sqlSegment;
    }

    /**
     * 清理
     *
     * @since 3.3.1
     */
    public void clear() {
        sqlSegment = TdengineStringPool.EMPTY;
        cacheSqlSegment = true;
        normal.clear();
        groupBy.clear();
        having.clear();
        orderBy.clear();
    }
}
