package com.redstars.tdengine.core.enums;

import com.redstars.tdengine.core.conditions.dao.TdengineISqlSegment;
import lombok.AllArgsConstructor;

/**
 * @author : zhouhx
 * sql 枚举
 * @date : 2023/12/25 16:50
 */
@AllArgsConstructor
public enum TdengineSqlKeyword implements TdengineISqlSegment {
    AND("AND"),
    OR("OR"),
    NOT("NOT"),
    IN("IN"),
    NOT_IN("NOT IN"),
    LIKE("LIKE"),
    NOT_LIKE("NOT LIKE"),
    EQ("="),
    NE("<>"),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL"),
    GROUP_BY("GROUP BY"),
    HAVING("HAVING"),
    ORDER_BY("ORDER BY"),
    EXISTS("EXISTS"),
    NOT_EXISTS("NOT EXISTS"),
    BETWEEN("BETWEEN"),
    NOT_BETWEEN("NOT BETWEEN"),
    ASC("ASC"),
    DESC("DESC");
    private final String keyword;

    @Override
    public String getSqlSegment() {
        return this.keyword;
    }
}
