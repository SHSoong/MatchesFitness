package com.match.app.db;

import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.match.app.common.User;
import com.match.app.config.AppConstant;

@DatabaseTable(tableName = "tb_Message")
public class TbMessage {
    @DatabaseField(columnName = "_id", generatedId = true)
    private int id;

    @DatabaseField
    private String message;

    @DatabaseField
    private String fromId;

    @DatabaseField
    private String toId;

    @DatabaseField
    private int time;

    @DatabaseField
    private int state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static TbMessage jsonToObject(String json) {
        try {
            return new Gson().fromJson(json, TbMessage.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
