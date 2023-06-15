package com.redstars.tdengine.example;

import cn.hutool.db.Page;
import com.alibaba.fastjson2.JSONObject;
import com.redstars.tdengine.api.TdengineDb2;
import com.redstars.tdengine.core.common.PageResult;
import com.redstars.tdengine.core.conditions.query.TdengineQueryWrapper;
import com.redstars.tdengine.example.entity.MeterEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @author : zhouhx
 * 时序数据库测试案例
 * @date : 2023/3/3 14:13
 */
public class AppcationTest {
    private static TdengineDb2 db = new TdengineDb2();

    public static void save(){
        MeterEntity meterEntity = new MeterEntity();
        meterEntity.setTs(new Date());
        meterEntity.setCurrent(11L);
        meterEntity.setVoltage(116L);
        meterEntity.setPhase(0.9);
        meterEntity.setDbName("d25");
        meterEntity.setGroupId(6);
        meterEntity.setLocation("California.Sunnyvale");
        boolean save = db.save(meterEntity);
        System.out.println("更新或者插入结果:"+save);
    }

    public static void save1(){
        String sql = "INSERT INTO d25 USING meters tags(6,'California.Sunnyvale') VALUES (?, ?,?, ?);";
        boolean b =  db.insertOrUpdate(sql, System.currentTimeMillis(), 11.55, 116, 0.9);
        System.err.println("sql更新或者插入结果："+b);
    }

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
        MeterEntity entity = (MeterEntity) db.getOne(sql, MeterEntity.class);
        System.err.println("只查询一条结果："+JSONObject.toJSONString(entity));
    }

    public  static void queryOneEntity() {
        TdengineDb2 db2 = new TdengineDb2();
        TdengineQueryWrapper<MeterEntity> queryWrapper = new TdengineQueryWrapper<>(MeterEntity.class);
        queryWrapper.select("last(ts) as ts, *");
        MeterEntity entity = db.getOne(queryWrapper);
        System.err.println("只查询一条结果："+JSONObject.toJSONString(entity));
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
