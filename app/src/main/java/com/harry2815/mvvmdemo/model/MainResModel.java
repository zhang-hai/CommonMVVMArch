package com.harry2815.mvvmdemo.model;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.harry2815.mvvm.base.model.BaseModel;
import com.harry2815.mvvm.base.model.HttpCallback;
import com.harry2815.mvvmdemo.bean.BaseResponse;
import com.harry2815.mvvmdemo.bean.PageBean;
import com.harry2815.mvvmdemo.bean.SAccountUtils;
import com.harry2815.mvvmdemo.model.request.FaqRequestBean;
import com.harry2815.mvvmdemo.model.response.FaqListResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhanghai on 2019/6/3.
 * function：
 */
public class MainResModel extends BaseModel {
    private final String faq_list_url = "http://api2.test.sxw.cn/teaching/api/activity_question/questions";


    /**
     * 请求问题列表
     * @param callback
     */
    public void requestFaqList(@NonNull final HttpCallback<FaqListResponse> callback) {

        OkHttpClient client = new OkHttpClient();
        MediaType contentType = MediaType.parse("application/json; charset=utf-8");
        FaqRequestBean faqRequestBean = new FaqRequestBean();
        faqRequestBean.setClassId(SAccountUtils.getClassId());
        PageBean pageBean = new PageBean();
        pageBean.setPage(1);
        pageBean.setSize(20);
        faqRequestBean.setPageableDto(pageBean);
        RequestBody body = RequestBody.create(contentType,JSON.toJSONString(faqRequestBean));
        Request request = new Request.Builder()
                .url(faq_list_url)
                .addHeader("TOKEN", SAccountUtils.getToken())
                .post(body)
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
                if(object.getCode() == 200){
                    String data = object.getData();
                    FaqListResponse result = JSON.parseObject(data,FaqListResponse.class);
                    if(callback != null){
                        callback.onResult(result);
                    }
                }else {
                    if(callback != null){
                        callback.onFail(object.getCode(),"failed");
                    }
                }
            }
        });

    }
}
