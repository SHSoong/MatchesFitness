package com.match.app.message.table;

import android.text.TextUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_user")
public class User {

    @DatabaseField(columnName = "user_id", generatedId = true)
    private int userId;
    @DatabaseField
    private String account;
    @DatabaseField
    private String password;
    @DatabaseField
    private String token;
    @DatabaseField
    private String name;    // 昵称
    @DatabaseField
    private String birthday; //  出生日期
    @DatabaseField
    private Integer sex;        //性别 0:男 1:女
    @DatabaseField(columnName = "has_exp")
    private Integer hasExp;  //是否有经验1有， 0或者null其它无
    @DatabaseField
    private String logo;        //头像
    @DatabaseField(columnName = "last_login_date")
    private String lastLoginDate;// 更新时间，上次登录时间

    public static boolean equals(User var1, User var2) {
        if (var1 != null && var2 != null) {
            if (!TextUtils.isEmpty(var2.getAccount())) {
                if (var1.getAccount().equals(var2.getAccount())) {
                    var1.setUserId(var2.getUserId());
                    return true;
                }
            }
        }
        return false;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getHasExp() {
        return hasExp;
    }

    public void setHasExp(Integer hasExp) {
        this.hasExp = hasExp;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public User() {
    }

    public User(String account, String password, String token, String name, String birthday, Integer sex, Integer hasExp, String logo, String lastLoginDate) {
        this.account = account;
        this.password = password;
        this.token = token;
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.hasExp = hasExp;
        this.logo = logo;
        this.lastLoginDate = lastLoginDate;
    }
}
