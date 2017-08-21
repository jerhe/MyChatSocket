package com.etc.entity;

import java.io.Serializable;

public class Groups implements Serializable{

	private static final long serialVersionUID = -9206448650793338285L;
	private int id ;
	private String groupid;
	private String groupname;
	private String username;
	private int isadmin;
	public Groups() {
	}
	
	public Groups(String groupid, String groupname, String username, int isadmin) {
		super();
		this.groupid = groupid;
		this.groupname = groupname;
		this.username = username;
		this.isadmin = isadmin;
	}


	public Groups(int id, String groupid, String groupname, String username,
			int isadmin) {
		super();
		this.id = id;
		this.groupid = groupid;
		this.groupname = groupname;
		this.username = username;
		this.isadmin = isadmin;
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
		builder.append("]");
		return builder.toString();
	}
	
	
}
