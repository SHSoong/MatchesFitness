package com.matches.fitness.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.matches.fitness.R;
import com.matches.fitness.ui.home.fragment.HomeAppointFragment;
import com.matches.fitness.ui.home.fragment.HomePairFragment;
import com.matches.fitness.ui.menu.MenuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends SlidingFragmentActivity {

    @BindView(R.id.rlMenu)
    RelativeLayout rlMenu;
    @BindView(R.id.tvAppoint)
    public TextView tvAppoint;
    @BindView(R.id.tvPair)
    public TextView tvPair;
    @BindView(R.id.ivSearch)
    public ImageView ivSearch;

    HomeAppointFragment homeAppointFragment;
    HomePairFragment homePairFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSlidingMenu();
        initView();
        initDefFragment();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setStatusBarColor(R.color.graySolBg);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    public void setStatusBarColor(int colorPrimary) {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarColor(colorPrimary)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .navigationBarColor(colorPrimary)
                .init();
    }

    private void initSlidingMenu() {
        ButterKnife.bind(this);

        setBehindContentView(R.layout.activity_menu);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, new MenuFragment()).commit();
        // 设置滑动菜单的属性值
        getSlidingMenu().setMode(SlidingMenu.LEFT); //设定模式，SlidingMenu在左边
        getSlidingMenu().setBehindOffsetRes(R.dimen.sliding_menu_offset); //配置slidingmenu偏移出来的尺寸
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE); //全屏都可以拖拽触摸，打开slidingmenu
        getSlidingMenu().setFadeDegree(0.35F);// SlidingMenu滑动时的渐变程度
    }

    private void initView() {
        rlMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSlidingMenu().toggle();
            }
        });

        tvAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment(0);//切换Fragment
                setTabState(tvAppoint, ContextCompat.getColor(MainActivity.this, R.color.black));//设置Tab状态
            }
        });
        tvPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment(1);//切换Fragment
                setTabState(tvPair, ContextCompat.getColor(MainActivity.this, R.color.black));//设置Tab状态
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });
    }

    private void initDefFragment() {
        switchFragment(1);//切换Fragment
        setTabState(tvPair, ContextCompat.getColor(MainActivity.this, R.color.black));//设置Tab状态
    }

    private void switchFragment(int i) {
        resetTabState();//reset the tab state
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (i) {
            case 0:
                if (homeAppointFragment == null) {
                    homeAppointFragment = new HomeAppointFragment();
                }
                transaction.replace(R.id.fl_content, homeAppointFragment);
                break;
            case 1:
                if (homePairFragment == null) {
                    homePairFragment = new HomePairFragment();
                }
                transaction.replace(R.id.fl_content, homePairFragment);
                break;
        }
        transaction.commit();
    }

    private void setTabState(TextView textView, int color) {
        textView.setTextColor(color);
    }

    private void resetTabState() {
        setTabState(tvAppoint, ContextCompat.getColor(this, R.color.gray));
        setTabState(tvPair, ContextCompat.getColor(this, R.color.gray));
    }

}
