package com.redstars.tdengine.core.enums;

import lombok.Getter;

/**
 * @author : zhouhx
 * @date : 2023/6/14 13:52
 */
@Getter
public enum SqlTypeEnum {
    /**
     * select 查询语句
     */
    SELECT(1,"select 查询语句"),
    /**
     * delete 删除语句
     */
    DELETE(2,"delete 删除语句");

    /**
     * 类型
     */
    private int type;
    /**
     * 说明
     */
    private String text;

    SqlTypeEnum(int type, String text){
        this.type = type;
        this.text = text;
    }
}
