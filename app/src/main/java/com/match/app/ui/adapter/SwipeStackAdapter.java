package com.match.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.matches.fitness.R;

import java.util.List;

public class SwipeStackAdapter extends BaseAdapter {

    private List<Integer> mData;
    Context context;

    public SwipeStackAdapter(Context context, List<Integer> data) {
        this.mData = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Integer getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.itemview_swipe_cards, null);
        ImageView imageView = convertView.findViewById(R.id.iv_avatar);
        imageView.setImageResource(mData.get(position));
        return convertView;
    }
}