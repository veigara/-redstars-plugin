package com.redstars.tdengine.core.enums;

import lombok.Getter;

/**
 * @author : zhouhx
 * @date : 2023/6/14 13:52
 */
@Getter
public enum SqlTypeEnum {
    SAVE(1,"INSERT INTO %s USING %s  tags(%s) VALUES(%s)"),
    /**
     * select 查询语句
     */
    SELECT(2,"select %s from %s %s"),
    /**
     * delete 删除语句
     */
    DELETE(3,"delete from %s %s"),
    /**
     * count 语句
     */
    COUNT(4,"select count(*) from %s %s");

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
