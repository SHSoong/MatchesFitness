package com.match.app.message.bean;

/**
 * 更新用户信息
 * 
 * @author fly
 *
 */
public class B200Request extends BaseRequest {

	private String name;//名称
	
	private String logo;// 头像(文件路径名称or二进制?)
	
	private Integer sex;//性别 0:男 1:女

	private Integer hasExp;
	
	private String birthday;
	
	public B200Request() {
		setActionCode("B200");
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

	public Integer getHasExp() {
		return hasExp;
	}

	public void setHasExp(Integer hasExp) {
		this.hasExp = hasExp;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
