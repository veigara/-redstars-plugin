package com.redstars.tdengine.starter;

import cn.hutool.core.util.ObjectUtil;
import com.redstars.tdengine.api.TdengineDb;
import com.redstars.tdengine.core.autoconfig.HutoolDataSourceProperty;
import com.redstars.tdengine.core.autoconfig.HutoolDynamicDataSourceProperties;
import com.redstars.tdengine.core.autoconfig.TdengineDataSource;
import com.redstars.tdengine.core.autoconfig.beecp.BeeCpConfig;
import com.redstars.tdengine.core.autoconfig.creator.BeecpDataSourceCreator;
import com.redstars.tdengine.core.autoconfig.creator.Dbcp2DataSourceCreator;
import com.redstars.tdengine.core.autoconfig.creator.DruidDataSourceCreator;
import com.redstars.tdengine.core.autoconfig.creator.HikariDataSourceCreator;
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
        BeeCpConfig beecp = this.properties.getBeecp();
        Dbcp2Config dbcp = this.properties.getDbcp();

        if (!datasource.isEmpty()) {
            log.info("开始配置全局tdengine数据源--------------------------------");
            // 设置组名
            if(ObjectUtil.isNotEmpty(hikari)){
                HikariDataSourceCreator poolCreator = new HikariDataSourceCreator();
                poolCreator.createDataSource(this.properties);
                log.info("设置tdengine连接池：HikariCp--------------------------------");
            }
            if(ObjectUtil.isNotEmpty(druid)){
                log.info("设置tdengine连接池：Druid--------------------------------");
                DruidDataSourceCreator poolCreator = new DruidDataSourceCreator();
                poolCreator.createDataSource(this.properties);
            }
            if(ObjectUtil.isNotEmpty(beecp)){
                BeecpDataSourceCreator poolCreator = new BeecpDataSourceCreator();
                poolCreator.createDataSource(this.properties);
                log.info("设置tdengine连接池：beecp JDBC Pool --------------------------------");
            }
            if(ObjectUtil.isNotEmpty(dbcp)){
                Dbcp2DataSourceCreator poolCreator = new Dbcp2DataSourceCreator();
                poolCreator.createDataSource(this.properties);
                log.info("设置tdengine连接池：DBCP2--------------------------------");
            }
            if(ObjectUtil.isAllEmpty(hikari, druid, beecp, dbcp)){
                throw new IllegalArgumentException("未配置tdengine连接池，请检查配置");
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
