package com.match.app.message.bean;

/**
 * 描述:
 * 申请配对健身
 *
 * @author Dengfei
 * @create 2018-12-02 19:51
 */
public class B335Request extends BaseRequest {

    public B335Request() {
        setActionCode("B335");
    }

    /**
     * 被申请方
     */
    private String userIdb;

    /**
     * 对应预约健身记录
     */
    private String matchProfileId;

    public String getUserIdb() {
        return userIdb;
    }

    public void setUserIdb(String userIdb) {
        this.userIdb = userIdb;
    }

    public String getMatchProfileId() {
        return matchProfileId;
    }

    public void setMatchProfileId(String matchProfileId) {
        this.matchProfileId = matchProfileId;
    }
}