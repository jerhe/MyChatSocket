package com.etc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * œ˚œ¢¿‡
 * @author zhuzhen
 *
 */
public class Messages implements Serializable{
	int id;
	String fromname;
	String toname;
	String content;
	Date sendtime;
	int isread;
	public Messages() {
	}
	
	public Messages(String fromname, String toname, String content,
			Date sendtime, int isread) {
		super();
		this.fromname = fromname;
		this.toname = toname;
		this.content = content;
		this.sendtime = sendtime;
		this.isread = isread;
	}

	public Messages(int id, String fromname, String toname,
			String content, Date sendtime, int isread) {
		super();
		this.id = id;
		this.fromname = fromname;
		this.toname = toname;
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

	public String getToname() {
		return toname;
	}

	public void setToname(String toname) {
		this.toname = toname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public int getIsread() {
		return isread;
	}

	public void setIsread(int isread) {
		this.isread = isread;
	}
	
	
}
