package com.matches.fitness.ui.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;
import com.matches.fitness.base.BaseEntity;
import com.matches.fitness.entity.LoginEntity;
import com.matches.fitness.retrofit.ApiService;
import com.matches.fitness.retrofit.manager.BaseObserver;
import com.matches.fitness.retrofit.manager.RetrofitManager;
import com.matches.fitness.retrofit.manager.RxSchedulers;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etUserName)
    public EditText etUserName;
    @BindView(R.id.etUserPwd)
    public EditText etUserPwd;
    @BindView(R.id.btnLogin)
    public Button btnLogin;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onInit() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
