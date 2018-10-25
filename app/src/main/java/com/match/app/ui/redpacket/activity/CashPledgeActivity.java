package com.match.app.ui.redpacket.activity;

import android.widget.TextView;

import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/21 0021.
 */

public class CashPledgeActivity extends BaseActivity{
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_cashpledge);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        tvTitle.setText("押金");

    }
}
