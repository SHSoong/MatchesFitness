package com.matches.fitness.ui.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;
import com.matches.fitness.retrofit.ApiService;
import com.matches.fitness.utils.RetrofitManager;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        Disposable disposable = RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doLogin(userName, userPwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();//TODO
        addSubscription(disposable);
    }
}
