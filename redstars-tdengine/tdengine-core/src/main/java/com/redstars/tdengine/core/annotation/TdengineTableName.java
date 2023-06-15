package com.redstars.tdengine.core.annotation;

import java.lang.annotation.*;

/**
 * @author : zhouhx
 *  时序数据库表名
 * @date : 2023/6/12 13:11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface TdengineTableName {
    String value() default "";
}
