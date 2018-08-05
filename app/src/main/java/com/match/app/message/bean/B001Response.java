package com.match.app.message.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class B001Response extends BaseResponse {

    @SerializedName("token")
    private String token = "";//user id
    @SerializedName("loginName")
    private String loginName;//登录名，同mobile(废弃)
    @SerializedName("name")
    private String name;    // 昵称
    @SerializedName("birthday")
    private String birthday; //  出生日期
    @SerializedName("sex")
    private Integer sex;        //性别 0:男 1:女
    @SerializedName("hasExp")
    private Integer hasExp;  //是否有经验1有， 0或者null其它无
    @SerializedName("logo")
    private String logo;        //头像
    @SerializedName("lastLoginDate")
    private String lastLoginDate;// 更新时间，上次登录时间

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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
}
