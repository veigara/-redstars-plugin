package com.redstars.tdengine.core.toolkit;

import com.redstars.tdengine.core.exception.TdengineException;

/**
 * @author : zhouhx
 * @date : 2023/12/26 13:53
 */
public class TdengineExceptionUtils {
    private TdengineExceptionUtils() {
    }
    public static TdengineException msg(String msg, Throwable t, Object... params) {
        return new TdengineException(String.format(msg, params), t);
    }

    public static TdengineException msg(String msg, Object... params) {
        return new TdengineException(String.format(msg, params));
    }

    public static TdengineException msg(Throwable t) {
        return new TdengineException(t);
    }

    public static void throwMsg(boolean condition, String msg, Object... params) {
        if (condition) {
            throw msg(msg, params);
        }
    }
}
