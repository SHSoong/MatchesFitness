package com.match.app.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.match.app.message.table.Message;
import com.match.app.ui.im.ChatActivity;
import com.matches.fitness.R;

import java.util.List;

public class ChatsAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {

    private static final int TYPE_SEND_TEXT = 1;
    private static final int TYPE_RECEIVE_TEXT = 2;

    private static final int SEND_TEXT = R.layout.item_text_send;
    private static final int RECEIVE_TEXT = R.layout.item_text_receive;
    /*
    private static final int SEND_LOCATION = R.layout.item_location_send;
    private static final int RECEIVE_LOCATION = R.layout.item_location_receive;*/


    public ChatsAdapter(Context context, List<Message> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<Message>() {
            @Override
            protected int getItemType(Message entity) {
                boolean isSend = entity.getSendToken().equals(ChatActivity.mSenderId);
                return isSend ? TYPE_SEND_TEXT : TYPE_RECEIVE_TEXT;
            }
        });
        getMultiTypeDelegate().registerItemType(TYPE_SEND_TEXT, SEND_TEXT)
                .registerItemType(TYPE_RECEIVE_TEXT, RECEIVE_TEXT);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        setContent(helper, item);
        setStatus(helper, item);
    }


    private void setStatus(BaseViewHolder helper, Message item) {
        //只需要设置自己发送的状态
        int sentStatus = item.getStatus();
        boolean isSend = item.getSendToken().equals(ChatActivity.mSenderId);
        if (isSend) {
            if (sentStatus == 0) {
                helper.setVisible(R.id.chat_item_progress, true).setVisible(R.id.chat_item_fail, false);
            } else if (sentStatus == 2) {
                helper.setVisible(R.id.chat_item_progress, false).setVisible(R.id.chat_item_fail, true);
            } else if (sentStatus == 1) {
                helper.setVisible(R.id.chat_item_progress, false).setVisible(R.id.chat_item_fail, false);
            }
        }
    }

    private void setContent(BaseViewHolder helper, Message item) {
        helper.setText(R.id.chat_item_content_text, item.getContent());
    }

}
