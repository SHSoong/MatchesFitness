package com.matches.fitness.ui.home.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

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
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.itemview_gym, getItemDatas()) {
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