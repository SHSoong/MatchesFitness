package com.matches.fitness.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.matches.fitness.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {
    private List<Integer> list = new ArrayList<>();

    public MyAdapter(List<Integer> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageView avatarImageView = ((MyViewHolder) holder).avatarImageView;
        avatarImageView.setImageResource(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatarImageView;
        public ImageView likeImageView;
        public ImageView dislikeImageView;

        MyViewHolder(View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.iv_avatar);
            likeImageView = itemView.findViewById(R.id.iv_like);
            dislikeImageView = itemView.findViewById(R.id.iv_dislike);
        }

    }
}