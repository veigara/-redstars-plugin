package com.redstars.tdengine.starter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.ds.dbcp.DbcpDSFactory;
import cn.hutool.db.ds.druid.DruidDSFactory;
import cn.hutool.db.ds.hikari.HikariDSFactory;
import cn.hutool.setting.Setting;
import com.redstars.tdengine.api.TdengineDb;
import com.redstars.tdengine.core.autoconfig.HutoolDataSourceProperty;
import com.redstars.tdengine.core.autoconfig.HutoolDynamicDataSourceProperties;
import com.redstars.tdengine.core.autoconfig.TdengineDataSource;
import com.redstars.tdengine.core.autoconfig.dbcp2.Dbcp2Config;
import com.redstars.tdengine.core.autoconfig.druid.DruidConfig;
import com.redstars.tdengine.core.autoconfig.hikari.HikariCpConfig;
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
        HikariCpConfig hikari = this.properties.getHikari();
        DruidConfig druid = this.properties.getDruid();
        Dbcp2Config dbcp2 = this.properties.getDbcp2();

        if (!datasource.isEmpty()) {
            log.info("开始配置全局tdengine数据源--------------------------------");
            // 设置组名
            Setting data = new Setting();
            Setting poolSetting = null;
            if(ObjectUtil.isNotEmpty(hikari)){
                log.info("设置tdengine连接池：HikariCp--------------------------------");
                poolSetting = hikari.covertSetting();
            }
            if(ObjectUtil.isNotEmpty(druid)){
                log.info("设置tdengine连接池：Druid--------------------------------");
                poolSetting = druid.covertSetting();
            }
            if(ObjectUtil.isNotEmpty(dbcp2)){
                log.info("设置tdengine连接池：Dbcp2--------------------------------");
                poolSetting = dbcp2.covertSetting();
            }
            if(ObjectUtil.isEmpty(poolSetting)){
                log.info("设置tdengine默认连接池：HikariCp--------------------------------");
                poolSetting = new HikariCpConfig().covertSetting();
            }
            for (String key : datasource.keySet()) {
                Setting setting = BeanUtil.copyProperties(poolSetting,Setting.class);
                setting.put("url", datasource.get(key).getUrl());
                setting.put("driver", datasource.get(key).getDriverClassName());
                setting.put("username", datasource.get(key).getUsername());
                setting.put("password", datasource.get(key).getPassword());

                DSFactory dsFactory = null;
                if(ObjectUtil.isNotEmpty(hikari)){
                    dsFactory = new HikariDSFactory(setting);
                }
                if(ObjectUtil.isNotEmpty(druid)){
                    dsFactory = new DruidDSFactory(setting);
                }
                if(ObjectUtil.isNotEmpty(dbcp2)){
                    dsFactory = new DbcpDSFactory(setting);
                }
                if(ObjectUtil.isEmpty(poolSetting)){
                    dsFactory = new HikariDSFactory(setting);
                }
                TdengineDataSource.addDataSource(key,dsFactory);

                log.info("tdengine数据源名称："+key);


            }
            TdengineDataSource.addPrimaryDsName(this.properties.getPrimary());
            log.info("tdengine默认数据源名称："+this.properties.getPrimary());
            log.info("配置全局tdengine数据源完毕--------------------------------");
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.init();
    }

    @Bean
    public TdengineDb tdengineDb() {
        return new TdengineDb();
    }
}
