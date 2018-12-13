package com.match.app.ui.home.activity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.match.app.customer.CustomLoadMoreView;
import com.match.app.message.bean.B332Request;
import com.match.app.message.bean.B332Response;
import com.match.app.retrofit.ApiService;
import com.match.app.retrofit.manager.BaseObserver;
import com.match.app.retrofit.manager.RetrofitManager;
import com.match.app.retrofit.manager.RxSchedulers;
import com.match.app.utils.ToastUtils;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;
import com.match.app.ui.home.RecyclerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    private long totalSize = 0;

    @BindView(R.id.tvLeft)
    TextView tvLeft;

    @BindView(R.id.etKeyword)
    EditText etKeyword;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseQuickAdapter mAdapter;
    private Integer page = 1;       // 页号
    private Integer pageSize = 10;   // 页号大小，0或者null为不分页
    private String keyword;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new RecyclerItemDecoration(15));
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<B332Response.UserBean, BaseViewHolder>(R.layout.itemview_search) {
            @Override
            protected void convert(BaseViewHolder helper, B332Response.UserBean item) {

            }
        });
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mAdapter.getItemCount() >= totalSize) {
                    mAdapter.loadMoreEnd();
                }
                initRequest();
            }
        }, recyclerView);

        etKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                page = 1;       // 页号
                keyword = charSequence.toString();
                initRequest();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        initRequest();
    }

    private void initRequest() {
        B332Request request = new B332Request();
        request.setKeyword(keyword);
        request.setPage(page++);
        request.setPageSize(pageSize);
        callApi(SearchActivity.this, request);
    }

    private void callApi(final Context context, B332Request request) {
        RetrofitManager.getInstance().getRetrofit()
                .create(ApiService.class)
                .getSearchResultList(request)
                .compose(RxSchedulers.<B332Response>io_main())
                .subscribe(new BaseObserver<B332Response>() {
                    @Override
                    protected void onHandleSuccess(B332Response res) {
                        totalSize = res.getPageCount();
//                        ToastUtils.showToast(SearchActivity.this, "" + totalSize);
                        if (mAdapter.isLoading()) {
                            mAdapter.addData(res.getBeans());
                        } else {
                            mAdapter.setNewData(res.getBeans());
                        }
                        if (page >= totalSize) {
                            mAdapter.loadMoreEnd();
                            ToastUtils.showToast(SearchActivity.this, "结束");
                        } else {
                            mAdapter.loadMoreComplete();
                            ToastUtils.showToast(SearchActivity.this, "完成");
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        mAdapter.loadMoreFail();
                        ToastUtils.showToast(context, msg);
                    }
                });
    }
}
