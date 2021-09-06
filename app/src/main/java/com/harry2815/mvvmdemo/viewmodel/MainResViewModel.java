package com.harry2815.mvvmdemo.viewmodel;

import com.harry2815.mvvm.arch.model.HttpCallback;
import com.harry2815.mvvm.arch.viewmodel.BaseViewModel;
import com.harry2815.mvvmdemo.model.MainResModel;
import com.harry2815.mvvmdemo.model.response.FaqListResponse;

/**
 * Created by zhanghai on 2019/6/3.
 * function：
 */
public class MainResViewModel extends BaseViewModel<MainResModel> {

    public void getFaqList(){
        showLoading("我来自Fragment");
        mModel.requestFaqList(new HttpCallback<FaqListResponse>() {
            @Override
            public void onResult(FaqListResponse result) {
                hideLoading();
                postValue(null,FaqListResponse.class,result);
            }

            @Override
            public void onFail(int code, String msg) {
                hideLoading();
            }
        });
    }
}
