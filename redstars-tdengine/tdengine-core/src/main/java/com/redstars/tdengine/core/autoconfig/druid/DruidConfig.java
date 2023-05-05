package com.redstars.tdengine.core.autoconfig.druid;

import cn.hutool.setting.Setting;
import lombok.Data;

import java.util.*;

/**
 * @author : zhouhx
 * Druid连接池配置
 * @date : 2023/5/5 10:09
 */
@Data
public class DruidConfig {
    private String initialSize;
    private String maxActive;
    private String minIdle;
    private String maxWait;
    private String timeBetweenEvictionRunsMillis;
    private String timeBetweenLogStatsMillis;
    private String statSqlMaxSize;
    private String minEvictableIdleTimeMillis;
    private String maxEvictableIdleTimeMillis;
    private String defaultCatalog;
    private String defaultAutoCommit;
    private String defaultReadOnly;
    private String defaultTransactionIsolation;
    private String testWhileIdle;
    private String testOnBorrow;
    private String testOnReturn;
    private String validationQuery;
    private String validationQueryTimeout;
    private String useGlobalDataSourceStat;
    private String asyncInit;
    private String filters;
    private String clearFiltersEnable;
    private String resetStatEnable;
    private String notFullTimeoutRetryCount;
    private String maxWaitThreadCount;
    private String failFast;
    private String phyTimeoutMillis;
    private String keepAlive;
    private String poolPreparedStatements;
    private String initVariants;
    private String initGlobalVariants;
    private String useUnfairLock;
    private String killWhenSocketReadTimeout;
    private String connectionProperties;
    private String maxPoolPreparedStatementPerConnectionSize;
    private String initConnectionSqls;
    private String sharePreparedStatements;
    private String connectionErrorRetryAttempts;
    private String breakAfterAcquireFailure;
    private String removeAbandoned;
    private String removeAbandonedTimeoutMillis;
    private String logAbandoned;
    private String queryTimeout;
    private String transactionQueryTimeout;
    private String publicKey;
    private String connectTimeout;
    private String socketTimeout;
    private Map<String, Object> wall = new HashMap();
    private Map<String, Object> slf4j = new HashMap();
    private Map<String, Object> log4j = new HashMap();
    private Map<String, Object> log4j2 = new HashMap();
    private Map<String, Object> commonsLog = new HashMap();
    private Map<String, Object> stat = new HashMap();
    private List<String> proxyFilters = new ArrayList();

//    public Properties toProperties(DruidConfig g) {
//        Properties properties = new Properties();
//        Integer initialSize = this.initialSize == null ? g.getInitialSize() : this.initialSize;
//        if (initialSize != null && !initialSize.equals(0)) {
//            properties.setProperty("druid.initialSize", String.valueOf(initialSize));
//        }
//
//        Integer maxActive = this.maxActive == null ? g.getMaxActive() : this.maxActive;
//        if (maxActive != null && !maxActive.equals(-1)) {
//            properties.setProperty("druid.maxActive", String.valueOf(maxActive));
//        }
//
//        Integer minIdle = this.minIdle == null ? g.getMinIdle() : this.minIdle;
//        if (minIdle != null && !minIdle.equals(0)) {
//            properties.setProperty("druid.minIdle", String.valueOf(minIdle));
//        }
//
//        Integer maxWait = this.maxWait == null ? g.getMaxWait() : this.maxWait;
//        if (maxWait != null && !maxWait.equals(-1)) {
//            properties.setProperty("druid.maxWait", String.valueOf(maxWait));
//        }
//
//        Long timeBetweenEvictionRunsMillis = this.timeBetweenEvictionRunsMillis == null ? g.getTimeBetweenEvictionRunsMillis() : this.timeBetweenEvictionRunsMillis;
//        if (timeBetweenEvictionRunsMillis != null && !timeBetweenEvictionRunsMillis.equals(60000L)) {
//            properties.setProperty("druid.timeBetweenEvictionRunsMillis", String.valueOf(timeBetweenEvictionRunsMillis));
//        }
//
//        Long timeBetweenLogStatsMillis = this.timeBetweenLogStatsMillis == null ? g.getTimeBetweenLogStatsMillis() : this.timeBetweenLogStatsMillis;
//        if (timeBetweenLogStatsMillis != null && timeBetweenLogStatsMillis > 0L) {
//            properties.setProperty("druid.timeBetweenLogStatsMillis", String.valueOf(timeBetweenLogStatsMillis));
//        }
//
//        Long minEvictableIdleTimeMillis = this.minEvictableIdleTimeMillis == null ? g.getMinEvictableIdleTimeMillis() : this.minEvictableIdleTimeMillis;
//        if (minEvictableIdleTimeMillis != null && !minEvictableIdleTimeMillis.equals(1800000L)) {
//            properties.setProperty("druid.minEvictableIdleTimeMillis", String.valueOf(minEvictableIdleTimeMillis));
//        }
//
//        Long maxEvictableIdleTimeMillis = this.maxEvictableIdleTimeMillis == null ? g.getMaxEvictableIdleTimeMillis() : this.maxEvictableIdleTimeMillis;
//        if (maxEvictableIdleTimeMillis != null && !maxEvictableIdleTimeMillis.equals(25200000L)) {
//            properties.setProperty("druid.maxEvictableIdleTimeMillis", String.valueOf(maxEvictableIdleTimeMillis));
//        }
//
//        Boolean testWhileIdle = this.testWhileIdle == null ? g.getTestWhileIdle() : this.testWhileIdle;
//        if (testWhileIdle != null && !testWhileIdle.equals(true)) {
//            properties.setProperty("druid.testWhileIdle", Boolean.FALSE.toString());
//        }
//
//        Boolean testOnBorrow = this.testOnBorrow == null ? g.getTestOnBorrow() : this.testOnBorrow;
//        if (testOnBorrow != null && !testOnBorrow.equals(false)) {
//            properties.setProperty("druid.testOnBorrow", Boolean.TRUE.toString());
//        }
//
//        String validationQuery = this.validationQuery == null ? g.getValidationQuery() : this.validationQuery;
//        if (validationQuery != null && validationQuery.length() > 0) {
//            properties.setProperty("druid.validationQuery", validationQuery);
//        }
//
//        Boolean useGlobalDataSourceStat = this.useGlobalDataSourceStat == null ? g.getUseGlobalDataSourceStat() : this.useGlobalDataSourceStat;
//        if (useGlobalDataSourceStat != null && useGlobalDataSourceStat.equals(Boolean.TRUE)) {
//            properties.setProperty("druid.useGlobalDataSourceStat", Boolean.TRUE.toString());
//        }
//
//        Boolean asyncInit = this.asyncInit == null ? g.getAsyncInit() : this.asyncInit;
//        if (asyncInit != null && asyncInit.equals(Boolean.TRUE)) {
//            properties.setProperty("druid.asyncInit", Boolean.TRUE.toString());
//        }
//
//        String filters = this.filters == null ? g.getFilters() : this.filters;
//        if (filters == null) {
//            filters = "stat";
//        }
//
//        if (this.publicKey != null && this.publicKey.length() > 0 && !filters.contains("config")) {
//            filters = filters + ",config";
//        }
//
//        properties.setProperty("druid.filters", filters);
//        Boolean clearFiltersEnable = this.clearFiltersEnable == null ? g.getClearFiltersEnable() : this.clearFiltersEnable;
//        if (clearFiltersEnable != null && clearFiltersEnable.equals(Boolean.FALSE)) {
//            properties.setProperty("druid.clearFiltersEnable", Boolean.FALSE.toString());
//        }
//
//        Boolean resetStatEnable = this.resetStatEnable == null ? g.getResetStatEnable() : this.resetStatEnable;
//        if (resetStatEnable != null && resetStatEnable.equals(Boolean.FALSE)) {
//            properties.setProperty("druid.resetStatEnable", Boolean.FALSE.toString());
//        }
//
//        Integer notFullTimeoutRetryCount = this.notFullTimeoutRetryCount == null ? g.getNotFullTimeoutRetryCount() : this.notFullTimeoutRetryCount;
//        if (notFullTimeoutRetryCount != null && !notFullTimeoutRetryCount.equals(0)) {
//            properties.setProperty("druid.notFullTimeoutRetryCount", String.valueOf(notFullTimeoutRetryCount));
//        }
//
//        Integer maxWaitThreadCount = this.maxWaitThreadCount == null ? g.getMaxWaitThreadCount() : this.maxWaitThreadCount;
//        if (maxWaitThreadCount != null && !maxWaitThreadCount.equals(-1)) {
//            properties.setProperty("druid.maxWaitThreadCount", String.valueOf(maxWaitThreadCount));
//        }
//
//        Boolean failFast = this.failFast == null ? g.getFailFast() : this.failFast;
//        if (failFast != null && failFast.equals(Boolean.TRUE)) {
//            properties.setProperty("druid.failFast", Boolean.TRUE.toString());
//        }
//
//        Long phyTimeoutMillis = this.phyTimeoutMillis == null ? g.getPhyTimeoutMillis() : this.phyTimeoutMillis;
//        if (phyTimeoutMillis != null && !phyTimeoutMillis.equals(-1L)) {
//            properties.setProperty("druid.phyTimeoutMillis", String.valueOf(phyTimeoutMillis));
//        }
//
//        Boolean keepAlive = this.keepAlive == null ? g.getKeepAlive() : this.keepAlive;
//        if (keepAlive != null && keepAlive.equals(Boolean.TRUE)) {
//            properties.setProperty("druid.keepAlive", Boolean.TRUE.toString());
//        }
//
//        Boolean poolPreparedStatements = this.poolPreparedStatements == null ? g.getPoolPreparedStatements() : this.poolPreparedStatements;
//        if (poolPreparedStatements != null && poolPreparedStatements.equals(Boolean.TRUE)) {
//            properties.setProperty("druid.poolPreparedStatements", Boolean.TRUE.toString());
//        }
//
//        Boolean initVariants = this.initVariants == null ? g.getInitVariants() : this.initVariants;
//        if (initVariants != null && initVariants.equals(Boolean.TRUE)) {
//            properties.setProperty("druid.initVariants", Boolean.TRUE.toString());
//        }
//
//        Boolean initGlobalVariants = this.initGlobalVariants == null ? g.getInitGlobalVariants() : this.initGlobalVariants;
//        if (initGlobalVariants != null && initGlobalVariants.equals(Boolean.TRUE)) {
//            properties.setProperty("druid.initGlobalVariants", Boolean.TRUE.toString());
//        }
//
//        Boolean useUnfairLock = this.useUnfairLock == null ? g.getUseUnfairLock() : this.useUnfairLock;
//        if (useUnfairLock != null) {
//            properties.setProperty("druid.useUnfairLock", String.valueOf(useUnfairLock));
//        }
//
//        Boolean killWhenSocketReadTimeout = this.killWhenSocketReadTimeout == null ? g.getKillWhenSocketReadTimeout() : this.killWhenSocketReadTimeout;
//        if (killWhenSocketReadTimeout != null && killWhenSocketReadTimeout.equals(Boolean.TRUE)) {
//            properties.setProperty("druid.killWhenSocketReadTimeout", Boolean.TRUE.toString());
//        }
//
//        Properties connectProperties = this.connectionProperties == null ? g.getConnectionProperties() : this.connectionProperties;
//        if (this.publicKey != null && this.publicKey.length() > 0) {
//            if (connectProperties == null) {
//                connectProperties = new Properties();
//            }
//
//            connectProperties.setProperty("config.decrypt", Boolean.TRUE.toString());
//            connectProperties.setProperty("config.decrypt.key", this.publicKey);
//        }
//
//        this.connectionProperties = connectProperties;
//        Integer maxPoolPreparedStatementPerConnectionSize = this.maxPoolPreparedStatementPerConnectionSize == null ? g.getMaxPoolPreparedStatementPerConnectionSize() : this.maxPoolPreparedStatementPerConnectionSize;
//        if (maxPoolPreparedStatementPerConnectionSize != null && !maxPoolPreparedStatementPerConnectionSize.equals(10)) {
//            properties.setProperty("druid.maxPoolPreparedStatementPerConnectionSize", String.valueOf(maxPoolPreparedStatementPerConnectionSize));
//        }
//
//        String initConnectionSqls = this.initConnectionSqls == null ? g.getInitConnectionSqls() : this.initConnectionSqls;
//        if (initConnectionSqls != null && initConnectionSqls.length() > 0) {
//            properties.setProperty("druid.initConnectionSqls", initConnectionSqls);
//        }
//
//        return properties;
//    }

    /**
     *
     *转换成配置文件
     * @author zhuohx
     * @param
     * @return cn.hutool.setting.Setting
     * @throws
     * @version 1.0
     * @since  2023/5/5 10:46
     */
    public Setting covertSetting(){
        Setting setting = new Setting();

        return setting;
    }
}
