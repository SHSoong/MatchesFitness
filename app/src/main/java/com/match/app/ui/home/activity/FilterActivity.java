package com.match.app.ui.home.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.match.app.base.BaseActivity;
import com.match.app.ui.home.RecyclerItemDecoration;
import com.matches.fitness.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity extends BaseActivity {

    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView3;
    @BindView(R.id.recyclerView4)
    RecyclerView recyclerView4;

    BaseQuickAdapter mAdapter;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_filter);
    }

    private void initTitleBar() {
        initTile("筛选", true);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initTitleBar();

        recyclerView1.setPadding(20, 20, 20, 20);
        recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView1.addItemDecoration(new RecyclerItemDecoration(20));
        recyclerView1.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.itemview_filter, getItemDatas()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
            }
        });

        recyclerView2.setPadding(10, 10, 10, 10);
        recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView2.addItemDecoration(new RecyclerItemDecoration(10));
        recyclerView2.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.itemview_filter, getItemDatas()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
            }
        });

        recyclerView3.setPadding(10, 10, 10, 10);
        recyclerView3.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView3.addItemDecoration(new RecyclerItemDecoration(10));
        recyclerView3.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.itemview_filter, getItemDatas()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
            }
        });

        recyclerView4.setPadding(10, 10, 10, 10);
        recyclerView4.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView4.addItemDecoration(new RecyclerItemDecoration(10));
        recyclerView4.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.itemview_filter, getItemDatas()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
            }
        });
    }

    private List<String> getItemDatas() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add("item" + i);
        }
        return list;
    }
}
