package com.redstars.tdengine.core.conditions.dao;

import com.redstars.tdengine.core.toolkit.TdengineCollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * @author : zhouhx
 * 查询条件封装
 * @date : 2023/12/25 16:20
 */
public interface TdengineCompare<Children, R>  {
    /**
     * ignore
     */
    default <V> Children allEq(Map<R, V> params) {
        return allEq(params, true);
    }

    /**
     * ignore
     */
    default <V> Children allEq(Map<R, V> params, boolean null2IsNull) {
        return allEq(true, params, null2IsNull);
    }

    /**
     * map 所有非空属性等于 =
     *
     * @param condition   执行条件
     * @param params      map 类型的参数, key 是字段名, value 是字段值
     * @param null2IsNull 是否参数为 null 自动执行 isNull 方法, false 则忽略这个字段\
     * @return children
     */
    <V> Children allEq(boolean condition, Map<R, V> params, boolean null2IsNull);

    /**
     * ignore
     */
    default <V> Children allEq(BiPredicate<R, V> filter, Map<R, V> params) {
        return allEq(filter, params, true);
    }

    /**
     * ignore
     */
    default <V> Children allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull) {
        return allEq(true, filter, params, null2IsNull);
    }

    /**
     * 字段过滤接口，传入多参数时允许对参数进行过滤
     *
     * @param condition   执行条件
     * @param filter      返回 true 来允许字段传入比对条件中
     * @param params      map 类型的参数, key 是字段名, value 是字段值
     * @param null2IsNull 是否参数为 null 自动执行 isNull 方法, false 则忽略这个字段
     * @return children
     */
    <V> Children allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull);

    /**
     * ignore
     */
    default Children eq(R column, Object val) {
        return eq(true, column, val);
    }

    /**
     * 等于 =
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children eq(boolean condition, R column, Object val);

    /**
     * ignore
     */
    default Children ne(R column, Object val) {
        return ne(true, column, val);
    }

    /**
     * 不等于 &lt;&gt;
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children ne(boolean condition, R column, Object val);

    /**
     * ignore
     */
    default Children gt(R column, Object val) {
        return gt(true, column, val);
    }

    /**
     * 大于 &gt;
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children gt(boolean condition, R column, Object val);

    /**
     * ignore
     */
    default Children ge(R column, Object val) {
        return ge(true, column, val);
    }

    /**
     * 大于等于 &gt;=
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children ge(boolean condition, R column, Object val);

    /**
     * ignore
     */
    default Children lt(R column, Object val) {
        return lt(true, column, val);
    }

    /**
     * 小于 &lt;
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children lt(boolean condition, R column, Object val);

    /**
     * ignore
     */
    default Children le(R column, Object val) {
        return le(true, column, val);
    }

    /**
     * 小于等于 &lt;=
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children le(boolean condition, R column, Object val);

    /**
     * ignore
     */
    default Children between(R column, Object val1, Object val2) {
        return between(true, column, val1, val2);
    }

    /**
     * BETWEEN 值1 AND 值2
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val1      值1
     * @param val2      值2
     * @return children
     */
    Children between(boolean condition, R column, Object val1, Object val2);

    /**
     * ignore
     */
    default Children notBetween(R column, Object val1, Object val2) {
        return notBetween(true, column, val1, val2);
    }

    /**
     * NOT BETWEEN 值1 AND 值2
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val1      值1
     * @param val2      值2
     * @return children
     */
    Children notBetween(boolean condition, R column, Object val1, Object val2);

    /**
     * ignore
     */
    default Children like(R column, Object val) {
        return like(true, column, val);
    }

    /**
     * LIKE '%值%'
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children like(boolean condition, R column, Object val);

    /**
     * ignore
     */
    default Children notLike(R column, Object val) {
        return notLike(true, column, val);
    }

    /**
     * NOT LIKE '%值%'
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children notLike(boolean condition, R column, Object val);

    /**
     * ignore
     */
    default Children notLikeLeft(R column, Object val) {
        return notLikeLeft(true, column, val);
    }

    /**
     * NOT LIKE '%值'
     *
     * @param condition
     * @param column
     * @param val
     * @return children
     */
    Children notLikeLeft(boolean condition, R column, Object val);

    /**
     * ignore
     */
    default Children notLikeRight(R column, Object val) {
        return notLikeRight(true, column, val);
    }

    /**
     * NOT LIKE '值%'
     *
     * @param condition
     * @param column
     * @param val
     * @return children
     */
    Children notLikeRight(boolean condition, R column, Object val);

    /**
     * ignore
     */
    default Children likeLeft(R column, Object val) {
        return likeLeft(true, column, val);
    }

    /**
     * LIKE '%值'
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children likeLeft(boolean condition, R column, Object val);

    /**
     * ignore
     */
    default Children likeRight(R column, Object val) {
        return likeRight(true, column, val);
    }

    /**
     * LIKE '值%'
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    Children likeRight(boolean condition, R column, Object val);

    /**
     * ignore
     */
    default Children isNull(R column) {
        return isNull(true, column);
    }

    /**
     * 字段 IS NULL
     * <p>例: isNull("name")</p>
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    Children isNull(boolean condition, R column);

    /**
     * ignore
     */
    default Children isNotNull(R column) {
        return isNotNull(true, column);
    }

    /**
     * 字段 IS NOT NULL
     * <p>例: isNotNull("name")</p>
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    Children isNotNull(boolean condition, R column);

    /**
     * ignore
     */
    default Children in(R column, Collection<?> coll) {
        return in(true, column, coll);
    }

    /**
     * 字段 IN (value.get(0), value.get(1), ...)
     * <p>例: in("id", Arrays.asList(1, 2, 3, 4, 5))</p>
     *
     * <li> 注意！集合为空若存在逻辑错误，请在 condition 条件中判断 </li>
     * <li> 如果集合为 empty 则不会进行 sql 拼接 </li>
     *
     * @param condition 执行条件
     * @param column    字段
     * @param coll      数据集合
     * @return children
     */
    Children in(boolean condition, R column, Collection<?> coll);

    /**
     * ignore
     */
    default Children in(R column, Object... values) {
        return in(true, column, values);
    }

    /**
     * 字段 IN (v0, v1, ...)
     * <p>例: in("id", 1, 2, 3, 4, 5)</p>
     *
     * <li> 注意！数组为空若存在逻辑错误，请在 condition 条件中判断 </li>
     * <li> 如果动态数组为 empty 则不会进行 sql 拼接 </li>
     *
     * @param condition 执行条件
     * @param column    字段
     * @param values    数据数组
     * @return children
     */
    Children in(boolean condition, R column, Object... values);

    /**
     * ignore
     */
    default Children notIn(R column, Collection<?> coll) {
        return notIn(true, column, coll);
    }

    /**
     * 字段 NOT IN (value.get(0), value.get(1), ...)
     * <p>例: notIn("id", Arrays.asList(1, 2, 3, 4, 5))</p>
     *
     * @param condition 执行条件
     * @param column    字段
     * @param coll      数据集合
     * @return children
     */
    Children notIn(boolean condition, R column, Collection<?> coll);

    /**
     * ignore
     */
    default Children notIn(R column, Object... value) {
        return notIn(true, column, value);
    }

    /**
     * 字段 NOT IN (v0, v1, ...)
     * <p>例: notIn("id", 1, 2, 3, 4, 5)</p>
     *
     * @param condition 执行条件
     * @param column    字段
     * @param values    数据数组
     * @return children
     */
    Children notIn(boolean condition, R column, Object... values);

    /**
     * ignore
     */
    default Children inSql(R column, String inValue) {
        return inSql(true, column, inValue);
    }

    /**
     * 字段 IN ( sql语句 )
     * <p>!! sql 注入方式的 in 方法 !!</p>
     * <p>例1: inSql("id", "1, 2, 3, 4, 5, 6")</p>
     * <p>例2: inSql("id", "select id from table where id &lt; 3")</p>
     *
     * @param condition 执行条件
     * @param column    字段
     * @param inValue   sql语句
     * @return children
     */
    Children inSql(boolean condition, R column, String inValue);

    /**
     * 字段 &gt; ( sql语句 )
     * <p>例1: gtSql("id", "1, 2, 3, 4, 5, 6")</p>
     * <p>例1: gtSql("id", "select id from table where name = 'JunJun'")</p>
     *
     * @param condition
     * @param column
     * @param inValue
     * @return
     */
    Children gtSql(boolean condition, R column, String inValue);

    /**
     * ignore
     */
    default Children gtSql(R column, String inValue) {
        return gtSql(true, column, inValue);
    }

    /**
     * 字段 >= ( sql语句 )
     * <p>例1: geSql("id", "1, 2, 3, 4, 5, 6")</p>
     * <p>例1: geSql("id", "select id from table where name = 'JunJun'")</p>
     *
     * @param condition
     * @param column
     * @param inValue
     * @return
     */
    Children geSql(boolean condition, R column, String inValue);

    /**
     * ignore
     */
    default Children geSql(R column, String inValue) {
        return geSql(true, column, inValue);
    }

    /**
     * 字段 &lt; ( sql语句 )
     * <p>例1: ltSql("id", "1, 2, 3, 4, 5, 6")</p>
     * <p>例1: ltSql("id", "select id from table where name = 'JunJun'")</p>
     *
     * @param condition
     * @param column
     * @param inValue
     * @return
     */
    Children ltSql(boolean condition, R column, String inValue);

    /**
     * ignore
     */
    default Children ltSql(R column, String inValue) {
        return ltSql(true, column, inValue);
    }

    /**
     * 字段 <= ( sql语句 )
     * <p>例1: leSql("id", "1, 2, 3, 4, 5, 6")</p>
     * <p>例1: leSql("id", "select id from table where name = 'JunJun'")</p>
     *
     * @param condition
     * @param column
     * @param inValue
     * @return
     */
    Children leSql(boolean condition, R column, String inValue);

    /**
     * ignore
     */
    default Children leSql(R column, String inValue) {
        return leSql(true, column, inValue);
    }

    /**
     * ignore
     */
    default Children notInSql(R column, String inValue) {
        return notInSql(true, column, inValue);
    }

    /**
     * 字段 NOT IN ( sql语句 )
     * <p>!! sql 注入方式的 not in 方法 !!</p>
     * <p>例1: notInSql("id", "1, 2, 3, 4, 5, 6")</p>
     * <p>例2: notInSql("id", "select id from table where id &lt; 3")</p>
     *
     * @param condition 执行条件
     * @param column    字段
     * @param inValue   sql语句 ---&gt; 1,2,3,4,5,6 或者 select id from table where id &lt; 3
     * @return children
     */
    Children notInSql(boolean condition, R column, String inValue);

    /**
     * 分组：GROUP BY 字段, ...
     * <p>例: groupBy("id")</p>
     *
     * @param condition 执行条件
     * @param column    单个字段
     * @return children
     */
    Children groupBy(boolean condition, R column);

    default Children groupBy(R column) {
        return groupBy(true, column);
    }

    /**
     * 分组：GROUP BY 字段, ...
     * <p>例: groupBy(Arrays.asList("id", "name"))</p>
     *
     * @param condition 执行条件
     * @param columns   字段数组
     * @return children
     */
    Children groupBy(boolean condition, List<R> columns);

    default Children groupBy(List<R> columns) {
        return groupBy(true, columns);
    }

    default Children groupBy(R column, R... columns) {
        return groupBy(true, column, columns);
    }

    /**
     * 分组：GROUP BY 字段, ...
     */
    Children groupBy(boolean condition, R column, R... columns);

    /**
     * 排序：ORDER BY 字段, ... ASC
     * <p>例: orderByAsc(true, "id")</p>
     *
     * @param condition 执行条件
     * @param column    单个字段
     * @return children
     */
    default Children orderByAsc(boolean condition, R column) {
        return orderBy(condition, true, column);
    }

    /**
     * 排序：ORDER BY 字段, ... ASC
     * <p>例: orderByAsc("id")</p>
     *
     * @param column 单个字段
     * @return children
     */
    default Children orderByAsc(R column) {
        return orderByAsc(true, column);
    }

    /**
     * 排序：ORDER BY 字段, ... ASC
     * <p>例: orderByAsc(true, Arrays.asList("id", "name"))</p>
     *
     * @param condition 执行条件
     * @param columns   字段数组
     * @return children
     */
    default Children orderByAsc(boolean condition, List<R> columns) {
        return orderBy(condition, true, columns);
    }

    /**
     * 排序：ORDER BY 字段, ... ASC
     * <p>例: orderByAsc(Arrays.asList("id", "name"))</p>
     *
     * @param columns 字段数组
     * @return children
     */
    default Children orderByAsc(List<R> columns) {
        return orderByAsc(true, columns);
    }

    /**
     * 排序：ORDER BY 字段, ... ASC
     *
     * @param column  字段
     * @param columns 字段数组
     * @return children
     */
    default Children orderByAsc(R column, R... columns) {
        return orderByAsc(true, column, columns);
    }

    /**
     * 排序：ORDER BY 字段, ... ASC
     *
     * @param condition 执行条件
     * @param column    字段
     * @param columns   字段数组
     */
    default Children orderByAsc(boolean condition, R column, R... columns) {
        return orderBy(condition, true, column, columns);
    }

    /**
     * 排序：ORDER BY 字段, ... ASC
     * <p>例: orderByAsc(true, Arrays.asList("id", "name"))</p>
     *
     * @param condition 执行条件
     * @param columns   字段数组
     * @return children
     * @since 3.5.4
     */
    default Children orderByAsc(boolean condition, R column, List<R> columns) {
        return orderBy(condition, true, column, columns);
    }

    /**
     * 排序：ORDER BY 字段, ... DESC
     * <p>例: orderByDesc(true, "id")</p>
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default Children orderByDesc(boolean condition, R column) {
        return orderBy(condition, false, column);
    }

    /**
     * 排序：ORDER BY 字段, ... DESC
     * <p>例: orderByDesc("id")</p>
     *
     * @param column    字段
     * @return children
     */
    default Children orderByDesc(R column) {
        return orderByDesc(true, column);
    }

    /**
     * 排序：ORDER BY 字段, ... DESC
     * <p>例: orderByDesc(true, Arrays.asList("id", "name"))</p>
     *
     * @param condition 执行条件
     * @param columns   字段列表
     * @return children
     */
    default Children orderByDesc(boolean condition, List<R> columns) {
        return orderBy(condition, false, columns);
    }

    /**
     * 排序：ORDER BY 字段, ... DESC
     *
     * @param columns 字段列表
     */
    default Children orderByDesc(List<R> columns) {
        return orderByDesc(true, columns);
    }

    /**
     * 排序：ORDER BY 字段, ... DESC
     *
     * @param column  单个字段
     * @param columns 字段列表
     */
    default Children orderByDesc(R column, R... columns) {
        return orderByDesc(true, column, columns);
    }

    /**
     * 排序：ORDER BY 字段, ... DESC
     *
     * @param condition 执行条件
     * @param column    单个字段
     * @param columns   字段列表
     */
    default Children orderByDesc(boolean condition, R column, R... columns) {
        return orderBy(condition, false, column, TdengineCollectionUtils.toList(columns));
    }

    /**
     * 排序：ORDER BY 字段, ... DESC
     *
     * @param condition 执行条件
     * @param column    单个字段
     * @param columns   字段列表
     * @since 3.5.4
     */
    default Children orderByDesc(boolean condition, R column, List<R> columns) {
        return orderBy(condition, false, column, columns);
    }


    /**
     * 排序：ORDER BY 字段, ...
     * <p>例: orderBy(true, "id")</p>
     *
     * @param condition 执行条件
     * @param isAsc     是否是 ASC 排序
     * @param column    单个字段
     * @return children
     */
    Children orderBy(boolean condition, boolean isAsc, R column);

    /**
     * 排序：ORDER BY 字段, ...
     * <p>例: orderBy(true, Arrays.asList("id", "name"))</p>
     *
     * @param condition 执行条件
     * @param isAsc     是否是 ASC 排序
     * @param columns   字段列表
     * @return children
     */
    Children orderBy(boolean condition, boolean isAsc, List<R> columns);

    /**
     * 排序：ORDER BY 字段, ...
     *
     * @param condition 执行条件
     * @param isAsc     是否是 ASC 排序
     * @param columns   字段列表
     * @return children
     */
    Children orderBy(boolean condition, boolean isAsc, R column, R... columns);

    /**
     * 排序：ORDER BY 字段, ...
     *
     * @param condition 执行条件
     * @param isAsc     是否是 ASC 排序
     * @param columns   字段列表
     * @return children
     * @since 3.5.4
     */
    Children orderBy(boolean condition, boolean isAsc, R column, List<R> columns);

    /**
     * ignore
     */
    default Children having(String sqlHaving, Object... params) {
        return having(true, sqlHaving, params);
    }

    /**
     * HAVING ( sql语句 )
     * <p>例1: having("sum(age) &gt; 10")</p>
     * <p>例2: having("sum(age) &gt; {0}", 10)</p>
     *
     * @param condition 执行条件
     * @param sqlHaving sql 语句
     * @param params    参数数组
     * @return children
     */
    Children having(boolean condition, String sqlHaving, Object... params);

    /**
     * ignore
     */
    default Children func(Consumer<Children> consumer) {
        return func(true, consumer);
    }

    /**
     * 消费函数
     *
     * @param consumer 消费函数
     * @return children
     * @since 3.3.1
     */
    Children func(boolean condition, Consumer<Children> consumer);

    /**
     * ignore
     */
    default Children or() {
        return or(true);
    }

    /**
     * 拼接 OR
     *
     * @param condition 执行条件
     * @return children
     */
    Children or(boolean condition);

    /**
     * ignore
     */
    default Children apply(String applySql, Object... values) {
        return apply(true, applySql, values);
    }

    /**
     * 拼接 sql
     * <p>!! 会有 sql 注入风险 !!</p>
     * <p>例1: apply("id = 1")</p>
     * <p>例2: apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")</p>
     * <p>例3: apply("date_format(dateColumn,'%Y-%m-%d') = {0}", LocalDate.now())</p>
     *
     * @param condition 执行条件
     * @param values    数据数组
     * @return children
     */
    Children apply(boolean condition, String applySql, Object... values);

    /**
     * ignore
     */
    default Children last(String lastSql) {
        return last(true, lastSql);
    }

    /**
     * 无视优化规则直接拼接到 sql 的最后(有sql注入的风险,请谨慎使用)
     * <p>例: last("limit 1")</p>
     * <p>注意只能调用一次,多次调用以最后一次为准</p>
     *
     * @param condition 执行条件
     * @param lastSql   sql语句
     * @return children
     */
    Children last(boolean condition, String lastSql);

    /**
     * ignore
     */
    default Children comment(String comment) {
        return comment(true, comment);
    }

    /**
     * sql 注释(会拼接在 sql 的最后面)
     *
     * @param condition 执行条件
     * @param comment   sql注释
     * @return children
     */
    Children comment(boolean condition, String comment);

    /**
     * ignore
     */
    default Children first(String firstSql) {
        return first(true, firstSql);
    }

    /**
     * sql 起始句（会拼接在SQL语句的起始处）
     *
     * @param condition 执行条件
     * @param firstSql  起始语句
     * @return children
     * @since 3.3.1
     */
    Children first(boolean condition, String firstSql);

    /**
     * ignore
     */
    default Children exists(String existsSql, Object... values) {
        return exists(true, existsSql, values);
    }

    /**
     * 拼接 EXISTS ( sql语句 )
     * <p>!! sql 注入方法 !!</p>
     * <p>例: exists("select id from table where age = 1")</p>
     *
     * @param condition 执行条件
     * @param existsSql sql语句
     * @param values    数据数组
     * @return children
     */
    Children exists(boolean condition, String existsSql, Object... values);

    /**
     * ignore
     */
    default Children notExists(String existsSql, Object... values) {
        return notExists(true, existsSql, values);
    }

    /**
     * 拼接 NOT EXISTS ( sql语句 )
     * <p>!! sql 注入方法 !!</p>
     * <p>例: notExists("select id from table where age = 1")</p>
     *
     * @param condition 执行条件
     * @param existsSql sql语句
     * @param values    数据数组
     * @return children
     */
    Children notExists(boolean condition, String existsSql, Object... values);
}
