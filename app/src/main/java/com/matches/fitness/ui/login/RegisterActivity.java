package com.matches.fitness.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.match.app.message.bean.B001Request;
import com.match.app.message.bean.B001Response;
import com.match.app.message.bean.B002Request;
import com.match.app.message.bean.B002Response;
import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;
import com.matches.fitness.retrofit.ApiService;
import com.matches.fitness.retrofit.manager.BaseObserver;
import com.matches.fitness.retrofit.manager.RetrofitManager;
import com.matches.fitness.retrofit.manager.RxSchedulers;
import com.matches.fitness.ui.home.activity.MainActivity;
import com.matches.fitness.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by john on 2018/6/27.
 * <p>
 * 用户注册
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_validate)
    EditText edtValidate;
    @BindView(R.id.tv_validate)
    TextView tvValidate;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;

    // 是否获取过短信验证码
    private boolean isGetCode = false;

    private String phone;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTile(R.string.register, true);
        tvValidate.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_validate:
                // 请求短信验证码
                getValidate();
                isGetCode = true;
                break;
            case R.id.btn_register: // 注册
                if (!isGetCode) {
                    ToastUtils.showToast(mContext, "请先获取短信验证吗");
                }
                register();
                break;
            case R.id.tv_agreement:
                startActivity(new Intent(mContext, AgreementActivity.class));
                break;
        }
    }

    /*****
     * 获取短信验证码;
     */
    private void getValidate() {
        phone = edtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || phone.length() != 11 || !phone.startsWith("1")) {
            ToastUtils.showToast(mContext, "请输入正确的手机号码！");
            return;
        }

        isGetCode = true;
    }

    /****
     * 注册
     */
    private void register() {

        String phone = edtPhone.getText().toString().trim();
        String validte = edtValidate.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (!phone.equals(this.phone)) {
            ToastUtils.showToast(mContext, "手机号码不能修改!");
            return;
        }
        if (TextUtils.isEmpty(validte)) {
            ToastUtils.showToast(mContext, "验证码不能为空！");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(mContext, "密码不能为空");
            return;
        }

        B002Request request = new B002Request();
        request.setMobile(phone);
        request.setCode(validte);
        request.setPassword(password);
        request.setDeviceType("android");
        callApi(request);
    }

    private void callApi(B002Request request) {
        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doRegister(request)
                .compose(RxSchedulers.<B002Response>io_main())
                .subscribe(new BaseObserver<B002Response>() {
                    @Override
                    protected void onHandleSuccess(B002Response b002Response) {
                        ToastUtils.showToast(mContext, "注册成功");
//                        startActivity(new Intent(mContext, LoginActivity.class));
                        Intent intent = new Intent();
                        intent.putExtra(PHONE, phone);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(mContext, msg);
                    }
                });
    }

}
