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
public class B334Response extends BaseResponse {

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

        private String matchProfileId;  //匹配的预约记录

        private String name;	// 昵称

        private Integer sex;		//性别 0:男 1:女

        private Integer hasExp;  //是否有经验1有， 0或者null其它无

        private String logo;		//头像

        private String startTime;   //开始时间

        private String endTime;   //开始时间

        private String fitnessName;        //健身房名称

        private String fitnessAddress;     //健身房地址

        private String birthday;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getMatchProfileId() {
            return matchProfileId;
        }

        public void setMatchProfileId(String matchProfileId) {
            this.matchProfileId = matchProfileId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getFitnessName() {
            return fitnessName;
        }

        public void setFitnessName(String fitnessName) {
            this.fitnessName = fitnessName;
        }

        public String getFitnessAddress() {
            return fitnessAddress;
        }

        public void setFitnessAddress(String fitnessAddress) {
            this.fitnessAddress = fitnessAddress;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }
}
