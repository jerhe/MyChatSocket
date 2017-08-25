package com.etc.entity;

import java.io.Serializable;

public class Groups implements Serializable{

	private static final long serialVersionUID = -9206448650793338285L;
	private int id ;
	private String groupid;
	private String groupname;
	private String username;
	private int isadmin;
	private String groupimg;
	private String remark;
	public Groups() {
	}
	
	public Groups(int id, String groupid, String groupname, String username,
			int isadmin, String groupimg, String remark) {
		super();
		this.id = id;
		this.groupid = groupid;
		this.groupname = groupname;
		this.username = username;
		this.isadmin = isadmin;
		this.groupimg = groupimg;
		this.remark = remark;
	}

	public Groups(String groupid, String groupname, String username,
			int isadmin, String groupimg, String remark) {
		super();
		this.groupid = groupid;
		this.groupname = groupname;
		this.username = username;
		this.isadmin = isadmin;
		this.groupimg = groupimg;
		this.remark = remark;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Groups [id=");
		builder.append(id);
		builder.append(", groupid=");
		builder.append(groupid);
		builder.append(", groupname=");
		builder.append(groupname);
		builder.append(", username=");
		builder.append(username);
		builder.append(", isadmin=");
		builder.append(isadmin);
		builder.append(", groupimg=");
		builder.append(groupimg);
		builder.append(", remark=");
		builder.append(remark);
		builder.append("]");
		return builder.toString();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getIsadmin() {
		return isadmin;
	}
	public void setIsadmin(int isadmin) {
		this.isadmin = isadmin;
	}
	public String getGroupimg() {
		return groupimg;
	}
	public void setGroupimg(String groupimg) {
		this.groupimg = groupimg;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
}
