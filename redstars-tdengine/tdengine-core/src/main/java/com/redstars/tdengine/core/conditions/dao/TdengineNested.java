package com.redstars.tdengine.core.conditions.dao;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * @author : zhouhx
 * <p>嵌套</p>
 * <li>泛型 Param 是具体需要运行函数的类(也是 wrapper 的子类)</li>
 * @date : 2023/12/25 16:22
 */
public interface TdengineNested<Param, Children> extends Serializable {
    /**
     * ignore
     */
    default Children and(Consumer<Param> consumer) {
        return and(true, consumer);
    }

    /**
     * AND 嵌套
     *
     * @param condition 执行条件
     * @param consumer  消费函数
     * @return children
     */
    Children and(boolean condition, Consumer<Param> consumer);

    /**
     * ignore
     */
    default Children or(Consumer<Param> consumer) {
        return or(true, consumer);
    }

    /**
     * OR 嵌套
     * @param condition 执行条件
     * @param consumer  消费函数
     * @return children
     */
    Children or(boolean condition, Consumer<Param> consumer);

    /**
     * ignore
     */
    default Children nested(Consumer<Param> consumer) {
        return nested(true, consumer);
    }

    /**
     * 正常嵌套 不带 AND 或者 OR
     * @param condition 执行条件
     * @param consumer  消费函数
     * @return children
     */
    Children nested(boolean condition, Consumer<Param> consumer);

    /**
     * ignore
     */
    default Children not(Consumer<Param> consumer) {
        return not(true, consumer);
    }

    /**
     * not嵌套
     *
     * @param condition 执行条件
     * @param consumer  消费函数
     * @return children
     */
    Children not(boolean condition, Consumer<Param> consumer);
}
