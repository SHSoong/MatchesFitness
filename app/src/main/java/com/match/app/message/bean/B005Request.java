package com.match.app.message.bean;

import java.util.Date;

/**
 * 更新用户信息
 * @author fei
 *
 */
public class B005Request extends BaseRequest {

	private String name;	// 昵称

	private String birthday; //  出生日期

	private Integer sex;		//性别 0:男 1:女

	private Integer hasExp;  //是否有经验1有， 0或者null其它无

	private String logo;		//头像

	public B005Request() {
		setActionCode("B005");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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
}
