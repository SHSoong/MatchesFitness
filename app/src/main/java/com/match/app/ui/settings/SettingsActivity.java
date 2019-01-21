package com.match.app.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.match.app.MyApp;
import com.match.app.common.User;
import com.match.app.ui.im.ConversationListActivity;
import com.match.app.ui.login.LoginActivity;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rlLeftBack)
    RelativeLayout rlLeftBack;

    @BindView(R.id.rlAccounts)
    RelativeLayout rlAccounts;
    @BindView(R.id.rlChangePwd)
    RelativeLayout rlChangePwd;
    @BindView(R.id.rlPersonalInfo)
    RelativeLayout rlPersonalInfo;
    @BindView(R.id.rl_exit)
    RelativeLayout rlExit;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        tvTitle.setText("设置");
        rlLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rlAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, ConversationListActivity.class));
            }
        });
        rlChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, ChangePwdActivity.class));
            }
        });
        rlPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        rlExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApp.logout(SettingsActivity.this);
            }
        });
    }

}
