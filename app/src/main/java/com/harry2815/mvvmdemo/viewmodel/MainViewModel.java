package com.harry2815.mvvmdemo.viewmodel;

import com.harry2815.mvvm.arch.viewmodel.BaseViewModel;
import com.harry2815.mvvmdemo.model.MainModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghai on 2019/5/28.
 * function：
 */
public class MainViewModel extends BaseViewModel<MainModel> {

    public void postTestValue(){
        List<String> list = new ArrayList<>();
        list.add("这时来自postValue 集合的测试");
        postValue("test", List.class,list);
    }

}
