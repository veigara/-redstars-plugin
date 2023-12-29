package com.redstars.tdengine.core.autoconfig.creator;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.redstars.tdengine.core.autoconfig.HutoolDataSourceProperty;
import com.redstars.tdengine.core.autoconfig.HutoolDynamicDataSourceProperties;
import com.redstars.tdengine.core.autoconfig.TdengineDataSource;
import com.redstars.tdengine.core.autoconfig.dbcp2.Dbcp2Config;
import com.redstars.tdengine.core.exception.TdengineException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import java.util.Map;

/**
 * @author : zhouhx
 * dbcp2数据源创建器
 * @date : 2023/12/28 14:38
 */
@Slf4j
public class Dbcp2DataSourceCreator implements DataSourceCreator{
    @Override
    public void createDataSource(HutoolDynamicDataSourceProperties dataSourceProperty) {
        if(ObjectUtil.isEmpty(dataSourceProperty)){
            throw new TdengineException("dataSourceProperty is null");
        }
        Dbcp2Config debcpProperty =  dataSourceProperty.getDbcp();
        Map<String, HutoolDataSourceProperty> datasources = dataSourceProperty.getDatasource();
        for (String key : datasources.keySet()) {
            String url = datasources.get(key).getUrl();
            String driverClassName = datasources.get(key).getDriverClassName();
            String username = datasources.get(key).getUsername();
            String password = datasources.get(key).getPassword();
            if(ObjectUtil.isEmpty(debcpProperty)){
                throw new TdengineException("dbcp2 config is null");
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
            BasicDataSource dataSource = BeanUtil.copyProperties(debcpProperty, BasicDataSource.class);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setUrl(url);
            if(ObjectUtil.isNotEmpty(driverClassName)){
                dataSource.setDriverClassName(driverClassName);
            }
            TdengineDataSource.addDataSource(key, dataSource);

            log.info("tdengine数据源名称："+key);
        }
    }
}
