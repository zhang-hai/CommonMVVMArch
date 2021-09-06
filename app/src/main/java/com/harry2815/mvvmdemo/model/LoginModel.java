package com.harry2815.mvvmdemo.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.harry2815.mvvm.base.model.BaseModel;
import com.harry2815.mvvm.base.model.HttpCallback;
import com.harry2815.mvvmdemo.bean.BaseResponse;
import com.harry2815.mvvmdemo.bean.SAccountUtils;
import com.harry2815.mvvmdemo.bean.TokenBean;
import com.harry2815.mvvmdemo.model.response.UserInfoResponse;
import com.harry2815.mvvmdemo.model.request.LoginRequestBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhanghai on 2019/5/27.
 * function：登录Model
 */
public class LoginModel extends BaseModel {

    private String login_url = "http://api2.test.sxw.cn/passport/api/auth/login";
    private String userInfo_url = "http://api2.test.sxw.cn/platform/api/user/get_user_info/1";

    /**
     * {"account":"510101201703290022","accountType":1,"app":"MDM","client":"STUDENT","password":"LTHdH1TIDf6JilFQ6vqyCQ==","platform":"ANDROID","twoFa":false,"userType":"1"}
     * @param userName
     * @param password
     * @param callback
     */
    public void login(String userName, String password, final HttpCallback<UserInfoResponse> callback){
        OkHttpClient client = new OkHttpClient();
        MediaType contentType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(contentType,JSON.toJSONString(new LoginRequestBean()));
        Request request = new Request.Builder()
                .url(login_url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
                callback.onFail(-1,e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BaseResponse object = JSON.parseObject(response.body().string(), BaseResponse.class);
                if(object.getCode() == 200){
                    String data = object.getData();
                    TokenBean tokenBean = JSON.parseObject(data,TokenBean.class);
                    SAccountUtils.saveToken(tokenBean);
                    getUserInfo(tokenBean,callback);
                }else {
                    if(callback != null){
                        callback.onFail(object.getCode(),"qweq2");
                    }
                }
            }
        });
    }


    /**
     * 根据token获取用户信息
     * @param token
     * @param callback
     */
    private void getUserInfo(TokenBean token, final HttpCallback<UserInfoResponse> callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(userInfo_url)
                .addHeader("TOKEN",token.getToken())
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(callback != null){
                    callback.onFail(-1,e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BaseResponse object = JSON.parseObject(response.body().string(), BaseResponse.class);
                String data = object.getData();
                if(!TextUtils.isEmpty(data)){
                    UserInfoResponse userInfoResponse = JSON.parseObject(data,UserInfoResponse.class);
                    SAccountUtils.saveUserInfo(userInfoResponse);
                    if(callback != null){
                        callback.onResult(userInfoResponse);
                    }
                }else {
                    if(callback != null) callback.onFail(-1,"no data");
                }
            }
        });
    }
}
