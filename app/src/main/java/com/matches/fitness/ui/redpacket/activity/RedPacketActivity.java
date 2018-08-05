package com.matches.fitness.ui.redpacket.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;
import com.matches.fitness.ui.login.LoginActivity;
import com.matches.fitness.ui.login.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedPacketActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvBuy)
    TextView tvBuy;
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

        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RedPacketActivity.this, RoomCardActivity.class));
            }
        });

        tvCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RedPacketActivity.this, BalanceActivity.class));
            }
        });
    }
}
