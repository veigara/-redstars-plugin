package com.redstars.tdengine.core.toolkit;

import cn.hutool.core.util.ObjectUtil;
import com.redstars.tdengine.core.annotation.TdengineSubTableName;
import com.redstars.tdengine.core.annotation.TdengineTableId;
import com.redstars.tdengine.core.annotation.TdengineTableName;
import com.redstars.tdengine.core.annotation.TdengineTableTag;
import com.redstars.tdengine.core.entity.TdengineTableInfo;
import com.redstars.tdengine.core.utils.StrUtil;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author : zhouhx
 * @date : 2023/6/12 15:45
 */
public class TdengineTableHelper {
    /**
     * 缓存表信息 key为包名+类名
     */
    public static HashMap<String, TdengineTableInfo> tableInfCache = new HashMap<>(8);

    /**
     *
     * 转为数据对象
     * @author zhuohx
     * @param   obj 实体类数据
     * @return com.redstars.tdengine.core.entity.TdengineTableInfo
     * @throws
     * @version 1.0
     * @since  2023/6/12 16:28
     */
    public static TdengineTableInfo getTableInfo(Class<?> classObj,Object obj) throws IllegalAccessException {
        TdengineTableInfo tdengineTableInfo = tableInfCache.get(classObj.getName());
        if(ObjectUtil.isNotEmpty(tdengineTableInfo)){
            return tdengineTableInfo;
        }
        TdengineTableInfo tableInfo = new TdengineTableInfo();

        Field[] fields = classObj.getDeclaredFields();

        // 获取表名
        TdengineTableName table = classObj.getAnnotation(TdengineTableName.class);
        if (table != null) {
            String tableName = table.value();
            tableInfo.setTalbleName(tableName);
        }
        ArrayList<String> tagColumn = new ArrayList<>();
        ArrayList<Object> tagColumnValue = new ArrayList<>();
        ArrayList<String> column = new ArrayList<>();
        ArrayList<Object> columnValue = new ArrayList<>();
        for (Field field : fields) {
            //在类的外面获取此类的私有成员变量的value时需配置 否则会抛异常
            field.setAccessible(true);

            boolean isTagField = field.isAnnotationPresent(TdengineTableTag.class);
            // 子表名
            boolean isSubTableNameField = field.isAnnotationPresent(TdengineSubTableName.class);
            boolean isTableNameIdField = field.isAnnotationPresent(TdengineTableId.class);
            if(isTagField){
                tagColumn.add(StrUtil.toUnderlineCase(field.getName()));
            }else if(isTableNameIdField){
                column.add(StrUtil.toUnderlineCase(field.getName()));
            }else if(!isSubTableNameField){
                column.add(StrUtil.toUnderlineCase(field.getName()));
            }

            if(ObjectUtil.isNotEmpty(obj)){
                Object value = field.get(obj);
                if(isTagField){
                    if(value instanceof String){
                        tagColumnValue.add(String.format("'%s'",value));
                    }else{
                        tagColumnValue.add(value);
                    }
                }else if(isSubTableNameField && ObjectUtil.isNotEmpty(value)){
                    tableInfo.setSubTalbleName(value.toString());
                }else{
                    if(isTableNameIdField && value instanceof Date){
                        // date 类型的时间戳主键自动转为long类型
                        columnValue.add(((Date) value).getTime());
                    }else{
                        columnValue.add(value);
                    }
                }
            }
        }
        tableInfo.setTableTagColumn(tagColumn);
        tableInfo.setTableTagColumnValue(tagColumnValue);
        tableInfo.setColumnList(column);
        tableInfo.setColumnValueList(columnValue);

        tableInfCache.put(classObj.getName(),tableInfo);

        return tableInfo;
    }

    /**
     *
     * 获取数据表名
     * @author zhuohx
     * @param   classObj 实体类class
     * @return java.lang.String
     * @throws
     * @version 1.0
     * @since  2023/6/15 10:20
     */
    public static  String getTableName(Class<?> classObj){
        // 获取表名
        TdengineTableName table = classObj.getAnnotation(TdengineTableName.class);
        if (table != null) {
            String tableName = table.value();

            return tableName;
        }
        return null;
    }

}
