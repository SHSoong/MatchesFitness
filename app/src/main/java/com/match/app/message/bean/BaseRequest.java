package com.match.app.message.bean;

/**
 * APP接口的实体Bean的请求端的父类
 */
public class BaseRequest {
	
	/**接口编码*/
	private String actionCode = "";
	
	/**id， 对应旧版本数据库的token字段*/
	private String token = "";
	
	/**md5签名串*/
	private String encryptCode = "android";
	
	/**请求时间*/
	private long time;	
	
	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEncryptCode() {
		return encryptCode;
	}

	public void setEncryptCode(String encryptCode) {
		this.encryptCode = encryptCode;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	
}
