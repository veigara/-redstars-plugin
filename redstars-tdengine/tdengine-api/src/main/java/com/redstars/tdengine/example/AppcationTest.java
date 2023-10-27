package com.redstars.tdengine.example;

import cn.hutool.db.Page;
import com.alibaba.fastjson2.JSONObject;
import com.redstars.tdengine.api.TdengineDb;
import com.redstars.tdengine.core.common.PageResult;
import com.redstars.tdengine.core.conditions.query.TdengineQueryWrapper;
import com.redstars.tdengine.example.entity.MeterEntity;

import java.util.*;

/**
 * @author : zhouhx
 * 时序数据库测试案例
 * @date : 2023/3/3 14:13
 */
public class AppcationTest {
    private static TdengineDb db = new TdengineDb();

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

    public static void bathchSave(){
        MeterEntity meterEntity = new MeterEntity();
        meterEntity.setTs(new Date());
        meterEntity.setCurrent(11L);
        meterEntity.setVoltage(116L);
        meterEntity.setPhase(0.9);
        meterEntity.setDbName("d25");
        meterEntity.setGroupId(6);
        meterEntity.setLocation("California.Sunnyvale");
        MeterEntity meterEntity1 = new MeterEntity();
        meterEntity1.setTs(new Date());
        meterEntity1.setCurrent(12L);
        meterEntity1.setVoltage(116L);
        meterEntity1.setPhase(0.9);
        meterEntity1.setDbName("d25");
        meterEntity1.setGroupId(6);
        meterEntity1.setLocation("California.Sunnyvale");
        MeterEntity meterEntity2 = new MeterEntity();
        meterEntity2.setTs(new Date());
        meterEntity2.setCurrent(13L);
        meterEntity2.setVoltage(117L);
        meterEntity2.setPhase(0.9);
        meterEntity2.setDbName("d26");
        meterEntity2.setGroupId(6);
        meterEntity2.setLocation("California.Sunnyvale");
        ArrayList<Object> list = new ArrayList<>();
        list.add(meterEntity);
        list.add(meterEntity1);
        list.add(meterEntity2);

        boolean save = db.saveBatch(list,2);
        System.out.println("批量插入结果:"+save);
    }

    public static void save1(){
        String sql = "INSERT INTO d25 USING meters tags(6,'California.Sunnyvale') VALUES (?, ?,?, ?);";
        boolean b =  db.insertOrUpdate(sql, System.currentTimeMillis(), 11.55, 118, 0.9);
        System.err.println("sql更新或者插入结果："+b);
    }

    public static  void remove(){
        TdengineQueryWrapper<MeterEntity> queryWrapper = new TdengineQueryWrapper<>(MeterEntity.class);
        queryWrapper.eq("voltage",118);
        boolean res = db.remove(queryWrapper);
        System.err.println("删除结果："+JSONObject.toJSONString(res));
    }

    public  static void queryOneClass() {
        TdengineQueryWrapper<MeterEntity> queryWrapper = new TdengineQueryWrapper<>(MeterEntity.class);
        queryWrapper.select("last(ts) as ts, *");
        queryWrapper.eq("voltage",116);
        MeterEntity entity = db.getOne(queryWrapper);
        System.err.println("queryOneClass结果："+JSONObject.toJSONString(entity));    }

    public  static void queryOne() {
        String sql = "select last(ts),* from meters";
        MeterEntity entity = (MeterEntity) db.getOne(sql, MeterEntity.class);
        System.err.println("queryOne结果："+JSONObject.toJSONString(entity));
    }

    public  static void queryOneEntity() {
        TdengineDb db2 = new TdengineDb();
        TdengineQueryWrapper<MeterEntity> queryWrapper = new TdengineQueryWrapper<>(MeterEntity.class);
        queryWrapper.select("last(ts) as ts, *");
        MeterEntity entity = db.getOne(queryWrapper);
        System.err.println("只查询一条结果："+JSONObject.toJSONString(entity));
    }

    public static void getMap(){
        TdengineQueryWrapper<MeterEntity> queryWrapper = new TdengineQueryWrapper<>(MeterEntity.class);
        queryWrapper.select("last(ts) as ts, *");
        queryWrapper.eq("voltage",116);
        Map<String, Object> map = db.getMap(queryWrapper);
        System.err.println("getMap结果："+JSONObject.toJSONString(map));
    }

    public static void count(){
        TdengineQueryWrapper<MeterEntity> queryWrapper = new TdengineQueryWrapper<>(MeterEntity.class);
        long count = db.count(queryWrapper);
        System.err.println("countNum结果："+JSONObject.toJSONString(count));
    }

    public static void getNumber(){
        String sql = "select count(*) from  meters";
        Number number = db.getNumber(sql);
        System.err.println("getNumber查询："+number.toString());

    }

    public static void getString(){
        TdengineQueryWrapper<MeterEntity> queryWrapper = new TdengineQueryWrapper<>(MeterEntity.class);
        queryWrapper.select("last(ts) as ts");
        queryWrapper.eq("voltage",116);
        String s = db.getString(queryWrapper);
        System.err.println("getString查询："+s);
    }

    public static void getList(){
        TdengineQueryWrapper<MeterEntity> queryWrapper = new TdengineQueryWrapper<>(MeterEntity.class);
        queryWrapper.eq("voltage",116);
        List<MeterEntity> list = db.list(queryWrapper);
        System.err.println("getList结果："+JSONObject.toJSONString(list));
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

    public static void getPage(){
        TdengineQueryWrapper<MeterEntity> queryWrapper = new TdengineQueryWrapper<>(MeterEntity.class);

        PageResult<MeterEntity> page = db.page(new Page(1, 100),queryWrapper);

        System.err.println("getPage分页结果："+ page);
    }

    public static void page(){
        String sql ="select * from test.meters";
        PageResult<MeterEntity> page = db.page(new Page(1, 100),MeterEntity.class, sql);

        System.err.println("tdengine 分页结果："+ page);
    }


}
