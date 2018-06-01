package com.matches.fitness.ui.settings;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.tvAccounts)
    public TextView tvAccounts;
    @BindView(R.id.tvChangePwd)
    public TextView tvChangePwd;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        tvAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, AccountsActivity.class));
            }
        });
        tvChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, ChangePwdActivity.class));
            }
        });
    }

}
