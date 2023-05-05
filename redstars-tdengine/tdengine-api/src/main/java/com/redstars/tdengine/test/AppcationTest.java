package com.redstars.tdengine.test;

import cn.hutool.db.Page;
import com.alibaba.fastjson2.JSONObject;
import com.redstars.tdengine.api.TdengineDb;
import com.redstars.tdengine.core.common.PageResult;
import com.redstars.tdengine.test.entity.MeterEntity;
import java.util.List;

/**
 * @author : zhouhx
 * 时序数据库测试案例
 * @date : 2023/3/3 14:13
 */
public class AppcationTest {
    private static TdengineDb db = new TdengineDb();
    /**
     *
     * @author zhuohx
     * @description 查询list
     * @parms  []
     * @return void
     * @throws
     * @date 2023/3/3 14:15
     */
    public static  void queryList() {
        String sql="SELECT * FROM test.d1";
        List<MeterEntity> list = db.list(sql,MeterEntity.class);
        System.err.println("查询所有数据集结果："+JSONObject.toJSONString(list));
    }

    public  static void queryOne() {
        String sql = "select last(ts),* from test.meters";
        MeterEntity entity = db.getOne(sql, MeterEntity.class);
        System.err.println("只查询一条结果："+JSONObject.toJSONString(entity));
    }
    public static void save(){
        String sql = "INSERT INTO test.d2 (ts, \"current\", voltage, phase) VALUES (?, ?,?, ?);";
        boolean b =  db.insertOrUpdate(sql, System.currentTimeMillis(), 11.55, 116, 0.9);
        System.err.println("更新或者插入结果："+b);
    }

    public static void count(){
        String sql = "select count(*) from  test.service_data_station_HN0001";
        Number number = db.getNumber(sql);
        System.err.println("总数查询："+number.toString());

    }

    public static void page(){
        String sql ="select * from test.meters";
        PageResult<MeterEntity> page = db.page(new Page(1, 100),MeterEntity.class, sql);

        System.err.println("tdengine 分页结果："+ page);
    }

}
