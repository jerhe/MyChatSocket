package com.etc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.sql.rowset.CachedRowSet;

import com.etc.entity.GroupMessages;
import com.etc.entity.Messages;
import com.etc.entity.Users;
import com.etc.util.DBUTIL;
import com.etc.util.DataUtil;

public class GroupMessagesDao extends DBUTIL {
	/**
	 * 获取和这个人有关的信息
	 * 
	 * @param fromname
	 * @return
	 */
	public ArrayList<GroupMessages> getToName(String fromname) {
		ArrayList<GroupMessages> list = new ArrayList<>();
		String sql = "select * from groupmessages where fromname=? and isread = 1";
		CachedRowSet crs = execQuery(sql, fromname);
		try {
			while (crs.next()) {
				int id = crs.getInt("id");
				String togroupid = crs.getString("togroupid");
				String content = crs.getString("content");
				String sendtime = crs.getString("sendtime");
				GroupMessages gmsg = new GroupMessages(id, fromname, togroupid, content,
						sendtime, 1);
				list.add(gmsg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取未读消息
	 * 
	 * @param fromname
	 * @param toname
	 * @return
	 */
	
	
	
	/**
	 * 群消息设置为已读
	 * 
	 * @param msg
	 */
	public boolean Read(int gmsgid) {
		String sql ="select isread from groupmessages where id =?";
		CachedRowSet crs= execQuery(sql, gmsgid);
		int read = 0;
		try {
			if(crs.next()){
				if((read=crs.getInt("isread"))==0){
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		String sql2 = "update groupmessages set isread = ? where id =?";
		execUpdate(sql2, read,gmsgid);
		return true;
	}

	/**
	 * 发送信息是保存id
	 * @param msg
	 * @return
	 */
	public int saveGroupMessages(GroupMessages gmsg) {
		String sql = "insert into groupmessages values(null,?,?,?,?,?)";
		execUpdate(sql, gmsg.getFromname(), gmsg.getGroupid(), gmsg.getContent(),
				gmsg.getSendtime(),gmsg.getIsread());
		String sql1 = "select id from groupmessages where fromname=? and togroupid =? and sendtime=?";
		CachedRowSet crs= execQuery(sql1, gmsg.getFromname(),gmsg.getGroupid(),gmsg.getSendtime());
		try {
			if(crs.next()){
			return crs.getInt("id");	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 通过名字获取他的最近聊天
	 * 
	 * @param name
	 * @return
	 */
	public ArrayList<Users> getRecentUsersByUserName(String name) {
		ArrayList<Users> list = new ArrayList<>();
		ArrayList<String> listName = new ArrayList<>();
		/**
		 * 先从数据库中获取到最近聊天的用户名
		 */
		String sql = "select * from messages where toname =? or fromname=? order by sendtime limit 0,100";
		CachedRowSet crs = execQuery(sql, name, name);
		try {
			while (crs.next()) {
				String str1 = crs.getString("fromname");
				String str2 = crs.getString("toname");
				if (!str1.equals(name)) {
					if (!listName.contains(str1)) {
						listName.add(str1);
					}
				}
				if (!str2.equals(name)) {
					if (!listName.contains(str2)) {
						listName.add(str2);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		/**
		 * 通过获取到的用户名从数据中获取Users对象
		 */
		if(!listName.isEmpty()){
			UsersDao usersDao =new UsersDao();
			for (String string : listName) {
				//System.out.println(string);
				Users u=usersDao.getUsersByName(string);
				//System.out.println(u);
				list.add(u);
			}
		}
		
		return list;
	}

	/**
	 * 清除记录待定
	 */
}
