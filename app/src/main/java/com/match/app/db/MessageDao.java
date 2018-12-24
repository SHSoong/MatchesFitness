package com.match.app.db;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MessageDao {

    private static final String TAG = MessageDao.class.getName();

    private Dao<TbMessage, Integer> dao;
    private DBHelper dbHelper;

    public MessageDao(Context context) {
        try {
            dbHelper = DBHelper.getHelper(context);
            dao = dbHelper.getDao(TbMessage.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(TbMessage tbMessage) {
        try {
            int count = dao.create(tbMessage);
            Log.d(TAG, "插入数据 " + count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(TbMessage message) {
        try {
            dao.delete(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TbMessage> queryMessageByConversation(String var1, String var2) {
        List<TbMessage> messages = new ArrayList<>();
        try {
            List<TbMessage> lst1 = getQueryBuiler().where().eq("fromId", var1).and().eq("toId", var2).query();

            List<TbMessage> lst2 = getQueryBuiler().where().eq("fromId", var2).and().eq("toId", var1).query();
            messages.addAll(lst1);
            messages.addAll(lst2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public QueryBuilder getQueryBuiler() {
        return dao.queryBuilder();
    }
}
