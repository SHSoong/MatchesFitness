package com.match.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.match.app.config.AppConstant;
import com.match.app.db.BaseDao;
import com.match.app.message.table.Conversation;
import com.match.app.message.table.Message;

import java.util.ArrayList;
import java.util.List;

public class NewsBroadCastReceiver extends BroadcastReceiver {

    private BaseDao messageDao;
    private BaseDao conversationDao;
    private static List<MessageListener> listeners;

    public static void register(MessageListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public static void unRegister(MessageListener listener) {
        if (listeners == null) {
            return;
        }
        listeners.remove(listener);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra(AppConstant.KEY_MESSAGE);
        saveMessage(context, msg);

        if (listeners != null && !listeners.isEmpty()) {
            for (MessageListener listener : listeners) {
                listener.notice();
            }
        }
    }

    private void saveMessage(Context context, String msg) {
        Message message = Message.jsonToObject(msg);
        List<Message> list = messageDao.queryByColumn("message_id", message.getMessageId());

        if (list == null || list.size() <= 0) {
            messageDao.insert(Message.class);
            conversationDao.insert(Conversation.class);
        }
        messageDao.update(message);

        Conversation conversation = new Conversation();
        conversation.setToken(message.getSendToken());
        conversation.setLastTime(message.getTime());
        conversation.setHisName(message.getSpeakerName());
        conversation.setLastMessage(message.getContent());
        conversation.setHisLogoUrl(message.getHisLogoUrl());
        conversationDao.update(conversation);
    }

    public interface MessageListener {
        void notice();
    }


}
