package com.match.app.ui.im;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.matches.fitness.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageHolder> {
    private List<Message> lists;
    private Context context;

    public ChatAdapter(List<Message> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.itemview_message_hi, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.itemview_message_me, parent, false);
        }

        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        holder.tvMessage.setText(lists.get(position).getContent());
        Glide.with(context)
                .load("")
                .placeholder(R.mipmap.anim_avitor)
                .error(R.mipmap.anim_avitor)
                .dontAnimate()
                .into(holder.headerImg);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 1) {
            return 1;
        } else return 0;
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        private CircleImageView headerImg;
        private TextView tvMessage;

        public MessageHolder(View itemView) {
            super(itemView);
            headerImg = itemView.findViewById(R.id.cimg_header);
            tvMessage = itemView.findViewById(R.id.tv_message);
        }
    }
}
