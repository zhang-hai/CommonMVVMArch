package com.harry2815.mvvmdemo.bean;

/**
 * Created by zhanghai on 2019/6/3.
 * function：
 */
public class ResourcesBean {
    /**
     * attachmentId : string
     * sort : 0
     * type : 0
     * url : string
     */

    private String attachmentId;
    private int sort;
    private int type;
    private String url;

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
