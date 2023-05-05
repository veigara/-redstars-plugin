package com.redstars.tdengine.test.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author : zhouhx
 * @date : 2023/3/29 10:26
 */
@Data
public class MeterEntity {
    private Date ts;

    private Long current;

    private Long voltage;

    private Double phase;

    private Integer groupId;

    private String location;
}
