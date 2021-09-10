package com.harry2815.mvvmdemo.ui;

import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.harry2815.mvvm.arch.ui.BaseActivity;
import com.harry2815.mvvmdemo.R;
import com.harry2815.mvvmdemo.viewmodel.MainViewModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity<MainViewModel> {
    @ViewById(R.id.tv_main_title)
    TextView tv_main_title;


    @AfterViews
    void init(){
        // todo 解决横竖屏切换时，重复fragment
        //  解决方法一：在相应的Activity配置中加上android:configChanges="orientation|keyboardHidden"设置，这样切换时就不会销毁FragmentActivity,所有的Fragment的状态及视图也就会保持。
        //  解决方法二：在使用FragmentTransaction.add()方法添加fragment时设置第三个tag参数,随后在还原时可通过FragmentManager.findFragmentByTag()方法找回还原的fragment.
        MainResFragment fragment = (MainResFragment) getSupportFragmentManager().findFragmentByTag("faqFragment");
        if(fragment == null){
            fragment = new MainResFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.frame_content, fragment,"faqFragment").commit();
        }else {
            getSupportFragmentManager().beginTransaction().show(fragment);
        }

        registerObserve("test", new Observer<List>() {
            @Override
            public void onChanged(@Nullable List list) {
                if(list != null && list.size() > 0){
                    tv_main_title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tv_main_title.setTextSize(20);
                    tv_main_title.setText(String.valueOf(list.get(0)));
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewModel.postTestValue();
            }
        },3000);
    }


}
