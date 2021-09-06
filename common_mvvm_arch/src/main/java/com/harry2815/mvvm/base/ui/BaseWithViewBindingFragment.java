package com.harry2815.mvvm.base.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.harry2815.mvvm.base.viewmodel.BaseViewModel;

/**
 * Created by zhanghai on 2021/9/6.
 * function：带DataBinding的基类，若不适用DataBinding，可直接使用BaseFragment
 */
public abstract class BaseWithViewBindingFragment<VM extends BaseViewModel,VB extends ViewDataBinding> extends BaseFragment<VM>{

    protected VB mViewBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() != 0){
            mViewBinding = DataBindingUtil.inflate(getLayoutInflater(),getLayoutId(),container,false);
            if (mViewBinding != null){
                return mViewBinding.getRoot();
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    /**
     * 设置布局文件
     * @return
     */
    protected abstract int getLayoutId();
}
