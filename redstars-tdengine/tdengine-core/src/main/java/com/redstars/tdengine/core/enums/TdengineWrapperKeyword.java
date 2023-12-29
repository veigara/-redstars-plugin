package com.redstars.tdengine.core.enums;

import com.redstars.tdengine.core.conditions.dao.TdengineISqlSegment;
import lombok.AllArgsConstructor;

/**
 * @author : zhouhx
 * @date : 2023/12/25 17:22
 */
@AllArgsConstructor
public enum TdengineWrapperKeyword implements TdengineISqlSegment {
    /**
     * 只用作于辨识,不用于其他
     */
    APPLY(null);

    private final String keyword;

    @Override
    public String getSqlSegment() {
        return keyword;
    }
}
