package com.match.app.ui.settings;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rlAccounts)
    RelativeLayout rlAccounts;
    @BindView(R.id.rlChangePwd)
    RelativeLayout rlChangePwd;
    @BindView(R.id.rlPersonalInfo)
    RelativeLayout rlPersonalInfo;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        tvTitle.setText("设置");

        rlAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, AccountsActivity.class));
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
    }

}
