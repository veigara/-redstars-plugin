package com.redstars.tdengine.core.autoconfig;

import lombok.Data;

import javax.sql.DataSource;

/**
 * @author : zhouhx
 * @date : 2023/5/5 10:00
 */
@Data
public class HutoolDataSourceProperty {
    private String poolName;
    private Class<? extends DataSource> type;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
