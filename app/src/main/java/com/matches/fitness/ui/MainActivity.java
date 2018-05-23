package com.matches.fitness.ui;

import android.widget.TextView;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tvTest)
    public TextView tvTest;

    @BindString(R.string.app_name)
    public String appName;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        tvTest.setText(appName);
    }

}
