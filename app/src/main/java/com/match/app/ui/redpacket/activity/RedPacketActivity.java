package com.match.app.ui.redpacket.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedPacketActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rlLeftBack)
    RelativeLayout rlLeftBack;

    @BindView(R.id.tvBuy)
    TextView tvBuy;
    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.tvCash)
    TextView tvCash;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_redpacket);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        tvTitle.setText("我的钱包");
        rlLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RedPacketActivity.this, RoomCardActivity.class));
            }
        });
        tvBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RedPacketActivity.this, BalanceActivity.class));
            }
        });
        tvCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RedPacketActivity.this, CashPledgeActivity.class));
            }
        });
    }
}
