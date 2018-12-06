package com.match.app.message.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 砍柴
 * @time: 2018/9/9 18:56
 * @see:
 */
public class B301Response extends BaseResponse {
    List<FitnessCenterBean> beans = new ArrayList<>();

    public List<FitnessCenterBean> getBeans() {
        return beans;
    }

    public void setBeans(List<FitnessCenterBean> beans) {
        this.beans = beans;
    }

    public static class FitnessCenterBean{

        private String centerId;

        private String centerName;

        private String address;

        private String contact;

        private String openingTime;

        public String getCenterId() {
            return centerId;
        }

        public void setCenterId(String centerId) {
            this.centerId = centerId;
        }

        public String getCenterName() {
            return centerName;
        }

        public void setCenterName(String centerName) {
            this.centerName = centerName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(String openingTime) {
            this.openingTime = openingTime;
        }
    }
}
