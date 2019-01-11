package com.match.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.match.app.message.bean.B334Response;
import com.matches.fitness.R;

import java.util.List;

public class SwipeStackAdapter extends BaseAdapter {

    private Context context;
    private List<B334Response.UserBean> list;

    public SwipeStackAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.itemview_swipe_cards, null);
        ImageView imageView = convertView.findViewById(R.id.iv_avatar);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvAge = convertView.findViewById(R.id.tvAge);
        TextView tvTime = convertView.findViewById(R.id.tvTime);

        B334Response.UserBean bean = list.get(position);

        imageView.setImageResource(R.mipmap.img_avatar_01);
        tvName.setText(bean.getName());
        tvAge.setText(bean.getBirthday()+"* ");
//        tvTime.setText(bean.getId()+"");
        return convertView;
    }

    public void setData(List<B334Response.UserBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
}