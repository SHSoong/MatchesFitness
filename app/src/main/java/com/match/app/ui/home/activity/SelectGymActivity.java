package com.match.app.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.match.app.config.AppConstant;
import com.match.app.message.bean.B301Request;
import com.match.app.message.bean.B301Response;
import com.match.app.message.bean.B301Response.*;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.utils.ToastUtils;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectGymActivity extends BaseActivity {

    @BindView(R.id.tvLeftBack)
    TextView tvLeftBack;
    @BindView(R.id.ivLeftBack)
    ImageView ivLeftBack;
    @BindView(R.id.rlRightBack)
    RelativeLayout rlRightBack;
    @BindView(R.id.tvRightBack)
    TextView tvRightBack;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseQuickAdapter mAdapter;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_selectgym);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        tvLeftBack.setText("选择健身房地段");
        ivLeftBack.setVisibility(View.GONE);
        rlRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvRightBack.setText("取消");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<FitnessCenterBean, BaseViewHolder>(R.layout.itemview_gym) {
            @Override
            protected void convert(BaseViewHolder helper, B301Response.FitnessCenterBean item) {
                helper.setText(R.id.tvGym, item.getCenterName());
                helper.setText(R.id.tvAddress, item.getAddress());
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FitnessCenterBean bean = (FitnessCenterBean) adapter.getItem(position);
                if (bean == null) return;
                Intent it = new Intent();
                it.putExtra(AppConstant.INTENT_CENTER_NAME, bean.getCenterName());
                it.putExtra(AppConstant.INTENT_CENTER_ID, bean.getCenterId());
                setResult(RESULT_OK, it);
                finish();
            }
        });

        callApi(this, new B301Request());
    }

    private void callApi(final Context context, B301Request request) {
        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doB301Request(request)
                .compose(RxSchedulers.<B301Response>io_main())
                .subscribe(new BaseObserver<B301Response>() {
                    @Override
                    protected void onHandleSuccess(B301Response res) {
                        mAdapter.addData(res.getBeans());
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(context, msg);
                    }
                });
    }

}