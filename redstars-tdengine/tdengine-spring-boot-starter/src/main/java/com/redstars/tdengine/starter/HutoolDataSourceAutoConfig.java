package com.redstars.tdengine.starter;

import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.ds.GlobalDSFactory;
import cn.hutool.setting.Setting;
import com.redstars.tdengine.api.TdengineDb;
import com.redstars.tdengine.core.autoconfig.HutoolDataSourceProperty;
import com.redstars.tdengine.core.autoconfig.HutoolDynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author : zhouhx
 * @date : 2023/5/5 11:25
 */
@Configuration
@EnableConfigurationProperties({HutoolDynamicDataSourceProperties.class})
@Slf4j
public class HutoolDataSourceAutoConfig implements ApplicationRunner {
    private final HutoolDynamicDataSourceProperties properties;

    public HutoolDataSourceAutoConfig(HutoolDynamicDataSourceProperties properties) {
        this.properties = properties;
    }

    public void init() throws SQLException {
        Map<String, HutoolDataSourceProperty> datasource = this.properties.getDatasource();

        if (!datasource.isEmpty()) {
            // 设置组名
            log.info("开始全局hutool数据源完毕--------------------------------");
            Setting data = new Setting();
            for (String key : datasource.keySet()) {
                Setting setting = this.properties.getHikari().covertSetting();
                setting.put("url", datasource.get(key).getUrl());
                setting.put("driver", datasource.get(key).getDriverClassName());
                setting.put("username", datasource.get(key).getUsername());
                setting.put("password", datasource.get(key).getPassword());

                data.putAll(key, setting);
                log.info("hutool数据源名称："+key);
            }
            GlobalDSFactory.set(DSFactory.create(data));
            log.info("设置全局hutool数据源完毕--------------------------------");
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.init();
    }

    @Bean
    public TdengineDb tdengineDb() {
        return new TdengineDb(this.properties.getPrimary());
    }
}
