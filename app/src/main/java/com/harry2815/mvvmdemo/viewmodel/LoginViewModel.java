package com.harry2815.mvvmdemo.viewmodel;

import com.harry2815.mvvm.arch.model.HttpCallback;
import com.harry2815.mvvm.arch.viewmodel.BaseViewModel;
import com.harry2815.mvvmdemo.bean.User;
import com.harry2815.mvvmdemo.model.LoginModel;
import com.harry2815.mvvmdemo.model.response.UserInfoResponse;

/**
 * Created by zhanghai on 2019/5/24.
 * function：登录的ViewModel
 */
public class LoginViewModel extends BaseViewModel<LoginModel> {

    public final String userKey = "user";

    /**
     * login
     * @param loginName
     * @param loginPassword
     *
     */
    public void login(String loginName, String loginPassword) {
        showLoading("我来自登录");
        mModel.login(loginName, loginPassword, new HttpCallback<UserInfoResponse>() {
            @Override
            public void onResult(UserInfoResponse result) {
                hideLoading();
                User user = new User();
                user.userId = result.getUserSimpleDTO().getId();
                user.username = result.getUserSimpleDTO().getName();
                user.avatar = result.getUserSimpleDTO().getPortraitUrl();
                postValue(userKey,user);
            }

            @Override
            public void onFail(int code, String msg) {
                hideLoading();
            }
        });
    }
}
