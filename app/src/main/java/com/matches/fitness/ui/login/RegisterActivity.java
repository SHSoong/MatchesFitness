package com.matches.fitness.ui.login;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by john on 2018/6/27.
 * <p>
 * 用户注册
 */

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
    }
}
