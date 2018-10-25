package com.match.app.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.huxq17.swipecardsview.BaseCardAdapter;
import com.matches.fitness.R;

import java.util.List;


public class CardViewsAdapter extends BaseCardAdapter {

    private List<Integer> datas;
    private Context context;

    public CardViewsAdapter(Context context, List<Integer> datas) {
        this.datas = datas;
        this.context = context;
    }

    public void setData(List<Integer> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.itemview_swipe_cards;
    }

    @Override
    public void onBindData(int position, View cardview) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        ImageView imageView = cardview.findViewById(R.id.iv_avatar);
        imageView.setImageResource(datas.get(position));
    }

    /**
     * 如果可见的卡片数是3，则可以不用实现这个方法
     *
     * @return s
     */
    @Override
    public int getVisibleCardCount() {
        return 3;
    }
}