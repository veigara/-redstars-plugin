package com.redstars.tdengine.core.conditions.segments;

import com.redstars.tdengine.core.conditions.dao.TdengineISqlSegment;
import com.redstars.tdengine.core.conditions.dao.TdengineStringPool;
import com.redstars.tdengine.core.enums.TdengineMatchSegment;
import com.redstars.tdengine.core.enums.TdengineSqlKeyword;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : zhouhx
 * 普通片段
 * @date : 2023/12/25 17:12
 */
@SuppressWarnings("serial")
public class TdengineNormalSegmentList extends TdengineAbstractISegmentList {
    /**
     * 是否处理了的上个 not
     */
    private boolean executeNot = true;

    TdengineNormalSegmentList() {
        this.flushLastValue = true;
    }

    @Override
    protected boolean transformList(List<TdengineISqlSegment> list, TdengineISqlSegment firstSegment, TdengineISqlSegment lastSegment) {
        if (list.size() == 1) {
            /* 只有 and() 以及 or() 以及 not() 会进入 */
            if (!TdengineMatchSegment.NOT.match(firstSegment)) {
                //不是 not
                if (isEmpty()) {
                    //sqlSegment是 and 或者 or 并且在第一位,不继续执行
                    return false;
                }
                boolean matchLastAnd = TdengineMatchSegment.AND.match(lastValue);
                boolean matchLastOr = TdengineMatchSegment.OR.match(lastValue);
                if (matchLastAnd || matchLastOr) {
                    //上次最后一个值是 and 或者 or
                    if (matchLastAnd && TdengineMatchSegment.AND.match(firstSegment)) {
                        return false;
                    } else if (matchLastOr && TdengineMatchSegment.OR.match(firstSegment)) {
                        return false;
                    } else {
                        //和上次的不一样
                        removeAndFlushLast();
                    }
                }
            } else {
                executeNot = false;
                return false;
            }
        } else {
            if (TdengineMatchSegment.APPLY.match(firstSegment)) {
                list.remove(0);
            }
            if (!TdengineMatchSegment.AND_OR.match(lastValue) && !isEmpty()) {
                add(TdengineSqlKeyword.AND);
            }
            if (!executeNot) {
                list.add(0, TdengineSqlKeyword.NOT);
                executeNot = true;
            }
        }
        return true;
    }

    @Override
    protected String childrenSqlSegment() {
        if (TdengineMatchSegment.AND_OR.match(lastValue)) {
            removeAndFlushLast();
        }
        final String str = this.stream().map(TdengineISqlSegment::getSqlSegment).collect(Collectors.joining(TdengineStringPool.SPACE));
        return (TdengineStringPool.LEFT_BRACKET + str + TdengineStringPool.RIGHT_BRACKET);
    }

    @Override
    public void clear() {
        super.clear();
        flushLastValue = true;
        executeNot = true;
    }
}
