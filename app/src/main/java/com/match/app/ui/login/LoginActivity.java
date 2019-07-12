package com.match.app.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.match.app.base.BaseActivity;
import com.match.app.message.entity.User;
import com.match.app.manager.ActivityManager;
import com.match.app.message.bean.B001Request;
import com.match.app.message.bean.B001Response;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.ui.home.activity.MainActivity;
import com.match.app.utils.ToastUtils;
import com.matches.fitness.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.rlLeftBack)
    RelativeLayout rlLeftBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
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

    private String userName;
    private String userPwd;

    @Override
    protected void onInitBinding() {
        ActivityManager.getInstance().addActivity(this);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTile();

        user = User.getInstance();

        Glide.with(this)
                .load(user.getLogo())
                .apply(new RequestOptions().placeholder(R.mipmap.icon_avatar))
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

                callApi(request);
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        tvForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, PasswordResetActivity.class));
            }
        });
        tvWb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(LoginActivity.this, "开发中...");
            }
        });
        tvWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(LoginActivity.this, "开发中...");
            }
        });
        tvTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(LoginActivity.this, "开发中...");
            }
        });
        tvFc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(LoginActivity.this, "开发中...");
            }
        });
    }

    private void initTile() {
        tvTitle.setText(getString(R.string.login));
        rlLeftBack.setVisibility(View.GONE);
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
                    }
                });
    }

    /*******
     * 保存登录信息
     * @param o a
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
        if (o.getHasInfo() != null)
            user.setHasInfo(o.getHasInfo());
        user.save();
    }
}
