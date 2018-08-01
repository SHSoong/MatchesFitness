package com.matches.fitness.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;
import com.matches.fitness.base.BaseEntity;
import com.matches.fitness.entity.LoginEntity;
import com.matches.fitness.retrofit.ApiService;
import com.matches.fitness.retrofit.manager.BaseObserver;
import com.matches.fitness.retrofit.manager.RetrofitManager;
import com.matches.fitness.retrofit.manager.RxSchedulers;
import com.matches.fitness.ui.home.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

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

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                String userName = etUserName.getText().toString().trim();
                String userPwd = etUserPwd.getText().toString().trim();
                if (userName.isEmpty()) {
                    return;
                }
                if (userPwd.isEmpty()) {
                    return;
                }
                callApi(userName, userPwd);
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
    }

    private void callApi(String userName, String userPwd) {
        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doLogin(userName, userPwd)
                .compose(RxSchedulers.<BaseEntity<LoginEntity>>io_main())
                .subscribe(new BaseObserver<LoginEntity>() {
                    @Override
                    protected void onHandleSuccess(LoginEntity loginModel) {

                    }

                    @Override
                    protected void onHandleError(String msg) {

                    }
                });
    }
}
