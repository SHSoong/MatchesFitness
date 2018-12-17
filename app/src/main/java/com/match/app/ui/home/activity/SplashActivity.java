package com.match.app.ui.home.activity;

import android.content.Intent;

import com.match.app.base.BaseActivity;
import com.match.app.common.User;
import com.match.app.ui.login.InfoPrefectActivity;
import com.match.app.ui.login.LoginActivity;

public class SplashActivity extends BaseActivity {
    private User user;

    @Override
    protected void onInitBinding() {

    }

    @Override
    protected void onInit() {
        user = User.getInstance();
        if (user.isLogin()) {
            if (user.getHasInfo() == 1) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, InfoPrefectActivity.class));
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}
