package com.match.app.message.bean;


/**
 * 重置密码
 * 
 * @author fly
 *
 */
public class B003Request extends BaseRequest {
	
	private String mobile = "";// 手机
	
	private String password = "";// 密码
	
	private String code = "";// 验证码

	public B003Request() {
		setActionCode("B003");
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
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
