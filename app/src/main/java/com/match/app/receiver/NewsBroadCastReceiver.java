package com.match.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.match.app.config.AppConstant;
import com.match.app.db.BaseDao;
import com.match.app.message.table.Conversation;
import com.match.app.message.table.Message;
import com.match.app.utils.ToastUtils;

import java.util.List;

public class NewsBroadCastReceiver extends BroadcastReceiver {

    private BaseDao messageDao;
    private BaseDao conversationDao;

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra(AppConstant.KEY_MESSAGE);
        saveMessage(context, msg);
    }

    private void saveMessage(Context context, String msg) {
        Message message = Message.jsonToObject(msg);
        messageDao = new BaseDao(Message.class);
        messageDao.insert(message);

        if (message != null) {
            conversationDao = new BaseDao(Conversation.class);
            List<Conversation> conversations = conversationDao.queryByColumn("his_token", message.getConversationToken());

            Conversation conversation = new Conversation();
            conversation.setHisToken(message.getSendToken());
            conversation.setLastTime(message.getTime());
            conversation.setHisName(message.getSpeakerName());
            conversation.setLastMessage(message.getContent());
            conversation.setHisLogoUrl(message.getHisLogoUrl());

            if (conversations == null || conversations.size() <= 0) {
                conversationDao.insert(conversation);
            } else {
                conversationDao.update(conversation);
            }
        }

    }

}
