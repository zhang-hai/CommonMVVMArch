package com.harry2815.mvvm.arch.bean;

/**
 * Created by zhanghai on 2019/5/28.
 * function：加载消息类
 */
public class LoadingMessageBean {
    public String message;
    public boolean isShow;

    public LoadingMessageBean(String message, boolean isShow) {
        this.message = message;
        this.isShow = isShow;
    }
}
