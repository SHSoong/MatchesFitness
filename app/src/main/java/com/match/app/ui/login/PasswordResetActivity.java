package com.match.app.ui.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.match.app.message.entity.User;
import com.match.app.message.bean.B003Request;
import com.match.app.message.bean.B003Response;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by john on 2018/6/28.
 * 密码重置界面
 */

public class PasswordResetActivity extends BaseActivity implements View.OnClickListener {

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
    private boolean isGetCode = false;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTile();
        tvGetIdentify.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    private void initTile() {
        tvTitle.setText(getString(R.string.reset_password));
        rlLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_identify:
                phone = edtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone) || phone.length() != 11 || !phone.startsWith("1")) {
                    ToastUtils.showToast(PasswordResetActivity.this, "请输入正确的手机号码!");
                    edtPhone.findFocus();
                    return;
                }
                isGetCode = true;
                break;
            case R.id.btn_commit:
                if (!isGetCode) {
                    ToastUtils.showToast(this, "请先获取短信验证码!");
                    return;
                }
                resetPassword();
                break;

        }
    }

    private void resetPassword() {
        String phone = edtPhone.getText().toString().trim();
        String validte = edtIdentify.getText().toString().trim();
        String password = edtNewPasswrod.getText().toString().trim();
        String passwordConfirm = edtNewPasswordConfirm.getText().toString().trim();
        if (!phone.equals(this.phone)) {
            ToastUtils.showToast(this, "手机号码不能修改!");
            return;
        }
        if (TextUtils.isEmpty(validte)) {
            ToastUtils.showToast(this, "验证码不能为空！");
            edtNewPasswrod.findFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(this, "新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(passwordConfirm)) {
            ToastUtils.showToast(this, "确认密码不能为空");
            return;
        }
        if (!password.equals(passwordConfirm)) {
            ToastUtils.showToast(this, "两次输入密码不一致");
            return;
        }
        B003Request request = new B003Request();
        request.setMobile(phone);
        request.setCode(validte);
        request.setPassword(password);
        callApi(request);

    }

    private void callApi(B003Request request) {
        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doRestPassword(request)
                .compose(RxSchedulers.<B003Response>io_main())
                .subscribe(new BaseObserver<B003Response>() {
                    @Override
                    protected void onHandleSuccess(B003Response b003Response) {
                        ToastUtils.showToast(PasswordResetActivity.this, "密码重置成功");
                        User.getInstance().setLoginName(phone);
                        User.getInstance().save();
                        finish();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(PasswordResetActivity.this, msg);
                    }
                });
    }
}
