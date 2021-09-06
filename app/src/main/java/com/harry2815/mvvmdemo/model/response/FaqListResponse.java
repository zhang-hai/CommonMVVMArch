package com.harry2815.mvvmdemo.model.response;

import com.harry2815.mvvmdemo.bean.FaqBean;

import java.util.List;

/**
 * Created by zhanghai on 2019/6/3.
 * function：
 */
public class FaqListResponse {

    /**
     * currentPage : 0
     * dataList : [{"id":"string","isResolve":true,"question":"string","replyCount":0,"resources":[{"attachmentId":"string","sort":0,"type":0,"url":"string"}],"source":"string","time":0,"userAvatar":"string","userName":"string"}]
     * pageSize : 0
     * totalCount : 0
     * totalPage : 0
     */

    private int currentPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<FaqBean> dataList;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<FaqBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<FaqBean> dataList) {
        this.dataList = dataList;
    }
}
