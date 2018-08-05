package com.match.app.message.bean;

import com.google.gson.annotations.SerializedName;

/**
 * APP接口的实体Bean的响应端的父类
 */
public class BaseResponse {

	@SerializedName("success")
	private boolean success = false;// 是否成功
	@SerializedName("message")
	private String message = ""; // 返回信息

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
