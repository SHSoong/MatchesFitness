package com.match.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.match.app.message.bean.B301Response.FitnessCenterBean;
import com.matches.fitness.R;

import java.util.List;

public class SwipeStackAdapter extends BaseAdapter {

    private Context context;
    private List<FitnessCenterBean> list;

    public SwipeStackAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.itemview_swipe_cards, null);
        ImageView imageView = convertView.findViewById(R.id.iv_avatar);
        imageView.setImageResource(R.mipmap.img_avatar_01);
        return convertView;
    }

    public void setData(List<FitnessCenterBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
}