package com.harry2815.mvvm.arch.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.harry2815.mvvm.R;
import com.harry2815.mvvm.arch.bean.LoadingMessageBean;
import com.harry2815.mvvm.arch.viewmodel.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by zhanghai on 2019/5/24.
 * function：Fragment的基类
 */
public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {
    private ViewModelProvider viewModelProvider;
    protected VM mViewModel;
    private Dialog progressDialog;
    protected BaseActivity mActivity;
    //handler对象
    protected Handler mHandler = new Handler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        viewModelProvider = getViewModelProvider();
        initViewModel();
        registerLoadingObserve();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    /**
     * 创建View后执行init方法完成后续工作
     */
    protected abstract void init();

    private void initViewModel(){
        Type superClazz = this.getClass().getGenericSuperclass();
        if(!(superClazz instanceof ParameterizedType)){
            superClazz = this.getClass().getSuperclass().getGenericSuperclass();
            if(!(superClazz instanceof ParameterizedType)){
                throw new RuntimeException("请设置类泛型");
            }
        }
        ParameterizedType parameterizedType = (ParameterizedType) superClazz;
        Class<VM> clazz = (Class<VM>) parameterizedType.getActualTypeArguments()[0];
        mViewModel = viewModelProvider.get(clazz);
    }

    /**
     * 注册loading的观察者
     */
    private void registerLoadingObserve(){
        registerObserve(mViewModel.loadingMessageKey, new Observer<LoadingMessageBean>() {
            @Override
            public void onChanged(@Nullable LoadingMessageBean o) {
                if(o.isShow){
                    showLoading(o.message);
                }else {
                    hideLoading();
                }
            }
        });
    }

    /**
     * 注册观察者
     * @param key key值，此处key值需要与postValue或setValue的key保持一致
     * @param observer 观察者接口
     * @param <T>
     */
    public <T> void registerObserve(String key, Observer<T> observer){
        if(mViewModel != null){
            mViewModel.get(key).observe(this,observer);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!isVisibleToUser()){
            hideLoading();
            progressDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        Log.i("tag----->",getClass().getSimpleName() + " onDestroy");
        viewModelProvider = null;
        //情况当前对象对应在消息队列中未执行完成的消息
        if (mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();
    }

    /**
     * 初始化ViewModelProvider对象
     * @return
     */
    private ViewModelProvider getViewModelProvider() {
        return ViewModelProviders.of(this);
    }


    /**
     * 显示加载弹窗
     */
    protected void showLoading(){
        showLoading(getResources().getString(R.string.txt_loading_common));
    }

    /**
     * fragment对用户是否可见
     */
    protected boolean isVisibleToUser(){
        return isResumed() && getUserVisibleHint();
    }

    /**
     * 显示加载弹窗
     * @param message 显示的消息内容
     */
    protected void showLoading(String message){
        if(TextUtils.isEmpty(message)){
            showLoading();
        }else {
            // 显示Loading框
            if (isVisibleToUser())
                forceShowLoading(message);
        }
    }

    //隐藏加载框
    protected void hideLoading(){
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    //强制显示弹出框
    public void forceShowLoading(String msg) {
        if (progressDialog == null) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_custom_loading, null);
            TextView tips = view.findViewById(R.id.tv_tip);
            tips.setText(msg);

            progressDialog = new Dialog(mActivity, R.style.CustomDialogWithDim);
            progressDialog.setContentView(view);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        } else {
            TextView tips = progressDialog.findViewById(R.id.tv_tip);
            tips.setText(msg);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    /**
     * 显示toast
     */
    protected void showToast(String msg) {
        mActivity.runOnUiThread(()-> Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show());
    }
}
