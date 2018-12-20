package com.match.app.message.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 根据配对信息获取系统推荐的用户
 *
 * @author Dengfei
 * @create 2018-11-27 14:12
 */
public class B336Response extends BaseResponse {

    private List<MatchApplyBean> beans = new ArrayList<>();

    public List<MatchApplyBean> getBeans() {
        return beans;
    }

    public void setBeans(List<MatchApplyBean> beans) {
        this.beans = beans;
    }

    public static class MatchApplyBean{

        private String id;

        private String userId;  //申请人

        private String name;	// 申请人名称

        private String sex;	// 申请人名称

        private String logo;	// 名称

        private String createdTime; //  创建时间

        private String address;

        private String matchTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMatchTime() {
            return matchTime;
        }

        public void setMatchTime(String matchTime) {
            this.matchTime = matchTime;
        }
    }
}