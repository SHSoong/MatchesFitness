package com.match.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.match.app.message.table.Account;
import com.match.app.message.table.Conversation;
import com.match.app.message.table.Message;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class BaseDBHelper extends OrmLiteSqliteOpenHelper {

    private static String DB_NAME = "fitness.db";

    private static final int DB_VERSION = 1;

    private Map<String, Dao> daoCache = new HashMap<>();

    private static BaseDBHelper instance;

    public static synchronized BaseDBHelper getHelper(Context context) {
        if (instance == null) {
            synchronized (BaseDBHelper.class) {
                if (instance == null) {
                    instance = new BaseDBHelper(context);
                }
            }
        }
        return instance;
    }

    private BaseDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao d;
        String className = clazz.getSimpleName();
        if (daoCache.containsKey(className)) {
            return daoCache.get(className);
        } else {
            TableUtils.createTableIfNotExists(getConnectionSource(), clazz);
            d = super.getDao(clazz);
            daoCache.put(className, d);
        }
        return d;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        createTables(connectionSource);//创建表,第一次创建表格的时候调用
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldV, int newV) {
    }

    private void createTables(ConnectionSource connection) {
        Set<Class<?>> set = new HashSet<>(Arrays.asList(getCreateTables()));
        for (Class<?> clazz : set) {
            try {
                TableUtils.createTableIfNotExists(connection, clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public <T> void clearTable(Class<T> clazz) {
        String clazzName = clazz.getSimpleName();
        if (daoCache.containsKey(clazzName)) {
            try {
                TableUtils.clearTable(getConnectionSource(), clazz);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Class<?>[] getCreateTables() {
        return new Class<?>[]{
                Account.class,
                Message.class,
                Conversation.class
        };
    }
}
