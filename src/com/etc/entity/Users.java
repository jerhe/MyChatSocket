package com.etc.entity;

import java.io.Serializable;

/**
 * ”√ªß¿‡
 * @author zhuzhen
 *
 */
public class Users implements Serializable {
	
	private static final long serialVersionUID = 4718164762975244274L;
	private int id;
	private String name;
	private String pwd;
	private String remark;
	private String headimg;
	private String sex;
	private String birthday;
	private String email;
	

	public Users() {
	}

	
	public Users(int id, String name, String pwd, String remark,
			String headimg, String sex, String birthday, String email) {
		super();
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.remark = remark;
		this.headimg = headimg;
		this.sex = sex;
		this.birthday = birthday;
		this.email = email;
	}


	public Users(String name, String pwd, String remark, String headimg,
			String sex, String birthday, String email) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.remark = remark;
		this.headimg = headimg;
		this.sex = sex;
		this.birthday = birthday;
		this.email = email;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getHeadimg() {
		return headimg;
	}


	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Users [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", pwd=");
		builder.append(pwd);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", headimg=");
		builder.append(headimg);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", birthday=");
		builder.append(birthday);
		builder.append(", email=");
		builder.append(email);
		builder.append("]");
		return builder.toString();
	}

	


}
