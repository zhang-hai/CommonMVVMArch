package com.harry2815.mvvm.arch.viewmodel;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.harry2815.mvvm.arch.bean.LoadingMessageBean;
import com.harry2815.mvvm.arch.model.BaseModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by zhanghai on 2019/5/24.
 * function：ViewModel的基类,用于管理LiveData
 */
public class BaseViewModel<M extends BaseModel> extends ViewModel {
    private HashMap<String, MutableLiveData> maps;
    protected M mModel;
    protected LoadingMessageBean mLoadingBean;
    public final String loadingMessageKey = "loadingMessageKey";
    public static final String TOAST_KEY = "toast_key";

    /**
     * 构造函数，初始化ArrayMap
     */
    public BaseViewModel(){
        //初始化map
        maps = new HashMap<>();
        initModel();
        initLoadingMessageBean();
    }

    /**
     * 实例化BaseModel
     */
    private void initModel(){
        Type superClazz = this.getClass().getGenericSuperclass();
        try {
            ParameterizedType parameterizedType = (ParameterizedType) superClazz;
            Class<M> clazz = (Class<M>) parameterizedType.getActualTypeArguments()[0];
            try {
                mModel = clazz.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }catch (ClassCastException e){
            throw new ClassCastException("必须为父类指定一个泛型");
        }
    }

    private void initLoadingMessageBean(){
        mLoadingBean = new LoadingMessageBean("",true);
    }

    /**
     * 显示弹窗
     */
    protected void showLoading(){
        showLoading("");
    }

    protected void showLoading(String message){
        if(mLoadingBean == null){
            initLoadingMessageBean();
        }
        mLoadingBean.isShow = true;
        mLoadingBean.message = message;
        postValue(loadingMessageKey,mLoadingBean);
    }

    /**
     * 隐藏
     */
    protected void hideLoading(){
        if(mLoadingBean == null){
            initLoadingMessageBean();
        }
        mLoadingBean.isShow = false;
        postValue(loadingMessageKey,mLoadingBean);
    }


    /**
     * 控制UI显示toast信息
     * @param toast
     */
    protected void postToast(String toast){
        get(TOAST_KEY).postValue(toast);
    }


    /**
     * 异步更新
     * @param key
     * @param <T> 要更新的value值
     */
    protected <T> void postValue(String key,T value){
        get(key).postValue(value);
    }

    /**
     * 同步更新,必须在主线程中调用
     * @param key
     * @param value
     */
    protected <T> void setValue(String key,@NonNull T value){
        get(key).setValue(value);
    }

    /**
     * 通过key值，直接获取到当前对应的value值
     * @param key
     * @return value
     */
    protected <T> T getValueByKey(@NonNull String key){
        if (TextUtils.isEmpty(key)){
            return null;
        }
        MutableLiveData<T> liveData = get(key);
        return liveData.getValue();
    }

    /**
     * 根据key和clazz从maps中查找相应的MutableLiveData对象
     * @param key 指定的key
     * @param <T>
     * @return
     */
    public  <T> MutableLiveData<T> get(@NonNull final String key) {
        MutableLiveData<T> mutableLiveData = maps.get(key);
        //为mutableLiveData为空，则进行初始化，并加入到maps中
        if(mutableLiveData == null){
            mutableLiveData = new MutableLiveData<T>();
            maps.put(key,mutableLiveData);
        }
        return mutableLiveData;
    }


    /**
     * 在对应的FragmentActivity销毁之后调用
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if(maps != null){
            maps.clear();
        }
        mLoadingBean = null;
        Log.i("tag----->",getClass().getSimpleName() + " onCleared");
    }
}
