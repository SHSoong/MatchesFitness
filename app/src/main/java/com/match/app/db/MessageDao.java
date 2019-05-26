package com.match.app.db;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.match.app.message.table.Message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MessageDao {

    private static final String TAG = MessageDao.class.getName();

    private Dao<Message, Integer> dao;
    private DBHelper dbHelper;

    public MessageDao(Context context) {
        try {
            dbHelper = DBHelper.getHelper(context);
            dao = dbHelper.getDao(Message.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Message tbMessage) {
        try {
            int count = dao.create(tbMessage);
            Log.d(TAG, "插入数据 " + count);
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
