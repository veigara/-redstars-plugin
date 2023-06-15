package com.redstars.tdengine.core.toolkit;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.db.Page;
import com.alibaba.fastjson.JSON;
import com.redstars.tdengine.core.annotation.TdengineCovert;
import com.redstars.tdengine.core.common.PageResult;
import com.redstars.tdengine.core.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author : zhouhx
 * 结果处理工具
 * @date : 2023/6/12 15:12
 */
@Slf4j
public class TdengineResultHelper {
    public  static  int getStart(Page page) {
        int firstPageNo = 1;
        int pageSize = page.getPageSize();
        int pageNo = page.getPageNumber();
        if (pageSize < 1) {
            pageSize = 1;
        }

        return (pageNo - firstPageNo) * pageSize;
    }

    /**
     *
     * @author zhuohx
     * @description 设置分页数据
     * @parms  [page, totalSize 总数, list 结果集]
     * @return com.hndk.common.page.PageResult<T>
     * @throws
     * @date 2023/3/6 10:02
     */
    public static <T> PageResult<T> setPageData(Page page, int totalSize, List<T> list) {
        PageResult<T> result = new PageResult<>();
        result.setList(list);
        result.setTotal(totalSize);
        result.setPageNo(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotalPage(PageUtil.totalPage(totalSize, page.getPageSize()));
        return result;
    }

    /**
     * 转换目标对象集合
     * 处理Tdengine数据库中的转换，byte[]类型自动转换成String
     * @author zhuohx
     * @param  t 原始数据
     * @param  beanClass  转换对象
     * @return java.util.List<T>
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:57
     */
    public static  <T> List<T> covertToList(List<T> t, Class<T> beanClass) {
        if (CollUtil.isNotEmpty(t)) {
            t.forEach(item -> {
                covertObj(item, beanClass);
            });
        }
        ;

        return t;
    }

    /**
     *
     * 转换目标对象
     * @author zhuohx
     * @param  t 原始数据
     * @param  beanClass  转换对象
     * @return T
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:59
     */
    public static  <T> T covertObj(T t, Class<T> beanClass) {
        if (ObjectUtil.isNotEmpty(t)) {
            Field[] fields = beanClass.getDeclaredFields();
            Arrays.stream(fields).forEach(field -> {
                TdengineCovert tdengineCovert = field.getAnnotation(TdengineCovert.class);
                if (ObjectUtil.isNotEmpty(tdengineCovert)) {
                    //将byte[]json字符串解析为string
                    String name = field.getName();
                    Class<?> itemClass = t.getClass();
                    try {
                        Field classField = itemClass.getDeclaredField(name);
                        classField.setAccessible(true);
                        Object value = classField.get(t);
                        if (ObjectUtil.isNotEmpty(value)) {
                            // 将json类型的byte[]转换成string
                            try {
                                List<Byte> bytes = JSON.parseArray(value.toString(), Byte.class);
                                String s = StrUtil.ascii2Str(bytes.toArray(new Byte[bytes.size()]));
                                classField.set(t, s);
                            } catch (Exception e) {

                            }
                        }

                    } catch (Exception e) {
                        log.error("转换字段类型异常", e);
                    }
                }
            });
        }
        return t;
    }

}
