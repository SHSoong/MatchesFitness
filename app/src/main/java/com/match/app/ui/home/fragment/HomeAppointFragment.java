package com.match.app.ui.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.match.app.base.BaseFragment;
import com.match.app.message.entity.User;
import com.match.app.config.AppConstant;
import com.match.app.message.bean.B330Request;
import com.match.app.message.bean.BaseResponse;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.ui.home.activity.MainActivity;
import com.match.app.ui.home.activity.SelectDateActivity;
import com.match.app.ui.home.activity.SelectGymActivity;
import com.match.app.utils.ScreenUtils;
import com.match.app.utils.ToastUtils;
import com.matches.fitness.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAppointFragment extends BaseFragment {

    int GYM = 0;
    int DATE = 1;

    @BindView(R.id.iv_anim)
    ImageView iv_anim;
    @BindView(R.id.llSelectGym)
    LinearLayout llSelectGym;
    @BindView(R.id.llSelectDate)
    LinearLayout llSelectDate;
    @BindView(R.id.tvSelectGym)
    TextView tvSelectGym;
    @BindView(R.id.tvSelectDate)
    TextView tvSelectDate;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    private String fitnessCenterId; // 健身房
    private String startTime;       // 开始时间,  yyyy-mm-dd hh:mm:ss, 一共是13个区间 从7点开始每1.5小时为一个时间段 共13个时间段 7-8.5开始  8-9.5结束
    private String endTime;         // 结束时间,  yyyy-mm-dd hh:mm:ss

    public static Fragment newInstance() {
        return new HomeAppointFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homeappoint, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        ScreenUtils.setImageViewHeight(getActivity(), iv_anim);
        Animation rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.appoint_anim);
        iv_anim.startAnimation(rotate);

        llSelectGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), SelectGymActivity.class), GYM);
            }
        });
        llSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new DateTimePicker(getActivity(), new DateTimePicker.OnDateTimePickerListener() {
//                    @Override
//                    public void setOnDateTimePickerListener(String dateStr) {
//                        startTime = dateStr;
//                        tvSelectDate.setText(dateStr);
//                    }
//                }).dateTimePicker();
                startActivityForResult(new Intent(getActivity(), SelectDateActivity.class), DATE);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSubmit(getActivity());
            }
        });
    }

    private void initSubmit(Context context) {
        if (TextUtils.isEmpty(fitnessCenterId)) {
            ToastUtils.showToast(context, "请选择地段");
            return;
        }
        if (TextUtils.isEmpty(startTime)) {
            ToastUtils.showToast(context, "请选择时间");
            return;
        }

        B330Request req = new B330Request();
        req.setFitnessCenterId(fitnessCenterId);
        req.setStartTime(startTime);
        req.setEndTime(endTime);
        req.setSex(User.getInstance().getSex());
        req.setExp(User.getInstance().getHasExp());

        callApi(context, req);
    }

    private void callApi(final Context context, B330Request request) {
        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doB330Request(request)
                .compose(RxSchedulers.<BaseResponse>io_main())
                .subscribe(new BaseObserver<BaseResponse>() {
                    @Override
                    protected void onHandleSuccess(BaseResponse res) {
                        fitnessCenterId = "";
                        startTime = "";
                        tvSelectGym.setText("");
                        tvSelectDate.setText("");
                        ToastUtils.showToast(context, "发布成功！");
                        if (getActivity() != null) ((MainActivity) getActivity()).switchFragment(1);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(context, msg);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == GYM) {
            if (data.getExtras() != null) {
                fitnessCenterId = data.getExtras().getString(AppConstant.INTENT_CENTER_ID);
                tvSelectGym.setText(data.getExtras().getString(AppConstant.INTENT_CENTER_NAME));
            }
        }
        if (resultCode == getActivity().RESULT_OK && requestCode == DATE) {
            if (data.getExtras() != null) {
                int sYear = data.getExtras().getInt(AppConstant.INTENT_INTERVAL_DATE_YEAR, 0);
                int sMonth = data.getExtras().getInt(AppConstant.INTENT_INTERVAL_DATE_MONTH, 0);
                int sDay = data.getExtras().getInt(AppConstant.INTENT_INTERVAL_DATE_DAY, 0);
                String timeStart = data.getExtras().getString(AppConstant.INTENT_INTERVAL_TIME_START);
                String timeEnd = data.getExtras().getString(AppConstant.INTENT_INTERVAL_TIME_END);

                startTime = sYear + "-" + sMonth + "-" + sDay + " " + timeStart + ":00";
                endTime = sYear + "-" + sMonth + "-" + sDay + " " + timeEnd + ":00";
                tvSelectDate.setText(sYear + "年" + sMonth + "月" + sDay + "日" + " " + timeStart + " - " + timeEnd);
            }
        }
    }
}
