package com.match.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.match.app.message.table.Message;
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

    public void setData(List<Message> lists) {
        this.lists = lists;
        notifyDataSetChanged();
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
        Message message = lists.get(position);
        holder.tvMessage.setText(message.getContent());
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.icon_avatar);
        Glide.with(context)
                .load(message.getHisLogoUrl())
                .apply(options)
                .into(holder.headerImg);
        switch (message.getStatus()) { // 状态  0 发送中，1发送成功，2 发送失败 ，3 未读，4 已读 -1 删除
            case 0:
                holder.imgState.setVisibility(View.VISIBLE);
                holder.imgStatus.setVisibility(View.GONE);
                break;
            case 2:
                holder.imgStatus.setVisibility(View.VISIBLE);
                holder.imgState.setVisibility(View.GONE);
                holder.imgStatus.setImageResource(R.mipmap.message_up_error);
                break;
            case 1:
            case 3:
            case 4:
                holder.imgState.setVisibility(View.GONE);
                holder.imgStatus.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = lists.get(position);
        if (message.getConversationId().equals(message.getReceiver())) {
            return 1;
        }
        if (message.getConversationId().equals(message.getSpeaker())) {
            return 0;
        }
        return 0;
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        private CircleImageView headerImg;
        private TextView tvMessage;
        private ImageView imgStatus;
        private ProgressBar imgState;

        public MessageHolder(View itemView) {
            super(itemView);
            headerImg = itemView.findViewById(R.id.cimg_header);
            tvMessage = itemView.findViewById(R.id.tv_message);
            imgStatus = itemView.findViewById(R.id.img_status);
            imgState = itemView.findViewById(R.id.img_state);
        }
    }
}
