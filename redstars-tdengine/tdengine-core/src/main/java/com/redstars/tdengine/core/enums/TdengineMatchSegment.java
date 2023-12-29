package com.redstars.tdengine.core.enums;

import com.redstars.tdengine.core.conditions.dao.TdengineISqlSegment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

/**
 * @author : zhouhx
 * 匹配片段
 * @date : 2023/12/25 17:19
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum TdengineMatchSegment {
    GROUP_BY(i -> i == TdengineSqlKeyword.GROUP_BY),
    ORDER_BY(i -> i == TdengineSqlKeyword.ORDER_BY),
    NOT(i -> i == TdengineSqlKeyword.NOT),
    AND(i -> i == TdengineSqlKeyword.AND),
    OR(i -> i == TdengineSqlKeyword.OR),
    AND_OR(i -> i == TdengineSqlKeyword.AND || i == TdengineSqlKeyword.OR),
    EXISTS(i -> i == TdengineSqlKeyword.EXISTS),
    HAVING(i -> i == TdengineSqlKeyword.HAVING),
    APPLY(i -> i == TdengineWrapperKeyword.APPLY);

    private final Predicate<TdengineISqlSegment> predicate;

    public boolean match(TdengineISqlSegment segment) {
        return getPredicate().test(segment);
    }
}
