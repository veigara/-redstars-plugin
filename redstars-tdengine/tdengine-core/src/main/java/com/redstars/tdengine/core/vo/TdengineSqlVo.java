package com.redstars.tdengine.core.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author : zhouhx
 * @date : 2023/6/12 18:56
 */
@Data
@AllArgsConstructor
public class TdengineSqlVo {
    /**
     * sql语句
     */
    private String sql;

    /**
     * 字段值集合
     */
    private List<Object> columnValeList;
}
