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
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.itemview_swipe_cards, null);
            initViewHolder(convertView, holder);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        B334Response.UserBean bean = list.get(position);

        holder.imageView.setImageResource(R.mipmap.img_avatar_01);
        holder.tvName.setText(bean.getName());
        holder.tvFitnessName.setText(bean.getFitnessName());
        holder.tvTime.setText(bean.getStartTime());
        holder.tvAge.setText(bean.getSex() + "· " + bean.getFitnessAddress());

        return convertView;
    }

    public void setData(List<B334Response.UserBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private void initViewHolder(View convertView, ViewHolder holder) {
        holder.imageView = convertView.findViewById(R.id.iv_avatar);
        holder.tvName = convertView.findViewById(R.id.tvName);
        holder.tvFitnessName = convertView.findViewById(R.id.tvFitnessName);
        holder.tvAge = convertView.findViewById(R.id.tvAge);
        holder.tvTime = convertView.findViewById(R.id.tvTime);
    }

    public class ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvFitnessName;
        TextView tvAge;
        TextView tvTime;
    }

}