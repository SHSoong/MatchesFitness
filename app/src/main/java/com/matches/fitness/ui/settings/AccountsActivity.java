package com.matches.fitness.ui.settings;

import android.widget.TextView;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountsActivity extends BaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_accounts);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        tvTitle.setText("账号管理");
    }
}
