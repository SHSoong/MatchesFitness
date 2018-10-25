package com.match.app.ui.redpacket.activity;

import android.view.View;
import android.widget.TextView;

import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/21 0021.
 */

public class BalanceActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvCash)
    TextView tvCash;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_balance);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        tvTitle.setText("余额");

        tvCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
