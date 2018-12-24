package com.match.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.match.app.config.AppConstant;
import com.match.app.db.MessageDao;
import com.match.app.db.TbMessage;

import java.util.ArrayList;
import java.util.List;

public class NewsBroadCastReceiver extends BroadcastReceiver {

    private MessageDao dao;

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

        for (MessageListener listener:listeners){
            listener.notice();
        }
    }

    private void saveMessage(Context context, String msg) {
        if (dao == null) {
            dao = new MessageDao(context);
        }
        TbMessage message = TbMessage.jsonToObject(msg);
        dao.add(message);
    }

    public interface MessageListener {
        void notice();
    }


}
