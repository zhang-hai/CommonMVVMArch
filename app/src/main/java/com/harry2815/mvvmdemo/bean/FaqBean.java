package com.harry2815.mvvmdemo.bean;

import java.util.List;

/**
 * Created by zhanghai on 2019/6/3.
 * function：
 */
public class FaqBean {

    /**
     * {
     *       "id" : "39db1747-aff5-40ef-b077-b8642531238d",
     *       "userId" : "6624a591-0170-4cea-b633-63c4466d94b5",
     *       className : 高2017级1班,
     *       "userName" : "古迪西",
     *       flowerCount : 0,
     *       "userAvatar" : "",
     *       "subjectId" : "38c6a671-f4dd-48c0-abd6-0b9c1cd91c7b",
     *       "subjectName" : "英语",
     *       "question" : "",
     *       "source" : "",
     *       "replyCount" : 0,
     *       "isOnlyTeacher" : false,
     *       "isResolve" : false,
     *       "isNewQuestion" : true,
     *       "hasNewReply" : false,
     *       "time" : 1559118639504,
     *       "urls" : [ ],
     *       "resources" : [ {
     *         "url" : "http://resource.test.sxw.cn/res/20190517/c7a5f1ac-e7b4-489f-8628-ed5004f459ea.jpg",
     *         "type" : 1,
     *         "sort" : 1
     *       } ]
     *     }
     */

    private String id;
    private String className;
    private boolean isResolve = false;
    private String question;
    private int flowerCount;
    private boolean isOnlyTeacher = false;
    private Boolean hasNewReply = false;
    private Boolean isNewQuestion = false;
    private int replyCount;
    private String source;
    private long time;
    private String userId;
    private String userAvatar;
    private String userName;
    private List<ResourcesBean> resources;
    private String subjectId;
    private String subjectName;
    private List<String> urls; //关联的作业素材地址

    //local param 区分是否是同一个班级
//    private boolean isNewClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isIsResolve() {
        return isResolve;
    }

    public void setIsResolve(boolean isResolve) {
        this.isResolve = isResolve;
    }

    public boolean isIsOnlyTeacher() {
        return isOnlyTeacher;
    }

    public void setIsOnlyTeacher(boolean onlyTeacher) {
        isOnlyTeacher = onlyTeacher;
    }

    public int getFlowerCount() {
        return flowerCount;
    }

    public void setFlowerCount(int flowerCount) {
        this.flowerCount = flowerCount;
    }

    public Boolean getHasNewReply() {
        return hasNewReply;
    }

    public void setHasNewReply(Boolean hasNewReply) {
        this.hasNewReply = hasNewReply;
    }

    public Boolean isIsNewQuestion() {
        return isNewQuestion;
    }

    public void setIsNewQuestion(Boolean newQuestion) {
        isNewQuestion = newQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ResourcesBean> getResources() {
        return resources;
    }

    public void setResources(List<ResourcesBean> resources) {
        this.resources = resources;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
