package com.match.app.ui.home.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;
import com.match.app.ui.home.RecyclerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.tvLeft)
    TextView tvLeft;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseQuickAdapter mAdapter;

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
        recyclerView.setPadding(10, 10, 10, 10);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new RecyclerItemDecoration(10));
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.itemview_search, getItemDatas()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
            }
        });
    }

    private List<String> getItemDatas() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("item" + i);
        }
        return list;
    }
}
