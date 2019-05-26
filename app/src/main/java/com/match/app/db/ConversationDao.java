package com.match.app.db;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.match.app.message.table.Conversation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ConversationDao {

    private static final String TAG = ConversationDao.class.getName();

    private Dao<Conversation, Integer> dao;
    private DBHelper dbHelper;

    public ConversationDao(Context context) {
        try {
            dbHelper = DBHelper.getHelper(context);
            dao = dbHelper.getDao(Conversation.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Conversation conversation) {
        try {
            List<Conversation> conversations = queryAll();
            for (Conversation con : conversations) {
                if (Conversation.equals(conversation, con)) {
                    update(conversation);
                    return;
                }
            }

            int count = dao.create(conversation);
            Log.d(TAG, "插入数据 " + count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Conversation conversation) {
        try {
            dao.update(conversation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Conversation conversation){
        try {
            dao.delete(conversation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Conversation> queryAll() {
        List<Conversation> conversations = new ArrayList<>();
        try {
            conversations = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conversations;
    }

    public QueryBuilder getQueryBuiler() {
        return dao.queryBuilder();
    }
}
