package com.etc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 群消息类
 * 
 * @author zhuzhen
 * 
 */
public class GroupMessages implements Serializable {
	int id;
	String fromname;
	String groupid;
	String content;
	String sendtime;
	int isread;

	public GroupMessages() {
	}

	/**
	 * 
	 * @param fromname
	 * @param groupid
	 * @param content
	 * @param sendtime
	 * @param isread
	 */
	public GroupMessages(String fromname, String groupid, String content,
			String sendtime, int isread) {
		super();
		this.fromname = fromname;
		this.groupid = groupid;
		this.content = content;
		this.sendtime = sendtime;
		this.isread = isread;
	}

	/**
	 * 
	 * @param id
	 * @param fromname
	 * @param groupid
	 * @param content
	 * @param sendtime
	 * @param isread
	 */
	public GroupMessages(int id, String fromname, String groupid, String content,
			String sendtime, int isread) {
		super();
		this.id = id;
		this.fromname = fromname;
		this.groupid = groupid;
		this.content = content;
		this.sendtime = sendtime;
		this.isread = isread;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFromname() {
		return fromname;
	}

	public void setFromname(String fromname) {
		this.fromname = fromname;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public int getIsread() {
		return isread;
	}

	public void setIsread(int isread) {
		this.isread = isread;
	}

}
