package com.match.app.db;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.match.app.message.table.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AccountDao {

    private static final String TAG = AccountDao.class.getName();

    private Dao<Account, Integer> dao;
    private DBHelper dbHelper;

    public AccountDao(Context context) {
        try {
            dbHelper = DBHelper.getHelper(context);
            dao = dbHelper.getDao(Account.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Account tbAccount) {
        try {
            List<Account> accounts = queryAll();
            for (Account account : accounts) {
                if (Account.equals(tbAccount, account)) {
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

    public void update(Account account) {
        try {
            dao.update(account);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Account account){
        try {
            dao.delete(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account queryByAccount(String var1) {
        Account account = null;
        try {
            List<Account> lists = getQueryBuiler().where().eq("account", var1).query();
            if (lists != null && !lists.isEmpty()) {
                account = lists.get(lists.size() - 1);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public List<Account> queryAll() {
        List<Account> accounts = new ArrayList<>();
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
