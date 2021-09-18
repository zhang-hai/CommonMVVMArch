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
     * 使用与父类相同的ViewModel，实现Fragment和Activity数据共享,默认false<br/>
     *
     * <b>Note：当该方法返回true时，泛型VM必须与Activity使用同一个ViewModel</b>
     * @return boolean
     * <li>false：不共享数据</li>
     * <li>true：需要通过ViewModel实现Fragment和Activity数据共享</li>
     */
    protected boolean useSameViewModelOfActivity(){
        return false;
    }

    /**
     * 初始化ViewModelProvider对象,
     * 根据是否使用Activity的ViewModel实现数据共享进行设置对应的owner
     * @return
     */
    private ViewModelProvider getViewModelProvider() {
        return new ViewModelProvider(useSameViewModelOfActivity() ? mActivity : this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        viewModelProvider = getViewModelProvider();
        initViewModel();
        registerLiveDataObserve();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
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
     * 创建View后执行init方法完成后续工作
     */
    protected abstract void init();

    private void initViewModel(){
        Class<VM> vmClass = getVMClass(this.getClass());

        //检测是否设置了与Activity使用相同的ViewModel实现数据共享，则需要检查设置的ViewModel是否与Activity中设置的是否一致
        if (useSameViewModelOfActivity()){
            Class<VM> vmAct = getVMClass(mActivity.getClass());
            if (!vmClass.getName().equals(vmAct.getName())){
                throw new RuntimeException("请与Activity设置的ViewModel泛型保持一致，即："+vmAct.getName());
            }
        }

        mViewModel = viewModelProvider.get(vmClass);
    }

    /**
     * 获取对应class类设置的第1个泛型参数对应的Class
     * @param clazz
     * @return
     */
    private Class<VM> getVMClass(Class<?> clazz){
        Type superClazz = clazz.getGenericSuperclass();
        if(!(superClazz instanceof ParameterizedType)){
            superClazz = clazz.getSuperclass().getGenericSuperclass();
            if(!(superClazz instanceof ParameterizedType)){
                throw new RuntimeException("请设置类泛型");
            }
        }
        ParameterizedType parameterizedType = (ParameterizedType) superClazz;
        Class<VM> vmClazz = (Class<VM>) parameterizedType.getActualTypeArguments()[0];
        return vmClazz;
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
        if (progressDialog != null){
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
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
