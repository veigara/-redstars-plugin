package com.redstars.tdengine.core.toolkit;

import com.redstars.tdengine.core.enums.TdengineSqlLike;

/**
 * @author : zhouhx
 * 组装like
 * @date : 2023/12/26 10:34
 */
public class TdengineSqlUtils {
    public static String concatLike(Object str, TdengineSqlLike type) {
        switch (type) {
            case LEFT:
                return "%" + str;
            case RIGHT:
                return str + "%";
            default:
                return "%" + str + "%";
        }
    }
}
