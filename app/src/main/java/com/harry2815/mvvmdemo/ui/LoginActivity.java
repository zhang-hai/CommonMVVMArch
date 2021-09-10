package com.harry2815.mvvmdemo.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.harry2815.mvvm.arch.ui.BaseActivity;
import com.harry2815.mvvmdemo.R;
import com.harry2815.mvvmdemo.bean.User;
import com.harry2815.mvvmdemo.viewmodel.LoginViewModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

/**
 * Created by zhanghai on 2019/5/24.
 * function：登录Activity
 */
@EActivity(R.layout.activity_login_layout)
public class LoginActivity extends BaseActivity<LoginViewModel> {

    @ViewById(R.id.et_login_name)
    EditText et_login_name;
    @ViewById(R.id.et_login_password)
    EditText et_login_password;
    @ViewById(R.id.tv_login_result)
    TextView tv_login_result;

    @AfterViews
    void init(){
        registerObserve(mViewModel.userKey,  new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if(user != null){
                    //数据更新刷新界面
                    tv_login_result.setText(String.format(Locale.CHINA,"登录结果：{用户名：%s，年龄：%d}",user.username,user.age));

                    startActivity(new Intent(LoginActivity.this,MainActivity_.class));
                    finish();
                }else {
                    //异常

                }
            }
        });
    }

    @Click({R.id.btn_login})
    void onClick(View v){
        if(v.getId() == R.id.btn_login){
            mViewModel.login(et_login_name.getText().toString().trim(),et_login_password.getText().toString().trim());
        }
    }


}
