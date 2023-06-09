package com.redstars.tdengine.core.autoconfig.dbcp2;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.setting.Setting;
import com.redstars.tdengine.core.autoconfig.InitSetting;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author : zhouhx
 * Dbcp2连接池配置
 * @date : 2023/5/5 10:10
 */
@Data
public class Dbcp2Config implements InitSetting {
    private String defaultAutoCommit;
    private String defaultReadOnly;
    private String defaultTransactionIsolation;
    private String defaultQueryTimeoutSeconds;
    private String defaultCatalog;
    private String defaultSchema;
    private String cacheState;
    private String lifo;
    private String maxTotal;
    private String maxIdle;
    private String minIdle;
    private String initialSize;
    private String maxWaitMillis;
    private String poolPreparedStatements;
    private String clearStatementPoolOnReturn;
    private String maxOpenPreparedStatements;
    private String testOnCreate;
    private String testOnBorrow;
    private String testOnReturn;
    private String timeBetweenEvictionRunsMillis;
    private String numTestsPerEvictionRun;
    private String minEvictableIdleTimeMillis;
    private String softMinEvictableIdleTimeMillis;
    private String evictionPolicyClassName;
    private String testWhileIdle;
    private String validationQuery;
    private String validationQueryTimeoutSeconds;
    private String connectionFactoryClassName;
    private List<String> connectionInitSqls;
    private String accessToUnderlyingConnectionAllowed;
    private String maxConnLifetimeMillis;
    private String logExpiredConnections;
    private String jmxName;
    private String autoCommitOnReturn;
    private String rollbackOnReturn;
    private Set<String> disconnectionSqlCodes;
    private String fastFailValidation;
    private String connectionProperties;

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
    @Override
    public Setting covertSetting() {

        Setting setting = new Setting();
        if (ObjectUtil.isNotEmpty(defaultAutoCommit)) {
            setting.put("defaultAutoCommit", defaultAutoCommit);
        }
        if (ObjectUtil.isNotEmpty(defaultTransactionIsolation)) {
            setting.put("defaultTransactionIsolation", defaultTransactionIsolation);
        }
        if (ObjectUtil.isNotEmpty(defaultQueryTimeoutSeconds)) {
            setting.put("defaultQueryTimeoutSeconds", defaultQueryTimeoutSeconds);
        }
        if (ObjectUtil.isNotEmpty(defaultCatalog)) {
            setting.put("defaultCatalog", defaultCatalog);
        }
        if (ObjectUtil.isNotEmpty(defaultSchema)) {
            setting.put("defaultSchema", defaultSchema);
        }
        if (ObjectUtil.isNotEmpty(cacheState)) {
            setting.put("cacheState", cacheState);
        }
        if (ObjectUtil.isNotEmpty(lifo)) {
            setting.put("lifo", lifo);
        }
        if (ObjectUtil.isNotEmpty(maxTotal)) {
            setting.put("maxTotal", maxTotal);
        }
        if (ObjectUtil.isNotEmpty(maxIdle)) {
            setting.put("maxIdle", maxIdle);
        }
        if (ObjectUtil.isNotEmpty(minIdle)) {
            setting.put("minIdle", minIdle);
        }
        if (ObjectUtil.isNotEmpty(initialSize)) {
            setting.put("initialSize", initialSize);
        }
        if (ObjectUtil.isNotEmpty(maxWaitMillis)) {
            setting.put("maxWaitMillis", maxWaitMillis);
        }
        if (ObjectUtil.isNotEmpty(poolPreparedStatements)) {
            setting.put("poolPreparedStatements", poolPreparedStatements);
        }
        if (ObjectUtil.isNotEmpty(clearStatementPoolOnReturn)) {
            setting.put("clearStatementPoolOnReturn", clearStatementPoolOnReturn);
        }
        if (ObjectUtil.isNotEmpty(maxOpenPreparedStatements)) {
            setting.put("maxOpenPreparedStatements", maxOpenPreparedStatements);
        }
        if (ObjectUtil.isNotEmpty(testOnCreate)) {
            setting.put("testOnCreate", testOnCreate);
        }
        if (ObjectUtil.isNotEmpty(testOnBorrow)) {
            setting.put("testOnBorrow", testOnBorrow);
        }
        if (ObjectUtil.isNotEmpty(testOnReturn)) {
            setting.put("testOnReturn", testOnReturn);
        }
        if (ObjectUtil.isNotEmpty(timeBetweenEvictionRunsMillis)) {
            setting.put("timeBetweenEvictionRunsMillis", timeBetweenEvictionRunsMillis);
        }
        if (ObjectUtil.isNotEmpty(numTestsPerEvictionRun)) {
            setting.put("numTestsPerEvictionRun", numTestsPerEvictionRun);
        }
        if (ObjectUtil.isNotEmpty(minEvictableIdleTimeMillis)) {
            setting.put("minEvictableIdleTimeMillis", minEvictableIdleTimeMillis);
        }
        if (ObjectUtil.isNotEmpty(softMinEvictableIdleTimeMillis)) {
            setting.put("softMinEvictableIdleTimeMillis", softMinEvictableIdleTimeMillis);
        }
        if (ObjectUtil.isNotEmpty(evictionPolicyClassName)) {
            setting.put("evictionPolicyClassName", evictionPolicyClassName);
        }
        if (ObjectUtil.isNotEmpty(testWhileIdle)) {
            setting.put("testWhileIdle", testWhileIdle);
        }
        if (ObjectUtil.isNotEmpty(validationQuery)) {
            setting.put("validationQuery", validationQuery);
        }
        if (ObjectUtil.isNotEmpty(validationQueryTimeoutSeconds)) {
            setting.put("validationQueryTimeoutSeconds", validationQueryTimeoutSeconds);
        }
        if (ObjectUtil.isNotEmpty(connectionFactoryClassName)) {
            setting.put("connectionFactoryClassName", connectionFactoryClassName);
        }
        if (ObjectUtil.isNotEmpty(accessToUnderlyingConnectionAllowed)) {
            setting.put("accessToUnderlyingConnectionAllowed", accessToUnderlyingConnectionAllowed);
        }
        if (ObjectUtil.isNotEmpty(maxConnLifetimeMillis)) {
            setting.put("maxConnLifetimeMillis", maxConnLifetimeMillis);
        }
        if (ObjectUtil.isNotEmpty(logExpiredConnections)) {
            setting.put("logExpiredConnections", logExpiredConnections);
        }
        if (ObjectUtil.isNotEmpty(jmxName)) {
            setting.put("jmxName", jmxName);
        }
        if (ObjectUtil.isNotEmpty(autoCommitOnReturn)) {
            setting.put("autoCommitOnReturn", autoCommitOnReturn);
        }
        if (ObjectUtil.isNotEmpty(rollbackOnReturn)) {
            setting.put("rollbackOnReturn", rollbackOnReturn);
        }
        if (ObjectUtil.isNotEmpty(fastFailValidation)) {
            setting.put("fastFailValidation", fastFailValidation);
        }
        if (ObjectUtil.isNotEmpty(connectionProperties)) {
            setting.put("connectionProperties", connectionProperties);
        }
        return setting;
    }

}
