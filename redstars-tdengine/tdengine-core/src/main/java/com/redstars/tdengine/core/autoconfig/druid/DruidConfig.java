package com.redstars.tdengine.core.autoconfig.druid;

import cn.hutool.setting.Setting;
import com.redstars.tdengine.core.autoconfig.InitSetting;
import lombok.Data;

import java.util.*;

/**
 * @author : zhouhx
 * Druid连接池配置
 * @date : 2023/5/5 10:09
 */
@Data
public class DruidConfig implements InitSetting {
    private Integer initialSize;
    private Integer maxActive;
    private Integer minIdle;
    private Integer maxWait;
    private Long timeBetweenEvictionRunsMillis;
    private Long timeBetweenLogStatsMillis;
    private Integer statSqlMaxSize;
    private Long minEvictableIdleTimeMillis;
    private Long maxEvictableIdleTimeMillis;
    private String defaultCatalog;
    private Boolean defaultAutoCommit;
    private Boolean defaultReadOnly;
    private Integer defaultTransactionIsolation;
    private Boolean testWhileIdle;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private String validationQuery;
    private Integer validationQueryTimeout;
    private Boolean useGlobalDataSourceStat;
    private Boolean asyncInit;
    private String filters;
    private Boolean clearFiltersEnable;
    private Boolean resetStatEnable;
    private Integer notFullTimeoutRetryCount;
    private Integer maxWaitThreadCount;
    private Boolean failFast;
    private Long phyTimeoutMillis;
    private Boolean keepAlive;
    private Boolean poolPreparedStatements;
    private Boolean initVariants;
    private Boolean initGlobalVariants;
    private Boolean useUnfairLock;
    private Boolean killWhenSocketReadTimeout;
    private Properties connectionProperties;
    private Integer maxPoolPreparedStatementPerConnectionSize;
    private String initConnectionSqls;
    private Boolean sharePreparedStatements;
    private Integer connectionErrorRetryAttempts;
    private Boolean breakAfterAcquireFailure;
    private Boolean removeAbandoned;
    private Integer removeAbandonedTimeoutMillis;
    private Boolean logAbandoned;
    private Integer queryTimeout;
    private Integer transactionQueryTimeout;
    private String publicKey;
    private Integer connectTimeout;
    private Integer socketTimeout;
    private Map<String, Object> wall = new HashMap();
    private Map<String, Object> slf4j = new HashMap();
    private Map<String, Object> log4j = new HashMap();
    private Map<String, Object> log4j2 = new HashMap();
    private Map<String, Object> commonsLog = new HashMap();
    private Map<String, Object> stat = new HashMap();
    private List<String> proxyFilters = new ArrayList();

    @Override
    public Setting covertSetting() {
        Setting properties = new Setting();
        if (initialSize != null && !initialSize.equals(0)) {
            properties.put("druid.initialSize", String.valueOf(initialSize));
        }

        if (maxActive != null && !maxActive.equals(-1)) {
            properties.put("druid.maxActive", String.valueOf(maxActive));
        }

        if (minIdle != null && !minIdle.equals(0)) {
            properties.put("druid.minIdle", String.valueOf(minIdle));
        }

        if (maxWait != null && !maxWait.equals(-1)) {
            properties.put("druid.maxWait", String.valueOf(maxWait));
        }

        if (timeBetweenEvictionRunsMillis != null && !timeBetweenEvictionRunsMillis.equals(60000L)) {
            properties.put("druid.timeBetweenEvictionRunsMillis", String.valueOf(timeBetweenEvictionRunsMillis));
        }

        if (timeBetweenLogStatsMillis != null && timeBetweenLogStatsMillis > 0L) {
            properties.put("druid.timeBetweenLogStatsMillis", String.valueOf(timeBetweenLogStatsMillis));
        }

        if (minEvictableIdleTimeMillis != null && !minEvictableIdleTimeMillis.equals(1800000L)) {
            properties.put("druid.minEvictableIdleTimeMillis", String.valueOf(minEvictableIdleTimeMillis));
        }

        if (maxEvictableIdleTimeMillis != null && !maxEvictableIdleTimeMillis.equals(25200000L)) {
            properties.put("druid.maxEvictableIdleTimeMillis", String.valueOf(maxEvictableIdleTimeMillis));
        }

        if (testWhileIdle != null && !testWhileIdle.equals(true)) {
            properties.put("druid.testWhileIdle", Boolean.FALSE.toString());
        }

        if (testOnBorrow != null && !testOnBorrow.equals(false)) {
            properties.put("druid.testOnBorrow", Boolean.TRUE.toString());
        }

        if (validationQuery != null && validationQuery.length() > 0) {
            properties.put("druid.validationQuery", validationQuery);
        }

        if (useGlobalDataSourceStat != null && useGlobalDataSourceStat.equals(Boolean.TRUE)) {
            properties.put("druid.useGlobalDataSourceStat", Boolean.TRUE.toString());
        }

        if (asyncInit != null && asyncInit.equals(Boolean.TRUE)) {
            properties.put("druid.asyncInit", Boolean.TRUE.toString());
        }

        if (filters == null) {
            filters = "stat";
        }

        if (this.publicKey != null && this.publicKey.length() > 0 && !filters.contains("config")) {
            filters = filters + ",config";
        }

        properties.put("druid.filters", filters);
        if (clearFiltersEnable != null && clearFiltersEnable.equals(Boolean.FALSE)) {
            properties.put("druid.clearFiltersEnable", Boolean.FALSE.toString());
        }

        if (resetStatEnable != null && resetStatEnable.equals(Boolean.FALSE)) {
            properties.put("druid.resetStatEnable", Boolean.FALSE.toString());
        }

        if (notFullTimeoutRetryCount != null && !notFullTimeoutRetryCount.equals(0)) {
            properties.put("druid.notFullTimeoutRetryCount", String.valueOf(notFullTimeoutRetryCount));
        }

        if (maxWaitThreadCount != null && !maxWaitThreadCount.equals(-1)) {
            properties.put("druid.maxWaitThreadCount", String.valueOf(maxWaitThreadCount));
        }

        if (failFast != null && failFast.equals(Boolean.TRUE)) {
            properties.put("druid.failFast", Boolean.TRUE.toString());
        }

        if (phyTimeoutMillis != null && !phyTimeoutMillis.equals(-1L)) {
            properties.put("druid.phyTimeoutMillis", String.valueOf(phyTimeoutMillis));
        }

        if (keepAlive != null && keepAlive.equals(Boolean.TRUE)) {
            properties.put("druid.keepAlive", Boolean.TRUE.toString());
        }

        if (poolPreparedStatements != null && poolPreparedStatements.equals(Boolean.TRUE)) {
            properties.put("druid.poolPreparedStatements", Boolean.TRUE.toString());
        }

        if (initVariants != null && initVariants.equals(Boolean.TRUE)) {
            properties.put("druid.initVariants", Boolean.TRUE.toString());
        }

        if (initGlobalVariants != null && initGlobalVariants.equals(Boolean.TRUE)) {
            properties.put("druid.initGlobalVariants", Boolean.TRUE.toString());
        }

        if (useUnfairLock != null) {
            properties.put("druid.useUnfairLock", String.valueOf(useUnfairLock));
        }

        if (killWhenSocketReadTimeout != null && killWhenSocketReadTimeout.equals(Boolean.TRUE)) {
            properties.put("druid.killWhenSocketReadTimeout", Boolean.TRUE.toString());
        }

        Properties connectProperties = this.connectionProperties;
        if (this.publicKey != null && this.publicKey.length() > 0) {
            if (connectProperties == null) {
                connectProperties = new Properties();
            }

            connectProperties.put("config.decrypt", Boolean.TRUE.toString());
            connectProperties.put("config.decrypt.key", this.publicKey);
        }

        this.connectionProperties = connectProperties;
        if (maxPoolPreparedStatementPerConnectionSize != null && !maxPoolPreparedStatementPerConnectionSize.equals(10)) {
            properties.put("druid.maxPoolPreparedStatementPerConnectionSize", String.valueOf(maxPoolPreparedStatementPerConnectionSize));
        }

        if (initConnectionSqls != null && initConnectionSqls.length() > 0) {
            properties.put("druid.initConnectionSqls", initConnectionSqls);
        }

        return properties;
    }
}
