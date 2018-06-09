package com.matches.fitness.ui.settings;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.rlAccounts)
    public RelativeLayout rlAccounts;
    @BindView(R.id.rlChangePwd)
    public RelativeLayout rlChangePwd;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
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
    }

}
