package com.match.app.ui.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.matches.fitness.R;
import com.match.app.base.BaseFragment;
import com.match.app.ui.adapter.ViewPagerAdapter;
import com.match.app.ui.info.ModifyActivity;
import com.match.app.ui.redpacket.activity.RedPacketActivity;
import com.match.app.ui.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuFragment extends BaseFragment {

    @BindView(R.id.tabLayout)
    public TabLayout tabLayout;
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    @BindView(R.id.tvSettings)
    public TextView tvSettings;
    @BindView(R.id.tvRedPacket)
    public TextView tvRedPacket;
    @BindView(R.id.ll_modify)
    LinearLayout llModify;

    private List<String> tabList = new ArrayList<>();
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, null);
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTabLayout();
        init();
    }

    private void init(){
        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });
        tvRedPacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RedPacketActivity.class));
            }
        });
        llModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity,ModifyActivity.class));
            }
        });
    }

    private void initTabLayout() {
        tabList.add("健身配对");
        tabList.add("通知");
        for (int i = 0; i < tabList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));
        }

        // 创建一个集合,装填Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(MenuPairFragment.newInstance());
        fragments.add(MenuNoticeFragment.newInstance());
        //viewpager
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), fragments, tabList);
        viewPager.setAdapter(viewPagerAdapter);
        //TabLayout加载viewpager
        tabLayout.setupWithViewPager(viewPager);
    }
}
