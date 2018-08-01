package com.matches.fitness.ui.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by john on 2018/6/28.
 * 密码重置界面
 */

public class PasswordResetActivity extends BaseActivity {
    @BindView(R.id.rlLeftBack)
    RelativeLayout rlLeftBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_identify)
    EditText edtIdentify;
    @BindView(R.id.tv_get_identify)
    TextView tvGetIdentify;
    @BindView(R.id.edt_new_password)
    EditText edtNewPasswrod;
    @BindView(R.id.edt_new_password_confirm)
    EditText edtNewPasswordConfirm;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private String phone;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        tvTitle.setText(R.string.reset_password);


        tvGetIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtPhone.getText().toString().trim().length() == 11 &&
                        edtPhone.getText().toString().trim().startsWith("1")) {
                    phone = edtPhone.getText().toString().trim();
                    // 请求短信验证码
                    return;
                }
            }
        });

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtNewPasswrod.getText().toString().trim())) {
                    //
                    edtNewPasswrod.findFocus();
                    return;
                }
                if (TextUtils.isEmpty(edtNewPasswordConfirm.getText().toString().trim())) {
                    //
                    return;
                }
                if (!edtNewPasswrod.getText().toString().trim().equals(
                        edtNewPasswordConfirm.getText().toString().trim())) {
                    //
                    Toast.makeText(mContext, R.string.error_password_difference, Toast.LENGTH_SHORT).show();
                    return;
                }
                // 修改密码

            }
        });
    }
}
