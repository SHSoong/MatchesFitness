package com.match.app.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.match.app.base.BaseFragmentActivity;
import com.match.app.customer.CustomDrawerLayout;
import com.match.app.customer.MyViewPager;
import com.match.app.customer.SwipeDirection;
import com.match.app.message.entity.User;
import com.match.app.ui.adapter.MyViewPagerAdapter;
import com.match.app.ui.adapter.ViewPagerAdapter;
import com.match.app.ui.home.fragment.HomeAppointFragment;
import com.match.app.ui.home.fragment.HomePairFragment;
import com.match.app.ui.info.ModifyActivity;
import com.match.app.ui.menu.MenuNoticeFragment;
import com.match.app.ui.menu.MenuPairFragment;
import com.match.app.ui.redpacket.activity.RedPacketActivity;
import com.match.app.ui.settings.SettingsActivity;
import com.match.app.utils.ToastUtils;
import com.matches.fitness.R;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.rlMenu)
    RelativeLayout rlMenu;
    @BindView(R.id.tvAppoint)
    public TextView tvAppoint;
    @BindView(R.id.tvPair)
    public TextView tvPair;
    @BindView(R.id.ivSearch)
    public ImageView ivSearch;
    @BindView(R.id.viewPager)
    public MyViewPager viewPager;
    @BindView(R.id.drawerLayout)
    public CustomDrawerLayout drawerLayout;
    @BindView(R.id.rlMenuLayout)
    public RelativeLayout rlMenuLayout;

    private MyViewPagerAdapter adapter;

    //menu
    @BindView(R.id.circleImageView)
    public CircleImageView circleImageView;
    @BindView(R.id.tabLayout)
    public TabLayout tabLayout;
    @BindView(R.id.tabViewPager)
    public MyViewPager tabViewPager;
    @BindView(R.id.tvSettings)
    public TextView tvSettings;
    @BindView(R.id.tvRedPacket)
    public TextView tvRedPacket;
    @BindView(R.id.ll_modify)
    LinearLayout llModify;
    @BindView(R.id.tvName)
    TextView tvName;

    private List<String> tabList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PushAgent.getInstance(this).onAppStart();

        setContentView(R.layout.activity_main);

        initMain();
        initMenu();
    }

    private void initMain() {
        initView();
        initDefFragment();
        switchFragment(0);
    }

    private void initView() {
        ButterKnife.bind(this);

        rlMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(rlMenuLayout);
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
        List<Fragment> list = new ArrayList<>();
        list.add(new HomeAppointFragment());
        list.add(new HomePairFragment());
        adapter = new MyViewPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.setSwipeDirection(SwipeDirection.none);
    }

    public void switchFragment(int i) {
        resetTabState();//reset the tab state
        switch (i) {
            case 0:
                viewPager.setCurrentItem(0);
                setTabState(tvAppoint, ContextCompat.getColor(MainActivity.this, R.color.black));
                break;
            case 1:
                viewPager.setCurrentItem(1);
                setTabState(tvPair, ContextCompat.getColor(MainActivity.this, R.color.black));
                break;
        }
    }

    private void setTabState(TextView textView, int color) {
        textView.setTextColor(color);
    }

    private void resetTabState() {
        setTabState(tvAppoint, ContextCompat.getColor(this, R.color.gray));
        setTabState(tvPair, ContextCompat.getColor(this, R.color.gray));
    }

    private void initMenu(){
        initTabLayout();
        init();
    }

    private void init() {
        initAvatar();
        tvName.setText(User.getInstance().getName());

        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
        tvRedPacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RedPacketActivity.class));
            }
        });
        llModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ModifyActivity.class));
            }
        });
    }

    private void initAvatar(){
        Glide.with(MainActivity.this)
                .load(User.getInstance().getLogo())
                .apply(new RequestOptions().placeholder(R.mipmap.icon_avatar))
                .into(circleImageView);
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
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(Objects.requireNonNull(MainActivity.this).getSupportFragmentManager(), fragments, tabList);
        tabViewPager.setAdapter(viewPagerAdapter);
        tabViewPager.setSwipeDirection(SwipeDirection.none);
        //TabLayout加载viewpager
        tabLayout.setupWithViewPager(tabViewPager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime;

    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showToast(this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAvatar();
    }
}
