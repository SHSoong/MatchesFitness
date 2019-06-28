package com.match.app.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.match.app.MyApp;

import java.sql.SQLException;
import java.util.List;

public class BaseDao<T> {

    private Dao<T, Integer> dao;

    public BaseDao(Class clazz) {
        try {
            BaseDBHelper baseDbHelper = BaseDBHelper.getHelper(MyApp.app);
            dao = baseDbHelper.getDao(clazz);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加一条数据
    public void insert(T t) {
        try {
            dao.create(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(T t) {
        try {
            dao.update(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除一条数据
    public void delete(T t) {
        try {
            dao.delete(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> queryByColumn(String columnName, Object columnValue) {
        try {
            QueryBuilder builder = dao.queryBuilder();
            builder.where().eq(columnName, columnValue);
            return builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> queryAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
