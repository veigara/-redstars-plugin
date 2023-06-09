package com.redstars.tdengine.core.autoconfig.hikari;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.setting.Setting;
import com.redstars.tdengine.core.autoconfig.InitSetting;
import lombok.Data;

/**
 * @author : zhouhx
 * Hikari连接池配置
 * @date : 2023/5/5 10:08
 */
@Data
public class HikariCpConfig implements InitSetting {
    private String catalog;
    private String connectionTimeout;
    private String validationTimeout;
    private String idleTimeout;
    private String leakDetectionThreshold;
    private String maxLifetime;
    private String maxPoolSize;
    private String maximumPoolSize;
    private String minIdle;
    private String minimumIdle;
    private String initializationFailTimeout;
    private String connectionInitSql;
    private String connectionTestQuery;
    private String dataSourceClassName;
    private String dataSourceJndiName;
    private String transactionIsolationName;
    private String autoCommit;
    private String readOnly;
    private String isolateInternalQueries;
    private String registerMbeans;
    private String allowPoolSuspension;
    private String dataSourceProperties;
    private String healthCheckProperties;
    private String schema;
    private String exceptionOverrideClassName;
    private String keepaliveTime;
    private String sealed;

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
    public Setting  covertSetting(){

        Setting setting = new Setting();

        if(ObjectUtil.isNotEmpty(catalog)){
            setting.put("catalog",catalog);
        }
        if(ObjectUtil.isNotEmpty(connectionTimeout)){
            setting.put("connectionTimeout",connectionTimeout);
        }
        if(ObjectUtil.isNotEmpty(validationTimeout)){
            setting.put("validationTimeout",validationTimeout);
        }
        if(ObjectUtil.isNotEmpty(idleTimeout)){
            setting.put("idleTimeout",idleTimeout);
        }
        if(ObjectUtil.isNotEmpty(leakDetectionThreshold)){
            setting.put("leakDetectionThreshold",leakDetectionThreshold);
        }
        if(ObjectUtil.isNotEmpty(maxLifetime)){
            setting.put("maxLifetime",maxLifetime);
        }
        if(ObjectUtil.isNotEmpty(maxPoolSize)){
            setting.put("maxPoolSize",maxPoolSize);
        }
        if(ObjectUtil.isNotEmpty(maximumPoolSize)){
            setting.put("maximumPoolSize",maximumPoolSize);
        }
        if(ObjectUtil.isNotEmpty(minIdle)){
            setting.put("minIdle",minIdle);
        }
        if(ObjectUtil.isNotEmpty(minimumIdle)){
            setting.put("minimumIdle",minimumIdle);
        }
        if(ObjectUtil.isNotEmpty(initializationFailTimeout)){
            setting.put("initializationFailTimeout",initializationFailTimeout);
        }
        if(ObjectUtil.isNotEmpty(connectionInitSql)){
            setting.put("connectionInitSql",connectionInitSql);
        }
        if(ObjectUtil.isNotEmpty(connectionTestQuery)){
            setting.put("connectionTestQuery",connectionTestQuery);
        }
        if(ObjectUtil.isNotEmpty(dataSourceClassName)){
            setting.put("dataSourceClassName",dataSourceClassName);
        }
        if(ObjectUtil.isNotEmpty(dataSourceJndiName)){
            setting.put("dataSourceJndiName",dataSourceJndiName);
        }
        if(ObjectUtil.isNotEmpty(transactionIsolationName)){
            setting.put("transactionIsolationName",transactionIsolationName);
        }
        if(ObjectUtil.isNotEmpty(autoCommit)){
            setting.put("autoCommit",autoCommit);
        }
        if(ObjectUtil.isNotEmpty(readOnly)){
            setting.put("readOnly",readOnly);
        }
        if(ObjectUtil.isNotEmpty(isolateInternalQueries)){
            setting.put("isolateInternalQueries",isolateInternalQueries);
        }
        if(ObjectUtil.isNotEmpty(registerMbeans)){
            setting.put("registerMbeans",registerMbeans);
        }
        if(ObjectUtil.isNotEmpty(allowPoolSuspension)){
            setting.put("allowPoolSuspension",allowPoolSuspension);
        }
        if(ObjectUtil.isNotEmpty(dataSourceProperties)){
            setting.put("dataSourceProperties",dataSourceProperties);
        }
        if(ObjectUtil.isNotEmpty(healthCheckProperties)){
            setting.put("healthCheckProperties",healthCheckProperties);
        }
        if(ObjectUtil.isNotEmpty(schema)){
            setting.put("schema",schema);
        }
        if(ObjectUtil.isNotEmpty(exceptionOverrideClassName)){
            setting.put("exceptionOverrideClassName",exceptionOverrideClassName);
        }
        if(ObjectUtil.isNotEmpty(keepaliveTime)){
            setting.put("keepaliveTime",keepaliveTime);
        }
        if(ObjectUtil.isNotEmpty(sealed)){
            setting.put("sealed",sealed);
        }
        return setting;
    }
}
