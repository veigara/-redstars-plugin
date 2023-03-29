package com.redstars.tdengine.core.annotation;

import java.lang.annotation.*;

/**
 * @author : zhouhx
 * varchar 类型对应的是byte[] 类型 需要转换成string
 * @date : 2023/3/3 13:11
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TdengineCovert {
    String value() default "";
}
