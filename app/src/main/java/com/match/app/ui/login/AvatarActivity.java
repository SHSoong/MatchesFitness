package com.match.app.ui.login;

import android.content.Intent;
import android.widget.TextView;

import com.match.app.base.BaseActivity;
import com.match.app.common.User;
import com.match.app.customer.ArcImageView;
import com.match.app.db.AccountDao;
import com.match.app.message.bean.B005Request;
import com.match.app.message.bean.B005Response;
import com.match.app.message.entity.Account;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.ui.home.activity.MainActivity;
import com.match.app.utils.ToastUtils;
import com.matches.fitness.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AvatarActivity extends BaseActivity {

    @BindView(R.id.arcImageView)
    ArcImageView arcImageView;
    @BindView(R.id.tvUploadAvatar)
    TextView tvUploadAvatar;

    // 性别
    private int sex = 0;
    // 1 有经验，0 无
    private int hasExperience = 1;
    private String nickName;
    private String birthDay;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_avatar);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
    }

    public void initCallB005(){
        B005Request request = new B005Request();
        request.setName(nickName);
        request.setBirthday(birthDay);
        request.setSex(Integer.valueOf(sex));
        request.setHasExp(Integer.valueOf(hasExperience));

        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doPerfeect(request)
                .compose(RxSchedulers.<B005Response>io_main())
                .subscribe(new BaseObserver<B005Response>() {
                    @Override
                    protected void onHandleSuccess(B005Response res) {
                        if (res.isSuccess()) {
                            User.getInstance().setHasInfo(Integer.valueOf(1));
                            User.getInstance().setSex(Integer.valueOf(sex));
                            User.getInstance().setName(nickName);
                            User.getInstance().setBirthday(birthDay);
                            User.getInstance().save();
                            update();
                            startActivity(new Intent(mContext, MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(mContext, msg);
                    }
                });
    }

    private AccountDao dao;
    private Account account;
    private void update() {
        if (dao == null) {
            dao = new AccountDao(mContext);
        }
        account = dao.queryByAccount(User.getInstance().getLoginName());
        if (account != null) {
            account.setName(nickName);
            account.setBirthday(birthDay);
            account.setSex(Integer.valueOf(sex));
            account.setHasExp(Integer.valueOf(hasExperience));
            dao.update(account);
        }
    }
}
