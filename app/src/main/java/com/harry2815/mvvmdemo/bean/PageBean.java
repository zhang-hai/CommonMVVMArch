package com.harry2815.mvvmdemo.bean;

import java.util.List;

/**
 * Created by zhanghai on 2019/6/3.
 * function：
 */
public class PageBean {
    /**
     * page : 0
     * size : 0
     * sort : string
     * sortProperties : ["string"]
     */

    private int page;
    private int size;
    private String sort = "asc"; //排序：asc/desc, 默认排序为ORDER By UpdatedAt ASC
    private List<String> sortProperties;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<String> getSortProperties() {
        return sortProperties;
    }

    public void setSortProperties(List<String> sortProperties) {
        this.sortProperties = sortProperties;
    }
}
