package com.match.app.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.match.app.message.table.Conversation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ConversationDao {

    private Dao<Conversation, Integer> dao;

    public ConversationDao(Context context) {
        try {
            BaseDBHelper baseDbHelper = BaseDBHelper.getHelper(context);
            dao = baseDbHelper.getDao(Conversation.class);
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
            dao.create(conversation);
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
