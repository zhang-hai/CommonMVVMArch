package com.harry2815.mvvmdemo.bean;

import com.harry2815.mvvmdemo.model.response.UserInfoResponse;

/**
 * Created by zhanghai on 2019/6/3.
 * function：
 */
public class SAccountUtils {

    private SAccountUtils(){}

    private static TokenBean mTokenBean;
    private static UserInfoResponse mUserInfoResponse;


    public static void saveToken(TokenBean tokenBean){
        mTokenBean = tokenBean;
    }

    /**
     * 获取token
     * @return
     */
    public static String getToken(){
        if(mTokenBean != null){
            return mTokenBean.getToken();
        }else {
            throw new RuntimeException("没有Token信息！！！");
        }
    }

    public static void saveUserInfo(UserInfoResponse userInfoResponse){
        mUserInfoResponse = userInfoResponse;
    }

    /**
     * 获取用户id
     * @return
     */
    public static String getUserId(){
        if(mUserInfoResponse != null && mUserInfoResponse.getUserSimpleDTO() != null){
            return mUserInfoResponse.getUserSimpleDTO().getId();
        }else {
            throw new RuntimeException("没有用户信息！！！");
        }
    }

    /**
     * 获取班级id
     * @return
     */
    public static String getClassId(){
        if(mUserInfoResponse != null && mUserInfoResponse.getClassComplexDTO() != null){
            return mUserInfoResponse.getClassComplexDTO().getClassSimpleDTO().getId();
        }else {
            throw new RuntimeException("没有班级信息！！！");
        }
    }


}
