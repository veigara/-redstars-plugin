package com.redstars.tdengine.core.entity;

import lombok.Data;

import java.util.List;

/**
 * @author : zhouhx
 * @date : 2023/6/12 15:48
 */
@Data
public class TdengineTableInfo {
    /**
     * 表名
     */
    private String talbleName;

    /**
     * 子表名
     */
    private String subTalbleName;

    /**
     * 超级表字段
     */
    private List<String> tableTagColumn;

    /**
     * 超级表字段值
     */
    private List<Object> tableTagColumnValue;

    /**
     *
     * 数据表名字段
     */
    private List<String> columnList;


    /**
     *
     * 数据表名字段值
     */
    private List<Object> columnValueList;
}
