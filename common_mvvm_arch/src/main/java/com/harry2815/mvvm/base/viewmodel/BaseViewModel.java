package com.harry2815.mvvm.base.viewmodel;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.harry2815.mvvm.base.bean.LoadingMessageBean;
import com.harry2815.mvvm.base.model.BaseModel;

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
        postValue(null,LoadingMessageBean.class,mLoadingBean);
    }

    /**
     * 隐藏
     */
    protected void hideLoading(){
        if(mLoadingBean == null){
            initLoadingMessageBean();
        }
        mLoadingBean.isShow = false;
        postValue(null,LoadingMessageBean.class,mLoadingBean);
    }

    /**
     * 异步更新
     * @param key
     * @param clazz
     * @param value
     * @param <T>
     */
    protected <T> void postValue(String key,Class<T> clazz,@NonNull T value){
        get(key,clazz).postValue(value);
    }

    /**
     * 同步更新,必须在主线程中调用
     * @param key
     * @param clazz
     * @param value
     * @param <T>
     */
    protected <T> void setValue(String key,Class<T> clazz,@NonNull T value){
        get(key,clazz).setValue(value);
    }

    /**
     * 根据指定的clazz从maps中查找相应的MutableLiveData对象
     * @param clazz 指定的类
     * @param <T>
     * @return
     */
    public  <T> MutableLiveData<T> get(@NonNull Class<T> clazz){
        return get(null,clazz);
    }

    /**
     * 根据key和clazz从maps中查找相应的MutableLiveData对象
     * @param key 指定的key
     * @param clazz 指定的类
     * @param <T>
     * @return
     */
    public  <T> MutableLiveData<T> get(final String key, @NonNull Class<T> clazz) {
        String keyName = "";
        if(TextUtils.isEmpty(key)){
            keyName = clazz.getCanonicalName();
        }else {
            keyName = key;
        }
        MutableLiveData<T> mutableLiveData = maps.get(keyName);
        //为mutableLiveData为空，则进行初始化，并加入到maps中
        if(mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
            maps.put(keyName,mutableLiveData);
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
