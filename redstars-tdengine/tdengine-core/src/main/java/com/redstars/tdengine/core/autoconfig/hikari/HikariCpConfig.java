package com.redstars.tdengine.core.autoconfig.hikari;


import lombok.Data;
import java.util.Properties;

/**
 * @author : zhouhx
 * Hikari连接池配置
 * @date : 2023/5/5 10:08
 */
@Data
public class HikariCpConfig{
    private String catalog;
    private Long connectionTimeout;
    private Long validationTimeout;
    private Long idleTimeout;
    private Long leakDetectionThreshold;
    private Long maxLifetime;
    private Integer maxPoolSize;
    private Integer maximumPoolSize;
    private Integer minIdle;
    private Integer minimumIdle;

    private Long initializationFailTimeout;
    private String connectionInitSql;
    private String connectionTestQuery;
    private String dataSourceClassName;
    private String dataSourceJndiName;
    private String transactionIsolationName;
    private Boolean isAutoCommit;
    private Boolean isReadOnly;
    private Boolean isIsolateInternalQueries;
    private Boolean isRegisterMbeans;
    private Boolean isAllowPoolSuspension;
    private Properties dataSourceProperties;
    private Properties healthCheckProperties;

    /**
     * 高版本才有
     */
    private String schema;
    private String exceptionOverrideClassName;
    private Long keepaliveTime;
    private Boolean sealed;

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maxPoolSize = maximumPoolSize;
    }

    public void setMinimumIdle(Integer minimumIdle) {
        this.minIdle = minimumIdle;
    }
}
