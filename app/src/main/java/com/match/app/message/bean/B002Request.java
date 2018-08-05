package com.match.app.message.bean;

import com.match.app.message.bean.BaseRequest;

/**
 * 注册(B0开头不需要传token)
 * 
 * @author fly
 *
 */
public class B002Request extends BaseRequest {
	
	private String mobile = "";		// 手机
	
	private String password = "";	// 密码
	
	private String code = "";		// 验证码  测试期间8888不验证
	
	private String deviceType = "";	// android,ios

	public B002Request() {
		setActionCode("B002");
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
