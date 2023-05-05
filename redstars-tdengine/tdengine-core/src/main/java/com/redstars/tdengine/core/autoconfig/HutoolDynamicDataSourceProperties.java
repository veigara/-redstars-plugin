package com.redstars.tdengine.core.autoconfig;

import com.redstars.tdengine.core.autoconfig.dbcp2.Dbcp2Config;
import com.redstars.tdengine.core.autoconfig.druid.DruidConfig;
import com.redstars.tdengine.core.autoconfig.hikari.HikariCpConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : zhouhx
 * @date : 2023/5/5 10:02
 */
@ConfigurationProperties(prefix = "hutool.datasource.dynamic")
@Data
@Configuration
// 找配置文件 先找jar包平级目录的 先加载.properties 找不到就找.yml 找不到再找classpath下的ignoreResourceNotFound=true是让他找不到文件的时候不报错继续往下找
@PropertySource(value = { "*:application.properties", "classpath:application.properties"}, ignoreResourceNotFound = true)
public class HutoolDynamicDataSourceProperties {
    public static final String PREFIX = "spring.datasource.dynamic";
    /**
     默认数据源名称
     */
    private String primary;
    private Map<String, HutoolDataSourceProperty> datasource;
    @NestedConfigurationProperty
    private DruidConfig druid;
    @NestedConfigurationProperty
    private HikariCpConfig hikari;
    @NestedConfigurationProperty
    private Dbcp2Config dbcp2;

    public HutoolDynamicDataSourceProperties() {
        this.datasource = new LinkedHashMap();
        this.druid = new DruidConfig();
        this.hikari = new HikariCpConfig();
        this.dbcp2 = new Dbcp2Config();
    }
}
