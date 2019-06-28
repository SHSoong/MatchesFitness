package com.match.app.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.match.app.message.table.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AccountDao {

    private Dao<User, Integer> dao;

    public AccountDao(Context context) {
        try {
            BaseDBHelper baseDbHelper = BaseDBHelper.getHelper(context);
            dao = baseDbHelper.getDao(User.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(User tbUser) {
        try {
            List<User> users = queryAll();
            for (User user : users) {
                if (User.equals(tbUser, user)) {
                    update(tbUser);
                    return;
                }
            }
            dao.create(tbUser);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        try {
            dao.update(user);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(User user){
        try {
            dao.delete(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User queryByAccount(String var1) {
        User user = null;
        try {
            List<User> lists = getQueryBuiler().where().eq("user", var1).query();
            if (lists != null && !lists.isEmpty()) {
                user = lists.get(lists.size() - 1);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> queryAll() {
        List<User> users = new ArrayList<>();
        try {
            users = dao.queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public QueryBuilder getQueryBuiler() {
        return dao.queryBuilder();
    }
}
