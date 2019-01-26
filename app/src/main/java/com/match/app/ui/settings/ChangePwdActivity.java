package com.match.app.ui.settings;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePwdActivity extends BaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rlLeftBack)
    RelativeLayout rlLeftBack;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_changepwd);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        tvTitle.setText("修改密码");
        rlLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
