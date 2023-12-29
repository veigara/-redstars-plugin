package com.redstars.tdengine.core.conditions;

import com.redstars.tdengine.core.conditions.dao.TdengineStringPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @author : zhouhx
 *  共享查询字段
 * @date : 2023/12/25 16:32
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TdengineSharedString implements Serializable {
    /**
     * 共享的 string 值
     */
    private String stringValue;

    /**
     * SharedString 里是 ""
     */
    public static TdengineSharedString emptyString() {
        return new TdengineSharedString(TdengineStringPool.EMPTY);
    }

    /**
     * 置 empty
     *
     * @since 3.3.1
     */
    public void toEmpty() {
        stringValue = TdengineStringPool.EMPTY;
    }

    /**
     * 置 null
     *
     * @since 3.3.1
     */
    public void toNull() {
        stringValue = null;
    }
}

