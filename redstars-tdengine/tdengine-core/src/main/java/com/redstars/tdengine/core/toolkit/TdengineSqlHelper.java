package com.redstars.tdengine.core.toolkit;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.Page;
import com.redstars.tdengine.core.conditions.query.TdengineQueryWrapper;
import com.redstars.tdengine.core.entity.TdengineTableInfo;
import com.redstars.tdengine.core.enums.SqlTypeEnum;
import com.redstars.tdengine.core.exception.TdengineException;
import com.redstars.tdengine.core.validate.TdengineTableVerify;
import com.redstars.tdengine.core.vo.TdengineSqlVo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : zhouhx
 * @date : 2023/6/12 15:12
 */
public class TdengineSqlHelper {
    /**
     *
     * 获取插入sql语句
     * @author zhuohx
     * @param  obj 实体类数据
     * @return java.lang.String
     * @throws
     * @version 1.0
     * @since  2023/6/12 18:37
     */
    public  static TdengineSqlVo entityTosql(Object obj) throws IllegalAccessException {

        // 验证
        TdengineTableVerify.verifySave(obj.getClass());

        TdengineTableInfo tableInfo = TdengineTableHelper.getTableInfo(obj.getClass(),obj);
        return new TdengineSqlVo(covertSaveSql(tableInfo),tableInfo.getColumnValueList()) ;
    }

    /**
     *
     * 转换成插入sql语句
     * @author zhuohx
     * @param  tableInfo 数据表
     * @return java.lang.String
     * @throws
     * @version 1.0
     * @since  2023/6/12 18:31
     */
    private  static String covertSaveSql(TdengineTableInfo tableInfo){
        List<Object> columnValueList = tableInfo.getColumnValueList();
        String[] fields = new String[columnValueList.size()];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = "?";
        }
        String tableName = ObjectUtil.isNotEmpty(tableInfo.getSubTalbleName())?tableInfo.getSubTalbleName():tableInfo.getTalbleName()+"_"+tableInfo.getTableTagColumn().get(0);

        return String.format(SqlTypeEnum.SAVE.getText(), tableName,tableInfo.getTalbleName(),ArrayUtil.join(tableInfo.getTableTagColumnValue().toArray(),","),ArrayUtil.join(fields,","));
    }

    /**
     *
     * wrappr 转换成sql语句
     * @author zhuohx
     * @param   sqlTypeEnum 类型 0 查询select语句 1 删除语句delete
     * @param   wrapper 查询条件
     * @return com.redstars.tdengine.core.vo.TdengineSqlVo
     * @throws
     * @version 1.0
     * @since  2023/6/13 9:22
     */
    public static String wrapperToSql(SqlTypeEnum sqlTypeEnum, TdengineQueryWrapper wrapper) throws IllegalAccessException {
        TdengineTableInfo tableInfo = TdengineTableHelper.getTableInfo(wrapper.getEntityClass(),null);
        if(ObjectUtil.isEmpty(tableInfo)){
            throw new TdengineException(wrapper.getEntityClass()+" parsing exceptions");
        }
        String talbleName = tableInfo.getTalbleName();
        if(ObjectUtil.isEmpty(talbleName)){
            throw new TdengineException(wrapper.getEntityClass()+" must exist @tdengineTableName annotation");
        }
        List<String> columnList = tableInfo.getColumnList();
        List<String> tableTagColumn = tableInfo.getTableTagColumn();

        String subTableSuffix = null;
        if(ObjectUtil.isNotEmpty(subTableSuffix)){
            talbleName = talbleName+"_"+subTableSuffix;
        }
        String sqlSelect = wrapper.getSqlSelect();
        String sqlSegment = wrapper.getSqlSegment();
        if(ObjectUtil.isEmpty(sqlSelect)){
            columnList.addAll(tableTagColumn);
            sqlSelect = columnList.stream().collect(Collectors.joining(","));
        }

        if(sqlTypeEnum.equals(SqlTypeEnum.SELECT)){
            return String.format(SqlTypeEnum.SELECT.getText(), sqlSelect, talbleName, sqlSegment);
        }
        if(sqlTypeEnum.equals(SqlTypeEnum.DELETE)){
            return String.format(SqlTypeEnum.DELETE.getText(), talbleName, sqlSegment);
        }
        if(sqlTypeEnum.equals(SqlTypeEnum.COUNT)){
            return String.format(SqlTypeEnum.COUNT.getText(), talbleName, sqlSegment);
        }

        return null;
    }

    /**
     *
     * 转换成分页语句
     * @author zhuohx
     * @param   page 分页参数
     * @param   sql 不带分页的sql语句
     * @return java.lang.String
     * @throws
     * @version 1.0
     * @since  2023/6/14 15:45
     */
    public static String covertPageSql(Page page, String sql) {
        StringBuilder order = new StringBuilder("");
        if(ArrayUtil.isNotEmpty(page.getOrders())){
            order.append(" order by ");
            for (int i = 0; i < page.getOrders().length; i++) {
                if(i != page.getOrders().length-1){
                    order.append( page.getOrders()[i].toString()).append(" ,");
                }else{
                    order.append( page.getOrders()[i].toString());
                }
            }
        }
        StringBuilder pageSql = new StringBuilder(sql).append(order.toString()).append(" limit ").append(page.getPageSize()).append(" offset ").append(TdengineResultHelper.getStart(page));

        return pageSql.toString();
    }
}
