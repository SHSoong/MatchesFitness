package com.match.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class DBHelper extends OrmLiteSqliteOpenHelper {

    /**
     * 数据库名字
     */
    private static String DB_NAME = "fitness.db";

    /**
     * 数据库版本
     */
    private static final int DB_VERSION = 1;

    /**
     * 用来存放Dao的Map集合，之所以创建Map集合，是为了方便对数据库资源的管理
     */
    private Map<String, Dao> daos = new HashMap<>();

    /**
     * 数据库连接对象
     */
    private AndroidConnectionSource connectionSource;

    /**
     * 单例对象
     */
    private static DBHelper instance;

    /**
     * 构造方法
     */
    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 使用单例模式获取数据库对象
     */
    public static synchronized DBHelper getHelper(Context context) {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(context);
                }
            }
        }
        return instance;
    }

    public synchronized Dao getDao(Class clz) throws SQLException {
        Dao dao = null;
        String className = clz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TbAccount.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldV, int newV) {
    }

    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
