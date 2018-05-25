package com.matches.fitness.ui;

import android.view.View;
import android.widget.Toast;

import com.huxq17.swipecardsview.SwipeCardsView;
import com.matches.fitness.R;
import com.matches.fitness.base.BaseActivity;
import com.matches.fitness.ui.adapter.MeiziAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private List<Integer> list = new ArrayList<>();
    private SwipeCardsView swipeCardsView;
    private MeiziAdapter adapter;
    private int curIndex;

    @Override
    protected void onInitBinding() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onInit() {
        ButterKnife.bind(this);
        initData();
        initView();
        show();
    }

    private void initView() {

        swipeCardsView = findViewById(R.id.swipCardsView);
        swipeCardsView.retainLastCard(true);
        swipeCardsView.enableSwipe(true);
        //设置滑动监听
        swipeCardsView.setCardsSlideListener(new SwipeCardsView.CardsSlideListener() {
            @Override
            public void onShow(int index) {
                curIndex = index;
            }

            @Override
            public void onCardVanish(int index, SwipeCardsView.SlideType type) {
                String orientation = "";
                switch (type) {
                    case LEFT:
                        orientation = "向左飞出";
                        break;
                    case RIGHT:
                        orientation = "向右飞出";
                        break;
                }
                Toast.makeText(MainActivity.this, "test position = " + index + ";卡片" + orientation, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemClick(View cardImageView, int index) {
                Toast.makeText(MainActivity.this, "点击了 position=" + index, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 显示cardsview
     */
    private void show() {
        if (adapter == null) {
            adapter = new MeiziAdapter( MainActivity.this,list);
            swipeCardsView.setAdapter(adapter);
        } else {
            //if you want to change the UI of SwipeCardsView,you must modify the data first
            adapter.setData(list);
            swipeCardsView.notifyDatasetChanged(curIndex);
        }
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

    private void initData() {
        list.add(R.mipmap.img_avatar_01);
        list.add(R.mipmap.img_avatar_02);
        list.add(R.mipmap.img_avatar_03);
        list.add(R.mipmap.img_avatar_04);
        list.add(R.mipmap.img_avatar_05);
        list.add(R.mipmap.img_avatar_06);
        list.add(R.mipmap.img_avatar_07);


    }

}
