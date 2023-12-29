/*
 * Copyright © 2018 organization baomidou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redstars.tdengine.core.autoconfig.creator;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.CommonsLogFilter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.redstars.tdengine.core.autoconfig.HutoolDataSourceProperty;
import com.redstars.tdengine.core.autoconfig.HutoolDynamicDataSourceProperties;
import com.redstars.tdengine.core.autoconfig.TdengineDataSource;
import com.redstars.tdengine.core.autoconfig.druid.DruidConfig;
import com.redstars.tdengine.core.autoconfig.druid.DruidLogConfigUtil;
import com.redstars.tdengine.core.autoconfig.druid.DruidStatConfigUtil;
import com.redstars.tdengine.core.autoconfig.druid.DruidWallConfigUtil;
import com.redstars.tdengine.core.exception.TdengineException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Druid数据源创建器
 *
 * @author TaoYu
 * @since 2020/1/21
 */
@Slf4j
public class DruidDataSourceCreator implements DataSourceCreator{

    @Override
    public void createDataSource(HutoolDynamicDataSourceProperties dataSourceProperty) {
        if(ObjectUtil.isEmpty(dataSourceProperty)){
            throw new TdengineException("dataSourceProperty is null");
        }
        DruidConfig druidProperty = dataSourceProperty.getDruid();
        Map<String, HutoolDataSourceProperty> datasources = dataSourceProperty.getDatasource();
        for (String key : datasources.keySet()) {
            String url = datasources.get(key).getUrl();
            String driverClassName = datasources.get(key).getDriverClassName();
            String username = datasources.get(key).getUsername();
            String password = datasources.get(key).getPassword();
            if(ObjectUtil.isEmpty(druidProperty)){
                throw new TdengineException("druid config is null");
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
            DruidDataSource dataSource = BeanUtil.copyProperties(druidProperty, DruidDataSource.class);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setUrl(url);
            dataSource.setName(key);

            if (!StringUtils.isEmpty(driverClassName)) {
                dataSource.setDriverClassName(driverClassName);
            }
            Properties properties = druidProperty.toProperties();

            List<Filter> proxyFilters = this.initFilters(dataSourceProperty, properties.getProperty("druid.filters"));
            dataSource.setProxyFilters(proxyFilters);

            dataSource.configFromPropety(properties);
            //连接参数单独设置
            dataSource.setConnectProperties(druidProperty.getConnectionProperties());
            //设置druid内置properties不支持的的参数
            this.setParam(dataSource, druidProperty);

            TdengineDataSource.addDataSource(key, dataSource);

            log.info("tdengine数据源名称："+key);
        }
    }
    private List<Filter> initFilters(HutoolDynamicDataSourceProperties dataSourceProperty, String filters) {
        List<Filter> proxyFilters = new ArrayList<>(2);
        if (!StringUtils.isEmpty(filters)) {
            String[] filterItems = filters.split(",");
            for (String filter : filterItems) {
                switch (filter) {
                    case "stat":
                        proxyFilters.add(DruidStatConfigUtil.toStatFilter(dataSourceProperty.getDruid().getStat()));
                        break;
                    case "wall":
                        WallConfig wallConfig = DruidWallConfigUtil.toWallConfig(dataSourceProperty.getDruid().getWall());
                        WallFilter wallFilter = new WallFilter();
                        wallFilter.setConfig(wallConfig);
                        proxyFilters.add(wallFilter);
                        break;
                    case "slf4j":
                        proxyFilters.add(DruidLogConfigUtil.initFilter(Slf4jLogFilter.class, dataSourceProperty.getDruid().getSlf4j()));
                        break;
                    case "commons-log":
                        proxyFilters.add(DruidLogConfigUtil.initFilter(CommonsLogFilter.class, dataSourceProperty.getDruid().getCommonsLog()));
                        break;
                    case "log4j":
                        proxyFilters.add(DruidLogConfigUtil.initFilter(Log4jFilter.class, dataSourceProperty.getDruid().getLog4j()));
                        break;
                    case "log4j2":
                        proxyFilters.add(DruidLogConfigUtil.initFilter(Log4j2Filter.class, dataSourceProperty.getDruid().getLog4j2()));
                        break;
                    default:
                        log.warn("dynamic-datasource current not support [{}]", filter);
                }
            }
        }

        return proxyFilters;
    }

    private void setParam(DruidDataSource dataSource, DruidConfig config) {
        String defaultCatalog = config.getDefaultCatalog() ;
        if (defaultCatalog != null) {
            dataSource.setDefaultCatalog(defaultCatalog);
        }
        Boolean defaultAutoCommit = config.getDefaultAutoCommit();
        if (defaultAutoCommit != null && !defaultAutoCommit) {
            dataSource.setDefaultAutoCommit(false);
        }
        Boolean defaultReadOnly = config.getDefaultReadOnly() == null ;
        if (defaultReadOnly != null) {
            dataSource.setDefaultReadOnly(defaultReadOnly);
        }
        Integer defaultTransactionIsolation = config.getDefaultTransactionIsolation();
        if (defaultTransactionIsolation != null) {
            dataSource.setDefaultTransactionIsolation(defaultTransactionIsolation);
        }

        Boolean testOnReturn = config.getTestOnReturn();
        if (testOnReturn != null && testOnReturn) {
            dataSource.setTestOnReturn(true);
        }
        Integer validationQueryTimeout =
                config.getValidationQueryTimeout();
        if (validationQueryTimeout != null && !validationQueryTimeout.equals(-1)) {
            dataSource.setValidationQueryTimeout(validationQueryTimeout);
        }

        Boolean sharePreparedStatements =
                config.getSharePreparedStatements();
        if (sharePreparedStatements != null && sharePreparedStatements) {
            dataSource.setSharePreparedStatements(true);
        }
        Integer connectionErrorRetryAttempts = config.getConnectionErrorRetryAttempts();
        if (connectionErrorRetryAttempts != null && !connectionErrorRetryAttempts.equals(1)) {
            dataSource.setConnectionErrorRetryAttempts(connectionErrorRetryAttempts);
        }
        Boolean breakAfterAcquireFailure = config.getBreakAfterAcquireFailure();
        if (breakAfterAcquireFailure != null && breakAfterAcquireFailure) {
            dataSource.setBreakAfterAcquireFailure(true);
        }

        Integer timeout = config.getRemoveAbandonedTimeoutMillis();
        if (timeout != null) {
            dataSource.setRemoveAbandonedTimeoutMillis(timeout);
        }

        Boolean abandoned = config.getRemoveAbandoned();
        if (abandoned != null) {
            dataSource.setRemoveAbandoned(abandoned);
        }

        Boolean logAbandoned = config.getLogAbandoned();
        if (logAbandoned != null) {
            dataSource.setLogAbandoned(logAbandoned);
        }

        Integer queryTimeOut = config.getQueryTimeout();
        if (queryTimeOut != null) {
            dataSource.setQueryTimeout(queryTimeOut);
        }

        Integer transactionQueryTimeout = config.getTransactionQueryTimeout();
        if (transactionQueryTimeout != null) {
            dataSource.setTransactionQueryTimeout(transactionQueryTimeout);
        }

        // since druid 1.2.12
        Integer connectTimeout = config.getConnectTimeout();
        if (connectTimeout != null) {
            try {
                DruidDataSource.class.getMethod("setConnectTimeout", int.class);
                dataSource.setConnectTimeout(connectTimeout);
            } catch (NoSuchMethodException e) {
                log.warn("druid current not support connectTimeout,please update druid 1.2.12 +");
            }
        }

        Integer socketTimeout = config.getSocketTimeout();
        if (connectTimeout != null) {
            try {
                DruidDataSource.class.getMethod("setSocketTimeout", int.class);
                dataSource.setSocketTimeout(socketTimeout);
            } catch (NoSuchMethodException e) {
                log.warn("druid current not support setSocketTimeout,please update druid 1.2.12 +");
            }
        }
    }
}