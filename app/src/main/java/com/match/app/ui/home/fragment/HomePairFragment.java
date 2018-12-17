package com.match.app.ui.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.classic.common.MultipleStatusView;
import com.match.app.base.BaseFragment;
import com.match.app.message.bean.B301Request;
import com.match.app.message.bean.B301Response;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.ui.adapter.SwipeStackAdapter;
import com.match.app.ui.home.activity.FilterActivity;
import com.match.app.utils.ToastUtils;
import com.matches.fitness.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import link.fls.swipestack.SwipeStack;

public class HomePairFragment extends BaseFragment implements SwipeStack.SwipeStackListener {

    @BindView(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.swipeStack)
    SwipeStack swipeStack;
    @BindView(R.id.rlFilter)
    RelativeLayout rlFilter;
    @BindView(R.id.rlYes)
    RelativeLayout rlYes;
    @BindView(R.id.rlNotice)
    RelativeLayout rlNotice;

    private List<B301Response.FitnessCenterBean> list = new ArrayList<>();
    private SwipeStackAdapter adapter;

    private Boolean showError = true; //只有加载成功过不会再显示error

    public static Fragment newInstance() {
        return new HomePairFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_homepair, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initSwipeCards();
    }

    private void initView() {
        rlFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FilterActivity.class));
            }
        });
        rlYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeStack.swipeTopViewToRight();
            }
        });
        rlNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, DateChoiceActivity.class));
            }
        });
        multipleStatusView.showLoading();
    }

    private void initData() {
        callApi(getActivity(), new B301Request());
    }

    private void callApi(final Context context, B301Request request) {
        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .getFitnessList(request)
                .compose(RxSchedulers.<B301Response>io_main())
                .subscribe(new BaseObserver<B301Response>() {
                    @Override
                    protected void onHandleSuccess(B301Response res) {
                        if (showError) {
                            showError = false;
                        }
                        list.clear();
                        list.addAll(res.getBeans());
                        adapter.notifyDataSetChanged();
                        multipleStatusView.showContent();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(context, msg);
                        if (showError) {
                            multipleStatusView.showError();
                        }
                    }
                });
    }

    private void initSwipeCards() {
        adapter = new SwipeStackAdapter(getActivity());
        adapter.setData(list);
        swipeStack.setAdapter(adapter);
        swipeStack.setListener(this);
    }

    @Override
    public void onViewSwipedToLeft(int position) {

    }

    @Override
    public void onViewSwipedToRight(int position) {

    }

    @Override
    public void onStackEmpty() {
        swipeStack.resetStack();
    }
}
