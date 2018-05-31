package com.matches.fitness.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.huxq17.swipecardsview.SwipeCardsView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.matches.fitness.R;
import com.matches.fitness.ui.adapter.MeiziAdapter;
import com.matches.fitness.ui.fragment.MenuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends SlidingFragmentActivity {

    @BindView(R.id.rlMenu)
    RelativeLayout rlMenu;
    @BindView(R.id.swipeCardsView)
    SwipeCardsView swipeCardsView;

    private List<Integer> list = new ArrayList<>();
    private MeiziAdapter adapter;
    private int curIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSlidingMenu();
        initView();
        initData();
        initSwipeCards();
        showSwipeCards();
    }

    private void initSlidingMenu() {
        ButterKnife.bind(this);

        setBehindContentView(R.layout.activity_menu);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, new MenuFragment()).commit();
        // 设置滑动菜单的属性值
        getSlidingMenu().setMode(SlidingMenu.LEFT); //设定模式，SlidingMenu在左边
        getSlidingMenu().setBehindOffsetRes(R.dimen.sliding_menu_offset); //配置slidingmenu偏移出来的尺寸
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN); //全屏都可以拖拽触摸，打开slidingmenu
        getSlidingMenu().setFadeDegree(0.35F);// SlidingMenu滑动时的渐变程度
        getSlidingMenu().addIgnoredView(swipeCardsView);
    }

    private void initView() {
        rlMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSlidingMenu().toggle();
            }
        });
    }

    private void initData() {
        list.add(R.mipmap.img_avatar_01);
        list.add(R.mipmap.img_avatar_02);
        list.add(R.mipmap.img_avatar_03);
        list.add(R.mipmap.img_avatar_04);
        list.add(R.mipmap.img_avatar_05);
        list.add(R.mipmap.img_avatar_06);
        list.add(R.mipmap.img_avatar_07);
    }

    private void initSwipeCards() {
        swipeCardsView.retainLastCard(true);
        swipeCardsView.enableSwipe(true);
        swipeCardsView.getTouchscreenBlocksFocus();
        //设置滑动监听
        swipeCardsView.setCardsSlideListener(new SwipeCardsView.CardsSlideListener() {

            @Override
            public void onShow(int index) {
                curIndex = index;
            }

            @Override
            public void onCardVanish(int index, SwipeCardsView.SlideType type) {
                switch (type) {
                    case LEFT:
                        break;
                    case RIGHT:
                        break;
                }
            }

            @Override
            public void onItemClick(View cardImageView, int index) {
            }
        });

    }

    /**
     * 点击：卡片向左边飞出
     */
    public void doLeftOut() {
        swipeCardsView.slideCardOut(SwipeCardsView.SlideType.LEFT);
    }

    /**
     * 点击：卡片向右边飞出
     */
    public void doRightOut() {
        swipeCardsView.slideCardOut(SwipeCardsView.SlideType.RIGHT);
    }

    /**
     * 显示cardsview
     */
    private void showSwipeCards() {
        if (adapter == null) {
            adapter = new MeiziAdapter(MainActivity.this, list);
            swipeCardsView.setAdapter(adapter);
        } else {
            adapter.setData(list);
            swipeCardsView.notifyDatasetChanged(curIndex);
        }
    }

}
