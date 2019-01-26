package com.match.app.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.match.app.db.AccountDao;
import com.match.app.message.entity.Account;
import com.match.app.message.bean.B001Request;
import com.match.app.message.bean.B001Response;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;
import com.match.app.common.User;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.ui.home.activity.MainActivity;
import com.match.app.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    private final static int REGISTER_CODE = 10001;
    private final static int RESET_PASSWORD_CODE = 10002;
    @BindView(R.id.img_login)
    ImageView imgLogin;
    @BindView(R.id.edt_phone)
    EditText etUserName;
    @BindView(R.id.edt_password)
    EditText etUserPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tvForgotPwd)
    TextView tvForgotPwd;
    @BindView(R.id.tv_wb)
    TextView tvWb;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.tv_tw)
    TextView tvTw;
    @BindView(R.id.tv_fc)
    TextView tvFc;
    private User user;

    private AccountDao dao;
    private String userName;
    private String userPwd;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTile(R.string.login, false);
        user = User.getInstance();
        if (user.isLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }
//        if (!TextUtils.isEmpty(user.getLoginName())) {
//            etUserName.setText(user.getLoginName());
//
//        }
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.anim_avitor);
        Glide.with(mContext)
                .load(user.getLogo())
                .apply(options)
                .into(imgLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = etUserName.getText().toString().trim();
                userPwd = etUserPwd.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    ToastUtils.showToast(LoginActivity.this, "请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(userPwd)) {
                    ToastUtils.showToast(LoginActivity.this, "请输入密码");
                    return;
                }

                B001Request request = new B001Request();
                request.setLoginName(userName);
                request.setPassword(userPwd);
                request.setDeviceType("android");

                callApi(request);
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class),
                        REGISTER_CODE);
            }
        });

        tvForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LoginActivity.this, PasswordResetActivity.class),
                        RESET_PASSWORD_CODE);
            }
        });
        tvWb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mContext, "开发中...");
            }
        });
        tvWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mContext, "开发中...");
            }
        });
        tvTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mContext, "开发中...");
            }
        });
        tvFc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(mContext, "开发中...");
            }
        });
    }

    private void callApi(B001Request request) {
        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doLogin(request)
                .compose(RxSchedulers.<B001Response>io_main())
                .subscribe(new BaseObserver<B001Response>() {
                    @Override
                    protected void onHandleSuccess(B001Response res) {
                        saveUserInfo(res);
                        if (user.getHasInfo() == 1) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(LoginActivity.this, InfoPrefectActivity.class));
                        }

                        finish();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(LoginActivity.this, msg);
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                });
    }

    /*******
     * 保存登录信息
     * @param o
     */
    private void saveUserInfo(B001Response o) {
        user.reset();
        user.setToken(o.getToken());
        user.setBirthday(o.getBirthday());
        user.setHasExp(o.getHasExp());
        user.setLastLoginDate(o.getLastLoginDate());
        user.setLoginName(o.getLoginName());
        user.setLogo(o.getLogo());
        user.setName(o.getName());
        user.setSex(o.getSex());
        user.setLogin(true);
        if(o.getHasInfo() != null)
            user.setHasInfo(o.getHasInfo());
        user.save();
        if (dao == null) {
            dao = new AccountDao(mContext);
        }
        dao.add(new Account(userName, userPwd, user.getToken(), user.getName(), user.getBirthday(),
                user.getSex(), user.getHasExp(), user.getLogo(), user.getLastLoginDate()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REGISTER_CODE:
            case RESET_PASSWORD_CODE:
                if (data != null && resultCode == RESULT_OK) {
                    String phone = data.getStringExtra(PHONE);
                    etUserName.setText(phone);
                }
                break;
        }
    }
}
