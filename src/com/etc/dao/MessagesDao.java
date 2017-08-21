package com.etc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.sql.rowset.CachedRowSet;

import com.etc.entity.Messages;
import com.etc.entity.Users;
import com.etc.util.DBUTIL;

public class MessagesDao extends DBUTIL {
	/**
	 * ��ȡ��������йص���Ϣ
	 * 
	 * @param fromname
	 * @return
	 */
	public ArrayList<Messages> getToName(String fromname) {
		ArrayList<Messages> list = new ArrayList<>();
		String sql = "select * from messages where fromname=? and isread = 1";
		CachedRowSet crs = execQuery(sql, fromname);
		try {
			while (crs.next()) {
				int id = crs.getInt("id");
				String toname = crs.getString("toname");
				String content = crs.getString("content");
				Date sendtime = crs.getDate("sendtime");
				Messages msg = new Messages(id, fromname, toname, content,
						sendtime, 1);
				list.add(msg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * ��Ϣ����Ϊ�Ѷ�
	 * 
	 * @param msg
	 */
	public void setRead(Messages msg) {
		String sql = "update messages set isread = 0 where id =?";
		execUpdate(sql, msg.getId());
	}

	/**
	 * ������Ϣʱ������Ϣ
	 * 
	 * @param msg
	 */
	public void saveMessage(Messages msg) {
		String sql = "insert into messages vlaues(null,?,?,?,?,1)";
		execUpdate(sql, msg.getFromname(), msg.getToname(), msg.getContent(),
				msg.getSendtime());

	}

	/**
	 * ͨ�����ֻ�ȡ�����������
	 * 
	 * @param name
	 * @return
	 */
	public ArrayList<Users> getRecentUsersByUserName(String name) {
		ArrayList<Users> list = new ArrayList<>();
		ArrayList<String> listName = new ArrayList<>();
		/**
		 * �ȴ����ݿ��л�ȡ�����������û���
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
		 * ͨ����ȡ�����û����������л�ȡUsers����
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
	 * �����¼����
	 */
}
