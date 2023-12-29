package com.redstars.tdengine.core.toolkit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author : zhouhx
 * @date : 2023/12/26 10:47
 */
public class TdengineCollectionUtils {
    @SafeVarargs
    public static <T> List<T> toList(T... t) {
        return t != null ? Arrays.asList(t) : Collections.emptyList();
    }
}
