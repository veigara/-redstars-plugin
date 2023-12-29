package com.redstars.tdengine.core.conditions.segments;

import com.redstars.tdengine.core.conditions.dao.TdengineISqlSegment;
import com.redstars.tdengine.core.conditions.dao.TdengineStringPool;
import com.redstars.tdengine.core.enums.TdengineSqlKeyword;
import java.util.List;
import static java.util.stream.Collectors.joining;

/**
 * @author : zhouhx
 * Having SQL 片段
 * @date : 2023/12/25 17:37
 */
@SuppressWarnings("serial")
public class TdengineHavingSegmentList extends TdengineAbstractISegmentList{
    @Override
    protected boolean transformList(List<TdengineISqlSegment> list, TdengineISqlSegment firstSegment, TdengineISqlSegment lastSegment) {
        if (!isEmpty()) {
            this.add(TdengineSqlKeyword.AND);
        }
        list.remove(0);
        return true;
    }

    @Override
    protected String childrenSqlSegment() {
        if (isEmpty()) {
            return TdengineStringPool.EMPTY;
        }
        return this.stream().map(TdengineISqlSegment::getSqlSegment).collect(joining(TdengineStringPool.SPACE, TdengineStringPool.SPACE + TdengineSqlKeyword.HAVING.getSqlSegment() + TdengineStringPool.SPACE, TdengineStringPool.EMPTY));
    }
}
