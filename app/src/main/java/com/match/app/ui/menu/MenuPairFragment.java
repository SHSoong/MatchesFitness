package com.matches.fitness.ui.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.matches.fitness.R;
import com.matches.fitness.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuPairFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseQuickAdapter mAdapter;

    public static Fragment newInstance() {
        return new MenuPairFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menupair, container, false);
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
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.itemview_menupair, getItemDatas()) {
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
