package com.match.app.ui.login;

import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by john on 2018/6/27.
 * <p>
 * 注册信息完善界面
 */

public class InfoPrefectActivity extends BaseActivity {
    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_personal_perfect);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
    }
}
