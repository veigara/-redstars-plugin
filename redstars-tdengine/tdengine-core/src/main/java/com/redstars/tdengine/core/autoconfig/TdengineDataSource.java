package com.redstars.tdengine.core.autoconfig;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : zhouhx
 * Tdengine 数据源
 * @date : 2023/6/9 16:48
 */
public class TdengineDataSource {
    /**
     * 存放所有的数据源
     */
    private static Map<String, DataSource> datasourceMap = new LinkedHashMap();
    /**
     * 主数据源名称
     */
    private static String primaryDsName;

    /**
     *
     *
     * @author zhuohx
     * @param  dsName 数据源名称
     * @param  dataSource 数据源
     * @return void
     * @throws
     * @version 2.0
     * @since  2023/12/23 11:25
     */
    public static void addDataSource(String dsName, DataSource dataSource){
        datasourceMap.put(dsName,dataSource);
    }

    public static  void addPrimaryDsName(String dsName){
        primaryDsName = dsName;
    }

    /**
     *
     * 获取主数据源名称
     */
    public static String getPrimaryDsName(){
        return primaryDsName;
    }
    /**
     *
     * 获取数据源
     * @author zhuohx
     * @param  dsName 数据源名称
     * @return javax.sql.DataSource
     * @throws
     * @version 2.0
     * @since   2023/12/23 11:25
     */
    public static  DataSource getDataSource(String dsName){
        return datasourceMap.get(dsName);
    }

    /**
     *
     * 获取主数据源
     * @author zhuohx
     * @param
     * @return javax.sql.DataSource
     * @throws
     * @version 2.0
     * @since  2023/12/23 11:25
     */
    public static  DataSource getPrimaryDataSource(){
        return datasourceMap.get(primaryDsName);
    }
}
