package com.match.app.message.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述:
 * 搜索用户列表
 *
 * @author Dengfei
 * @create 2018-11-27 16:31
 */
public class B332Response extends BaseResponse {

    private long pageCount;			//分页时返回总页数

    private List<UserBean> beans = new ArrayList<>();

    public List<UserBean> getBeans() {
        return beans;
    }

    public void setBeans(List<UserBean> beans) {
        this.beans = beans;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }
    public static class UserBean{

        private String id;

        private String loginName;//登录名，同mobile(废弃)

        private String name;	// 昵称

        private String birthday; //  出生日期

        private Integer sex;		//性别 0:男 1:女

        private Integer hasExp;  //是否有经验1有， 0或者null其它无

        private String logo;		//头像

        private String lastLoginDate;// 更新时间，上次登录时间

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
}