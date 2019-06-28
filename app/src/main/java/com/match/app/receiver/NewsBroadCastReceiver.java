package com.match.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.match.app.config.AppConstant;
import com.match.app.db.ConversationDao;
import com.match.app.db.MessageDao;
import com.match.app.message.table.Conversation;
import com.match.app.message.table.Message;

import java.util.ArrayList;
import java.util.List;

public class NewsBroadCastReceiver extends BroadcastReceiver {

    private MessageDao dao;
    private ConversationDao conversationDao;

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
        if (dao == null) {
            dao = new MessageDao(context);
        }
        if (conversationDao == null) {
            conversationDao = new ConversationDao(context);
        }
        Message message = Message.jsonToObject(msg);
        message.setConversationId(message.getConversationId());
        dao.add(message);
        Conversation conversation = new Conversation();
        conversation.setConversationId(message.getConversationId());
        conversation.setHisLogoUrl(message.getHisLogoUrl());
        conversation.setHisName(message.getSpeakerName());
        conversation.setLastMessage(message.getContent());
        conversation.setLastTime(message.getTime());
        conversation.setStatus(0);
        conversationDao.add(conversation);
    }

    public interface MessageListener {
        void notice();
    }


}
