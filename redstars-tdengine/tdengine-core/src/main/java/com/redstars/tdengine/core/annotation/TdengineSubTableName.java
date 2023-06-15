package com.redstars.tdengine.core.annotation;

import java.lang.annotation.*;

/**
 * @author : zhouhx
 *  时序数据库子表表名
 * @date : 2023/6/12 13:11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TdengineSubTableName {
    String value() default "";
}
