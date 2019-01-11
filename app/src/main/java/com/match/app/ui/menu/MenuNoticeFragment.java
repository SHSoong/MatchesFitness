package com.match.app.ui.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.match.app.message.bean.B336Request;
import com.match.app.message.bean.B336Response;
import com.match.app.message.bean.B337Request;
import com.match.app.message.bean.BaseResponse;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.utils.ToastUtils;
import com.matches.fitness.R;
import com.match.app.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuNoticeFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BaseQuickAdapter mAdapter;

    public static Fragment newInstance() {
        return new MenuNoticeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menunotice, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<B336Response.MatchApplyBean, BaseViewHolder>(R.layout.itemview_menunotice) {
            @Override
            protected void convert(final BaseViewHolder helper, final B336Response.MatchApplyBean item) {
                ImageView ivYes = helper.getView(R.id.ivYes);
                ImageView ivNo = helper.getView(R.id.ivNo);
                TextView tvItem = helper.getView(R.id.tvItem);
                TextView tvTime = helper.getView(R.id.tvTime);

                tvItem.setText(item.getName()+"邀请你");
                tvTime.setText(item.getCreatedTime()+"去"+item.getAddress());
                ivYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        B337Request req = new B337Request();
                        req.setMatchApplyId(item.getId());
                        req.setStatus(11);
                        callApi(getActivity(), req, helper.getLayoutPosition(), 11);
                    }
                });
                ivNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        B337Request req = new B337Request();
                        req.setMatchApplyId(item.getId());
                        req.setStatus(21);
                        callApi(getActivity(), req, helper.getLayoutPosition(), 21);
                    }
                });
            }
        });

        callApiB336(getActivity(), new B336Request());
    }

    private void callApiB336(final Context context, B336Request request) {
        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .getPairList(request)
                .compose(RxSchedulers.<B336Response>io_main())
                .subscribe(new BaseObserver<B336Response>() {
                    @Override
                    protected void onHandleSuccess(B336Response res) {
                        mAdapter.addData(res.getBeans());
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(context, msg);
                    }
                });
    }

    private void callApi(final Context context, B337Request request, final int position, final int status) {
        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doAgreeOrRefusePair(request)
                .compose(RxSchedulers.<BaseResponse>io_main())
                .subscribe(new BaseObserver<BaseResponse>() {
                    @Override
                    protected void onHandleSuccess(BaseResponse res) {
                        mAdapter.getData().remove(position);
                        mAdapter.notifyDataSetChanged();
                        if (status == 11){
                            ToastUtils.showToast(context, "配对成功！");
                        }
                        if (status == 21){
                            ToastUtils.showToast(context, "取消配对！");
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(context, msg);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
