package com.match.app.ui.login;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matches.fitness.R;
import com.match.app.base.BaseActivity;
import com.paradoxie.autoscrolltextview.VerticalTextview;

import butterknife.BindView;
import butterknife.ButterKnife;

/*********
 * 用户协议
 * create by john
 */
public class AgreementActivity extends BaseActivity {

    @BindView(R.id.rlLeftBack)
    RelativeLayout rlLeftBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tv_agreement)
    VerticalTextview tvAgreement;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_agreement);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTile();
    }

    private void initTile() {
        tvTitle.setText(getString(R.string.user_agreement));
        rlLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
