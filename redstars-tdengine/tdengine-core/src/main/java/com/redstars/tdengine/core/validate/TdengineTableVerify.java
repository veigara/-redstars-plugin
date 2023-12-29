package com.redstars.tdengine.core.validate;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.redstars.tdengine.core.annotation.TdengineTableId;
import com.redstars.tdengine.core.annotation.TdengineTableName;
import com.redstars.tdengine.core.annotation.TdengineTableTag;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : zhouhx
 *  各种验证
 * @date : 2023/6/12 11:31
 */
public class TdengineTableVerify {
    /**
     *
     * 验证数据保存对象
     * @author zhuohx
     * @param   entity 实体类数据
     * @return void 
     * @throws 
     * @version 1.0
     * @since  2023/6/12 14:29
     */
   public static void verifySave(Class entity){
        Assert.notNull(entity, "Class must not be null", new Object[0]);
        boolean tableNameFalg = entity.isAnnotationPresent(TdengineTableName.class);
        Assert.isTrue(tableNameFalg, "Class must exist @tdengineTableName annotation", new Object[0]);

        // 验证是否有主键，tag注解
        Field[] fields = entity.getDeclaredFields();
        if(fields!= null && fields.length>0){
            AtomicBoolean tableIdFlag = new AtomicBoolean(false);
            AtomicBoolean tableTagFlag = new AtomicBoolean(false);

            // 实体类的第一个字段必须为时间戳主键
            boolean annotationPresent = fields[0].isAnnotationPresent(TdengineTableId.class);
            tableIdFlag.set(annotationPresent);

            Arrays.stream(fields).forEach(field ->{
                boolean fieldFlag = field.isAnnotationPresent(TdengineTableTag.class);

                if(ObjectUtil.isNotEmpty(fieldFlag)){
                    tableTagFlag.set(true);
                }
            });
            Assert.isTrue(tableIdFlag.get(),"entity  first field must exist @tdengineTableId annotation");
            Assert.isTrue(tableTagFlag.get(),"entity must exist @tdengineTableTag annotation");
        }
    }
}
