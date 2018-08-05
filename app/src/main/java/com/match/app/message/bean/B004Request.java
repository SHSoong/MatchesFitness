package com.match.app.message.bean;

import java.util.Date;


/**
 * 第三方登陆
 * 
 * @author fly
 *
 */
public class B004Request extends BaseRequest {

	private String openid;

	private String unionid;
	
	private String name;

	private String logo;
	
	private Date birthday;

	private Integer sex;

	public B004Request() {
		setActionCode("B004");
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}	
}
