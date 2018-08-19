package com.matches.fitness.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.match.app.message.bean.B001Request;
import com.match.app.message.bean.B001Response;
import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;
import com.matches.fitness.common.User;
import com.matches.fitness.retrofit.ApiService;
import com.matches.fitness.retrofit.manager.BaseObserver;
import com.matches.fitness.retrofit.manager.RetrofitManager;
import com.matches.fitness.retrofit.manager.RxSchedulers;
import com.matches.fitness.ui.home.activity.MainActivity;
import com.matches.fitness.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTile(R.string.login, false);
        user = User.getInstance();
        if (!TextUtils.isEmpty(user.getLoginName())) {
            etUserName.setText(user.getLoginName());

        }
        Glide.with(mContext)
                .load(user.getLogo())
                .placeholder(R.mipmap.anim_avitor)
                .error(R.mipmap.anim_avitor)
                .dontAnimate()
                .into(imgLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = etUserName.getText().toString().trim();
                String userPwd = etUserPwd.getText().toString().trim();
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
                    protected void onHandleSuccess(B001Response b001Response) {
                        saveUserInfo(b001Response);
                        ToastUtils.showToast(LoginActivity.this, "" + b001Response.getLoginName());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
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
        user.save();
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
