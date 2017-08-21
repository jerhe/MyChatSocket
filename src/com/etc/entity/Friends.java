package com.etc.entity;

import java.io.Serializable;

public class Friends implements Serializable{

	private static final long serialVersionUID = 8461397278338447669L;
	private int id ;
	private String myname;
	private String friendname;
	public Friends() {
	}
	public Friends(String myname, String friendname) {
		super();
		this.myname = myname;
		this.friendname = friendname;
	}

	public Friends(int id, String myname, String friendname) {
		super();
		this.id = id;
		this.myname = myname;
		this.friendname = friendname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMyname() {
		return myname;
	}
	public void setMyname(String myname) {
		this.myname = myname;
	}
	public String getFriendname() {
		return friendname;
	}
	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Friends [id=");
		builder.append(id);
		builder.append(", myname=");
		builder.append(myname);
		builder.append(", friendname=");
		builder.append(friendname);
		builder.append("]");
		return builder.toString();
	}	
}
