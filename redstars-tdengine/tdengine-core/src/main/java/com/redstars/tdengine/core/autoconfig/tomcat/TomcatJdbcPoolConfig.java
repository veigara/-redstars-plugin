package com.redstars.tdengine.core.autoconfig.tomcat;

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
public class TomcatJdbcPoolConfig implements InitSetting {
    private String defaultAutoCommit;
    private String defaultReadOnly;
    private String defaultTransactionIsolation;
    private String initialSize;
    private String maxActive;
    private String maxIdle;
    private String minIdle;
    private String maxWait;
    private String validationQuery;
    private String testOnBorrow;
    private String testOnReturn;
    private String testWhileIdle;

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
        if (ObjectUtil.isNotEmpty(defaultReadOnly)) {
            setting.put("defaultReadOnly", defaultReadOnly);
        }
        if (ObjectUtil.isNotEmpty(initialSize)) {
            setting.put("initialSize", initialSize);
        }
        if (ObjectUtil.isNotEmpty(maxActive)) {
            setting.put("maxActive", maxActive);
        }
        if (ObjectUtil.isNotEmpty(maxIdle)) {
            setting.put("maxIdle", maxIdle);
        }
        if (ObjectUtil.isNotEmpty(minIdle)) {
            setting.put("minIdle", minIdle);
        }
        if (ObjectUtil.isNotEmpty(validationQuery)) {
            setting.put("validationQuery", validationQuery);
        }
        if (ObjectUtil.isNotEmpty(testOnBorrow)) {
            setting.put("testOnBorrow", testOnBorrow);
        }
        if (ObjectUtil.isNotEmpty(testOnReturn)) {
            setting.put("testOnReturn", testOnReturn);
        }
        if (ObjectUtil.isNotEmpty(testWhileIdle)) {
            setting.put("testWhileIdle", testWhileIdle);
        }
        return setting;
    }

}
