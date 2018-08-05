package com.match.app.message.bean;


/**
 * 登录请求(B0开头不需要传token)
 *
 * @author fei
 */
public class B001Request extends BaseRequest {

    private String loginName = "";    //登录名
    private String password = "";    //密码
    private String deviceType = "";    //android,ios

    public B001Request() {
        setActionCode("B001");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

}
