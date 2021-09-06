package com.harry2815.mvvmdemo.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.harry2815.mvvm.base.model.HttpCallback;
import com.harry2815.mvvm.base.viewmodel.BaseViewModel;
import com.harry2815.mvvmdemo.bean.User;
import com.harry2815.mvvmdemo.model.response.UserInfoResponse;
import com.harry2815.mvvmdemo.model.LoginModel;

/**
 * Created by zhanghai on 2019/5/24.
 * function：登录的ViewModel
 */
public class LoginViewModel extends BaseViewModel<LoginModel> {

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
                postValue(null,User.class,user);
            }

            @Override
            public void onFail(int code, String msg) {
                hideLoading();
            }
        });
    }
}
