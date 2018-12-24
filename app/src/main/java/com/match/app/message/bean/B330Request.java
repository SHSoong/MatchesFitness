package com.match.app.message.bean;


/**
 * 描述:
 * 生成健身信息
 *
 * @author Dengfei
 * @create 2018-11-17 14:12
 */
public class B330Request extends BaseRequest {

    public B330Request() {
        setActionCode("B330");
    }

    private String fitnessCenterId; // 健身房

    private String startTime;       // 开始时间,  yyyy-mm-dd hh:mm:ss, 一共是13个区间 从7点开始每1.5小时为一个时间段 共13个时间段 7-8.5开始  8-9.5结束

    private String endTime;         // 结束时间,  yyyy-mm-dd hh:mm:ss

    private int sex;             // 性别

    private int exp;             // 经验

    public String getFitnessCenterId() {
        return fitnessCenterId;
    }

    public void setFitnessCenterId(String fitnessCenterId) {
        this.fitnessCenterId = fitnessCenterId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
