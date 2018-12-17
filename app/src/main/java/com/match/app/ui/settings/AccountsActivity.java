package com.match.app.ui.settings;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountsActivity extends BaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.accounts_rcy)
    RecyclerView accountsRcy;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_accounts);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTile("账号管理",true);
    }
}
