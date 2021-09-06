package com.harry2815.mvvmdemo.model.request;

import com.harry2815.mvvmdemo.bean.PageBean;

/**
 * Created by zhanghai on 2019/6/3.
 * function：
 */
public class FaqRequestBean {

    private int category = 2;//1.教师端，2.学生端
    private String classId;//班级id
    private PageBean pageableDto;
    private String subjectId = "";//科目ID
    private String termId = "";//学期ID
    private int type = 0;//类型：0.全部 1.未采纳，2.问我的 3.我的提问 4.我的回答
    private String keyword;


    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public PageBean getPageableDto() {
        return pageableDto;
    }

    public void setPageableDto(PageBean pageableDto) {
        this.pageableDto = pageableDto;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
