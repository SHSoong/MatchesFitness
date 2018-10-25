package com.match.app.ui.redpacket.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.matches.fitness.R;
import com.match.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/21 0021.
 */

public class RoomCardActivity extends BaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseQuickAdapter mAdapter;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_roomcard);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);

        tvTitle.setText("健身房卡");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.itemview_card, getItemDatas()) {
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
