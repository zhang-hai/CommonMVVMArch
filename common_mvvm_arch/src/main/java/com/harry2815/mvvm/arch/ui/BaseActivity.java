package com.harry2815.mvvm.arch.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.harry2815.mvvm.R;
import com.harry2815.mvvm.arch.bean.LoadingMessageBean;
import com.harry2815.mvvm.arch.viewmodel.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by zhanghai on 2019/5/24.
 * function：Activity基类
 */
public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {
    protected ViewModelProvider mViewModelProvider;
    protected VM mViewModel;
    private Dialog progressDialog;
    //handler对象
    protected Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            onHandleMessage(msg);
        }
    };

    /**
     * 消息处理
     * @param msg
     */
    protected void onHandleMessage(Message msg){

    }

    /**
     * 获取ViewModelProvider对象
     * @return
     */
    private ViewModelProvider getViewModelProvider(){
        return new ViewModelProvider(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModelProvider = getViewModelProvider();
        initViewModel();
        //注册事件
        registerLiveDataObserve();
    }

    /**
     * 注册observe，默认注册了loading、toast对应的observe
     *
     * 需要给新的LiveData注册observe时可重写该方法
     *
     */
    protected void registerLiveDataObserve(){
        registerBaseObserve();
    }

    /**
     * 初始化viewModel
     */
    private void initViewModel(){
        //获取继承父类的泛型
        Type superClazz = this.getClass().getGenericSuperclass();
        if(!(superClazz instanceof ParameterizedType)){
            superClazz = this.getClass().getSuperclass().getGenericSuperclass();
            if(!(superClazz instanceof ParameterizedType)){
                throw new RuntimeException("请设置类泛型");
            }
        }
        //获取第一个泛型参数
        ParameterizedType parameterizedType = (ParameterizedType) superClazz;
        Class<VM> vmClass = (Class<VM>) parameterizedType.getActualTypeArguments()[0];
        //两种实例化mViewModel方式
        //1.通过调用vmClass.newInstance();
        //2.通过使用viewModelProvider.get()
        //本例中使用第2中方式，因为在实例化的时候，viewModel会被ViewModelProvider管理起来
        mViewModel = mViewModelProvider.get(vmClass);
//        try {
//            mViewModel = vmClass.newInstance();
//        } catch (IllegalAccessException | InstantiationException e) {
//            e.printStackTrace();
//            Log.e(getClass().getSimpleName(),"ViewModel泛型对象构造失败!");
//        }
    }

    /**
     * 注册loading的观察者
     */
    private void registerBaseObserve(){
        registerObserve(mViewModel.loadingMessageKey, (Observer<LoadingMessageBean>) value -> {
            if(value != null && value.isShow){
                showLoading(value.message);
            }else {
                hideLoading();
            }
        });

        //注册监听toast事件
        registerObserve(BaseViewModel.TOAST_KEY, (Observer<String>) s -> showToast(s));
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
    protected void onStop() {
        super.onStop();
        if(isFinishing()){
            hideLoading();
            progressDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        Log.i("tag----->",getClass().getSimpleName() + " onDestroy");
        mViewModelProvider = null;
        //情况当前对象对应在消息队列中未执行完成的消息
        if (mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }

        super.onDestroy();
    }


    /**
     * 显示加载弹窗
     */
    protected void showLoading(){
        showLoading(getResources().getString(R.string.txt_loading_common));
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
            if (!isFinishing())
                forceShowLoading(message);
        }
    }

    //隐藏加载框
    protected void hideLoading(){
        if (progressDialog != null){
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
    }

    public void forceShowLoading(String msg) {
        if (progressDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_custom_loading, null);
            TextView tips = view.findViewById(R.id.tv_tip);
            tips.setText(msg);

            progressDialog = new Dialog(this, R.style.CustomDialogWithDim);
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
        runOnUiThread(()->Toast.makeText(this, msg, Toast.LENGTH_SHORT).show());
    }


}
