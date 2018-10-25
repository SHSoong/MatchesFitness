package com.match.app.ui.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.matches.fitness.R;

import java.util.List;

public class RecordListAdapter extends BaseAdapter {
    private Context context;
    private List<Person> lists;
    private boolean isBig =false;
    public RecordListAdapter(Context context, List<Person> lists) {
        this.context = context;
        this.lists = lists;
    }
    public RecordListAdapter(Context context, List<Person> lists,boolean isBig) {
        this.context = context;
        this.lists = lists;
        this.isBig = isBig;
    }


    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            if(isBig){
                convertView = LayoutInflater.from(context).inflate(R.layout.itemview_person1, null);
            }else{
                convertView = LayoutInflater.from(context).inflate(R.layout.itemview_person, null);
            }
            holder.mImageView = convertView.findViewById(R.id.img_icon);
            holder.mTextView = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Person person = lists.get(position);
        Glide.with(context)
                .load(person.getLogUrl())
                .placeholder(R.mipmap.anim_avitor)
                .error(R.mipmap.anim_avitor)
                .dontAnimate()
                .into(holder.mImageView);
        holder.mTextView.setText(person.getName());
        return convertView;
    }

    private class Holder {
        ImageView mImageView;
        TextView mTextView;
    }
}
