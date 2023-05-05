package com.redstars.tdengine.core.common;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author zhouhx
 */
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    //第几页
    private Integer pageNo = 1;

    //每页条数
    private Integer pageSize = 20;

    //总页数
    private Integer totalPage = 0;

    //总记录数
    private Integer total = 0;

    //结果集
    private List<T> list;

    public PageResult() {
    }
    

    public PageResult(Page<T> page) {
        this.setList(page.getRecords());
        this.setTotal(Convert.toInt(page.getTotal()));
        this.setPageNo(Convert.toInt(page.getCurrent()));
        this.setPageSize(Convert.toInt(page.getSize()));
        this.setTotalPage(PageUtil.totalPage(Convert.toInt(page.getTotal()),
                Convert.toInt(page.getSize())));
    }

    /**
     *
     * @author zhuohx
     * @description 将mybatis-plus的page转成自定义的PageResult
     * @parms  [page 分页对象, t数据集合]
     * @throws
     * @date 2022/9/19 10:04
     */
    public PageResult(IPage<T> page, List<T> t) {
        this.setList(t);
        this.setTotal(Convert.toInt(page.getTotal()));
        this.setPageNo(Convert.toInt(page.getCurrent()));
        this.setPageSize(Convert.toInt(page.getSize()));
        this.setTotalPage(PageUtil.totalPage(Convert.toInt(page.getTotal()),
                Convert.toInt(page.getSize())));
    }

    /**
     * 分页
     * @param list   列表数据
     * @param total  总记录数
     */
    public PageResult(List<T> list, long total) {
        this.list = list;
        this.total = (int)total;
    }
}