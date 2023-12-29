package com.redstars.tdengine.core.conditions.segments;

import com.redstars.tdengine.core.conditions.dao.TdengineISqlSegment;
import com.redstars.tdengine.core.conditions.dao.TdengineStringPool;
import com.redstars.tdengine.core.enums.TdengineSqlKeyword;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.joining;

/**
 * @author : zhouhx
 * @date : 2023/12/25 17:40
 */
@SuppressWarnings("serial")
public class TdengineOrderBySegmentList  extends TdengineAbstractISegmentList{
    @Override
    protected boolean transformList(List<TdengineISqlSegment> list, TdengineISqlSegment firstSegment, TdengineISqlSegment lastSegment) {
        list.remove(0);
        final List<TdengineISqlSegment> sqlSegmentList = new ArrayList<>(list);
        list.clear();
        list.add(() -> sqlSegmentList.stream().map(TdengineISqlSegment::getSqlSegment).collect(joining(TdengineStringPool.SPACE)));
        return true;
    }

    @Override
    protected String childrenSqlSegment() {
        if (isEmpty()) {
            return TdengineStringPool.EMPTY;
        }
        return this.stream().map(TdengineISqlSegment::getSqlSegment).collect(joining(TdengineStringPool.COMMA, TdengineStringPool.SPACE + TdengineSqlKeyword.ORDER_BY.getSqlSegment() + TdengineStringPool.SPACE, TdengineStringPool.EMPTY));
    }
}
