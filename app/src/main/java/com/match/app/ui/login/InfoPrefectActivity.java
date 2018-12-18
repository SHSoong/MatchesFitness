package com.match.app.ui.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.match.app.message.bean.B003Response;
import com.match.app.message.bean.B005Request;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.ui.home.activity.MainActivity;
import com.match.app.utils.ToastUtils;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by john on 2018/6/27.
 * <p>
 * 注册信息完善界面
 */

public class InfoPrefectActivity extends BaseActivity {

    @BindView(R.id.edt_nickname)
    EditText edtNickName;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.rg_experience)
    RadioGroup rgExperience;
    @BindView(R.id.btn_perfect)
    Button btnPerfect;

    // 性别
    private int sex = 0;

    // 1 有经验，0 无
    private int hasExperience = 1;

    private String nickName;
    private Date birthDay;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_personal_perfect);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTile(R.string.personal_info_prefect, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });
        tvBirthday.setOnClickListener(this);
        rgSex.check(R.id.rdb_gent);
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                sex = i;
            }
        });
        rgExperience.check(R.id.rdb_yes);
        rgExperience.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == 0) {
                    hasExperience = 1;
                } else {
                    hasExperience = 0;
                }
            }
        });
        btnPerfect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_birthday:
                Calendar ca = Calendar.getInstance();
                int mYear = ca.get(Calendar.YEAR);
                int mMonth = ca.get(Calendar.MONTH);
                int mDay = ca.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                birthDay = new Date(year, month, dayOfMonth);
                                tvBirthday.setText(year + "年-" + (month + 1) + "月-" + dayOfMonth + "日 ");
                                final String data = (month + 1) + "月-" + dayOfMonth + "日 ";
                            }
                        },
                        mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.btn_perfect:

                nickName = edtNickName.getText().toString().trim();
                if (TextUtils.isEmpty(nickName)) {
                    ToastUtils.showToast(mContext, "昵称不能为空!");
                    return;
                }
                if (birthDay == null) {
                    ToastUtils.showToast(mContext, "请先选择您的出生日期!");
                    return;
                }


                B005Request request = new B005Request();
                request.setName(nickName);
                request.setBirthday(birthDay);
                request.setSex(Integer.valueOf(sex));
                request.setHasExp(Integer.valueOf(hasExperience));

                RetrofitManager.getInstance().getRetrofit()
                        .create(ApiService.class)
                        .doPerfeect(request)
                        .compose(RxSchedulers.<B003Response>io_main())
                        .subscribe(new BaseObserver<B003Response>() {
                            @Override
                            protected void onHandleSuccess(B003Response res) {

                            }

                            @Override
                            protected void onHandleError(String msg) {
                                ToastUtils.showToast(mContext, msg);
                            }
                        });

                break;
        }
    }
}
