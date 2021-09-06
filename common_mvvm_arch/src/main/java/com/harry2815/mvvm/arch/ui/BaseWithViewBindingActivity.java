package com.harry2815.mvvm.arch.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.harry2815.mvvm.arch.viewmodel.BaseViewModel;

/**
 * Created by zhanghai on 2021/9/6.
 * function：带DataBinding的基类，若不适用DataBinding，可直接使用BaseActivity
 */
public abstract class BaseWithViewBindingActivity<VM extends BaseViewModel,VB extends ViewDataBinding> extends BaseActivity<VM>{

    protected VB mViewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0){
            mViewBinding = DataBindingUtil.inflate(getLayoutInflater(),getLayoutId(),null,false);
            setContentView(mViewBinding.getRoot());
        }
    }

    /**
     * 设置布局文件
     * @return
     */
    protected abstract int getLayoutId();
}
