package com.match.app.db;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.List;


public class AccountDao {

    private static final String TAG = AccountDao.class.getName();

    private Dao<TbAccount, Integer> dao;
    private DBHelper dbHelper;

    public AccountDao(Context context) {
        try {
            dbHelper = DBHelper.getHelper(context);
            dao = dbHelper.getDao(TbAccount.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(TbAccount tbAccount) {
        try {
            List<TbAccount> accounts = queryAll();
            for (TbAccount account : accounts) {
                if (TbAccount.equals(tbAccount, account)) {
                    update(tbAccount);
                    return;
                }
            }

            int count = dao.create(tbAccount);
            Log.d(TAG, "插入数据 " + count);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(TbAccount account) {
        try {
            dao.update(account);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public TbAccount queryByAccount(String var1) {
        TbAccount account = null;
        try {
            List<TbAccount> lists = getQueryBuiler().where().eq("account", var1).query();
            if (lists != null && !lists.isEmpty()) {
                account = lists.get(lists.size() - 1);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public List<TbAccount> queryAll() {
        List<TbAccount> accounts = new ArrayList<>();
        try {
            accounts = dao.queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public QueryBuilder getQueryBuiler() {
        return dao.queryBuilder();
    }
}
