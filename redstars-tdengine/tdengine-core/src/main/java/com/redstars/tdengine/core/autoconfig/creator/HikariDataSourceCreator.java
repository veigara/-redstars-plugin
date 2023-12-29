package com.redstars.tdengine.core.autoconfig.creator;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.redstars.tdengine.core.autoconfig.HutoolDataSourceProperty;
import com.redstars.tdengine.core.autoconfig.HutoolDynamicDataSourceProperties;
import com.redstars.tdengine.core.autoconfig.TdengineDataSource;
import com.redstars.tdengine.core.autoconfig.hikari.HikariCpConfig;
import com.redstars.tdengine.core.exception.TdengineException;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

/**
 * @author : zhouhx
 * Hikari数据源创建器
 * @date : 2023/12/28 14:38
 */
@Slf4j
public class HikariDataSourceCreator implements DataSourceCreator{
    @Override
    public void createDataSource(HutoolDynamicDataSourceProperties dataSourceProperty) {
        if(ObjectUtil.isEmpty(dataSourceProperty)){
            throw new TdengineException("dataSourceProperty is null");
        }
        HikariCpConfig hikariProperty =  dataSourceProperty.getHikari();
        Map<String, HutoolDataSourceProperty> datasources = dataSourceProperty.getDatasource();
        for (String key : datasources.keySet()) {
            String url = datasources.get(key).getUrl();
            String driverClassName = datasources.get(key).getDriverClassName();
            String username = datasources.get(key).getUsername();
            String password = datasources.get(key).getPassword();
            if(ObjectUtil.isEmpty(hikariProperty)){
                throw new TdengineException("hikari config is null");
            }
            if(ObjectUtil.isEmpty(username)){
                throw new TdengineException("JDBC 用户名 is null");
            }
            if(ObjectUtil.isEmpty(password)){
                throw new TdengineException("JDBC 密码 is null");
            }
            if(ObjectUtil.isEmpty(url)){
                throw new TdengineException("JDBC url is null");
            }
            HikariDataSource dataSource = BeanUtil.copyProperties(hikariProperty, HikariDataSource.class);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setJdbcUrl(url);
            if(ObjectUtil.isNotEmpty(driverClassName)){
                dataSource.setDriverClassName(driverClassName);
            }
            TdengineDataSource.addDataSource(key, dataSource);

            log.info("tdengine数据源名称："+key);
        }
    }
}
