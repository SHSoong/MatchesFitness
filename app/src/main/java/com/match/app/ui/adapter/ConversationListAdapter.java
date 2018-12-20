package com.match.app.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.match.app.message.entity.Conversation;
import com.match.app.message.entity.Message;
import com.matches.fitness.R;
import com.match.app.utils.DateUtils;

import java.util.List;

public class ConversationListAdapter extends BaseAdapter {
    private List<Conversation> lists;
    private Context mContext;

    public ConversationListAdapter(Context context, List<Conversation> lists) {
        this.mContext = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

//    public

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.itemview_conversation, null);

            holder.mImageView = convertView.findViewById(R.id.img_icon);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvLastMsg = convertView.findViewById(R.id.tv_lastmsg);
            holder.tvTime = convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Conversation conversation = lists.get(position);
        Glide.with(mContext)
                .load(conversation.getHisLogoUrl())
                .placeholder(R.mipmap.anim_avitor)
                .error(R.mipmap.anim_avitor)
                .dontAnimate()
                .into(holder.mImageView);
        setText(holder.tvName, conversation.getHisName());
        /****
         * int id, int conversation, String speaker, String content, int time, int status
         */
        Message message = new Message(conversation.getId(), conversation.getId(), conversation.getHim(), conversation.getHisName(),
                "你好啊", (int) (System.currentTimeMillis() / 1000), 0); // 此处由数据库中获取
        setText(holder.tvLastMsg, message.getContent());
        setText(holder.tvTime, timeInterval(message.getTime()));
//        ((LinearLayout) (holder.mImageView.getParent())).setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                new AlertDialog.Builder(mContext)
//                        .setMessage("是否删除该对话")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                lists.remove(position);
//                                notifyDataSetChanged();
//                            }
//                        }).setNegativeButton("取消", null).create().show();
//                return false;
//            }
//        });
        return convertView;
    }

    private void setText(TextView textView, String text) {
        if (textView != null && !TextUtils.isEmpty(text)) {
            textView.setText(text);
        }
    }

    private String timeInterval(int time) {
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        long timeInterval = (currentTime - time) / 60;
        if (timeInterval < 1) {
            return "刚刚";
        } else if (timeInterval < 60) {
            return time + "分钟之前";
        } else {
            return DateUtils.longToDate(time * 1000);
        }

    }

    private class Holder {
        ImageView mImageView;
        TextView tvName;
        TextView tvLastMsg;
        TextView tvTime;
    }
}
