package com.redstars.tdengine.core.autoconfig.creator;

import cn.beecp.BeeDataSource;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.redstars.tdengine.core.autoconfig.HutoolDataSourceProperty;
import com.redstars.tdengine.core.autoconfig.HutoolDynamicDataSourceProperties;
import com.redstars.tdengine.core.autoconfig.TdengineDataSource;
import com.redstars.tdengine.core.autoconfig.beecp.BeeCpConfig;
import com.redstars.tdengine.core.exception.TdengineException;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

/**
 * @author : zhouhx
 * beecp数据源创建器
 * @date : 2023/12/28 14:38
 */
@Slf4j
public class BeecpDataSourceCreator implements DataSourceCreator{
    @Override
    public void createDataSource(HutoolDynamicDataSourceProperties dataSourceProperty) {
        if(ObjectUtil.isEmpty(dataSourceProperty)){
            throw new TdengineException("dataSourceProperty is null");
        }
        BeeCpConfig beecpProperty = dataSourceProperty.getBeecp();
        Map<String, HutoolDataSourceProperty> datasources = dataSourceProperty.getDatasource();
        for (String key : datasources.keySet()) {
            String url = datasources.get(key).getUrl();
            String driverClassName = datasources.get(key).getDriverClassName();
            String username = datasources.get(key).getUsername();
            String password = datasources.get(key).getPassword();
            if(ObjectUtil.isEmpty(beecpProperty)){
                throw new TdengineException("beecp config is null");
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
            BeeDataSource beeDataSource = BeanUtil.copyProperties(beecpProperty, BeeDataSource.class);
            beeDataSource.setUsername(username);
            beeDataSource.setPassword(password);
            beeDataSource.setUrl(url);
            beeDataSource.setPoolName(key);
            if(ObjectUtil.isNotEmpty(driverClassName)){
                beeDataSource.setDriverClassName(driverClassName);
            }

            TdengineDataSource.addDataSource(key, beeDataSource);

            log.info("tdengine数据源名称："+key);
        }
    }
}
