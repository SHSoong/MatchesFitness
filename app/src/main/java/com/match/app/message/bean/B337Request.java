package com.match.app.message.bean;

/**
 * 描述:
 * 同意配对状态
 *
 * @author Dengfei
 * @create 2018-12-02 19:51
 */
public class B337Request extends BaseRequest {

    public B337Request() {
        setActionCode("B337");
    }

    private String matchApplyId;
    private int status;     // 11同意申请、21拒绝申请

    public String getMatchApplyId() {
        return matchApplyId;
    }

    public void setMatchApplyId(String matchApplyId) {
        this.matchApplyId = matchApplyId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}