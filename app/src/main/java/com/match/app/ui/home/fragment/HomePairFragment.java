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
import com.match.app.message.bean.B334Request;
import com.match.app.message.bean.B334Response;
import com.match.app.message.bean.B335Request;
import com.match.app.message.bean.BaseResponse;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.ui.adapter.SwipeStackAdapter;
import com.match.app.ui.home.activity.FilterActivity;
import com.match.app.ui.im.ConversationListActivity;
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

    private List<B334Response.UserBean> list = new ArrayList<>();
    private SwipeStackAdapter adapter;

    private Boolean showError = true; //只有加载成功过不会再显示error
    private View rootView;// 缓存Fragment view@Override

    public static Fragment newInstance() {
        return new HomePairFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_homepair, container, false);
        }
        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootView已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        ButterKnife.bind(this, rootView);
        return rootView;
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
                startActivity(new Intent(getActivity(), ConversationListActivity.class));
            }
        });
        multipleStatusView.showLoading();
    }

    private void initData() {
        callApi(getActivity(), new B334Request());
    }

    private void callApi(final Context context, B334Request request) {
        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doB334Request(request)
                .compose(RxSchedulers.<B334Response>io_main())
                .subscribe(new BaseObserver<B334Response>() {
                    @Override
                    protected void onHandleSuccess(B334Response res) {
                        if (showError && res.getBeans().size() <= 0) {
                            multipleStatusView.showEmpty();
                        } else {
                            if (showError) {
                                showError = false;
                            }
                            list.clear();
                            list.addAll(res.getBeans());
                            adapter.notifyDataSetChanged();
                            multipleStatusView.showContent();
                        }

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

    private void callB335Api(final Context context, B335Request request) {
        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .doB335Request(request)
                .compose(RxSchedulers.<BaseResponse>io_main())
                .subscribe(new BaseObserver<BaseResponse>() {
                    @Override
                    protected void onHandleSuccess(BaseResponse res) {
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(context, msg);
                    }
                });
    }

    private void initSwipeCards() {
        list.clear();
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
        B334Response.UserBean bean = (B334Response.UserBean) adapter.getItem(position);
        B335Request req = new B335Request();
        req.setUserIdb(bean.getId());
        req.setMatchProfileId(bean.getMatchProfileId());
        callB335Api(getActivity(), req);
    }

    @Override
    public void onStackEmpty() {
        swipeStack.resetStack();
    }
}
