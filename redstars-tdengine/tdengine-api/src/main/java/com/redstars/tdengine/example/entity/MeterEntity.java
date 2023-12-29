package com.redstars.tdengine.example.entity;

import com.redstars.tdengine.core.annotation.TdengineSubTableName;
import com.redstars.tdengine.core.annotation.TdengineTableId;
import com.redstars.tdengine.core.annotation.TdengineTableName;
import com.redstars.tdengine.core.annotation.TdengineTableTag;
import lombok.Data;

import java.util.Date;

/**
 * @author : zhouhx
 * @date : 2023/3/29 10:26
 */
@Data
@TdengineTableName("meters")
public class MeterEntity {
    @TdengineTableId
    private Date ts;

    private Float current;

    private Long voltage;

    private Float phase;

    @TdengineSubTableName
    private String dbName;

    @TdengineTableTag
    private Integer groupid;

    @TdengineTableTag
    private String location;
}
