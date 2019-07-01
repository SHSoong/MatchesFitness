package com.match.app.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.match.app.message.table.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AccountDao {

    private Dao<Account, Integer> dao;

    public AccountDao(Context context) {
        try {
            BaseDBHelper baseDbHelper = BaseDBHelper.getHelper(context);
            dao = baseDbHelper.getDao(Account.class);
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
            dao.create(tbAccount);
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
