package com.match.app.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.match.app.message.table.Message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MessageDao {

    private Dao<Message, Integer> dao;

    public MessageDao(Context context) {
        try {
            BaseDBHelper baseDbHelper = BaseDBHelper.getHelper(context);
            dao = baseDbHelper.getDao(Message.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Message tbMessage) {
        try {
             dao.create(tbMessage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(Message message) {
        try {
            dao.delete(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Message> queryMessageByConversation(String conversation) {
        List<Message> messages = new ArrayList<>();
        try {
            List<Message> lst = getQueryBuiler().orderBy("time",true).where().eq("conversation_id", conversation).query();
            messages.addAll(lst);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public QueryBuilder getQueryBuiler() {
        return dao.queryBuilder();
    }
}
