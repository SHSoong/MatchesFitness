package com.matches.fitness.ui.im;

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
import java.util.concurrent.CopyOnWriteArrayList;

public class ContactListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Contact> mLists;

    public ContactListAdapter(Context context, List<Contact> lists) {
        this.mContext = context;
        this.mLists = lists;
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.itemview_contact, null);
            holder.imgHeader = view.findViewById(R.id.img_header);
            holder.tvWord = view.findViewById(R.id.tv_word);
            holder.tvName = view.findViewById(R.id.tv_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Contact contact = mLists.get(i);
        String word = contact.getHeaderWord();
        holder.tvWord.setText(word);
        holder.tvName.setText(contact.getName());
        Glide.with(mContext)
                .load(contact.getLogUrl())
                .placeholder(R.mipmap.anim_avitor)
                .error(R.mipmap.anim_avitor)
                .dontAnimate()
                .into(holder.imgHeader);
        if (i == 0) {
            holder.tvWord.setVisibility(View.VISIBLE);
        } else {
            String headerWord = mLists.get(i - 1).getHeaderWord();
            if (contact.getHeaderWord().equals(headerWord)) {
                holder.tvWord.setVisibility(View.GONE);
            } else {
                holder.tvWord.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    private class ViewHolder {
        private TextView tvWord;
        private ImageView imgHeader;
        private TextView tvName;
    }
}
