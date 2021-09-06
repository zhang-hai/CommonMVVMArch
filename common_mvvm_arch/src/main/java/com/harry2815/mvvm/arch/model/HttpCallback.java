package com.harry2815.mvvm.arch.model;

/**
 * Created by zhanghai on 2019/5/27.
 * function：http call back
 */
public interface HttpCallback<T> {
    void onResult(T result);

    void onFail(int code, String msg);
}
