package com.redstars.tdengine.core.autoconfig;

import cn.hutool.db.ds.DSFactory;
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
    private static Map<String, DSFactory> datasourceMap = new LinkedHashMap();
    /**
     * 主数据源名称
     */
    private static String primaryDsName;

    /**
     *
     *
     * @author zhuohx
     * @param  dsName 数据源名称
     * @param  dsFactory 数据源工厂
     * @return void
     * @throws
     * @version 1.0
     * @since  2023/6/9 16:50
     */
    public static void addDataSource(String dsName,DSFactory dsFactory){
        datasourceMap.put(dsName,dsFactory);
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
     * @return cn.hutool.db.ds.DSFactory
     * @throws
     * @version 1.0
     * @since  2023/6/9 16:52
     */
    public static  DSFactory getDataSource(String dsName){
        return datasourceMap.get(dsName);
    }

    /**
     *
     * 获取主数据源
     * @author zhuohx
     * @param
     * @return cn.hutool.db.ds.DSFactory
     * @throws
     * @version 1.0
     * @since  2023/6/9 16:55
     */
    public static  DSFactory getPrimaryDataSource(){
        return datasourceMap.get(primaryDsName);
    }
}
