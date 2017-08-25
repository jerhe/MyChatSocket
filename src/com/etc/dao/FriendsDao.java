package com.etc.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.etc.entity.Friends;
import com.etc.entity.Users;
import com.etc.util.DBUTIL;

/**
 * ���ѱ�����
 * 
 * @author zhuzhen
 * 
 */
public class FriendsDao extends DBUTIL {

	/**
	 * ����ҵĺ��ѵ����ּ���
	 * 
	 * @param myname
	 * @return
	 */
	public ArrayList<String> getMyFriendsName(String friendname, String myname) {
		ArrayList<String> list = new ArrayList<>();
		String sql = "select friendname from friends where myname =? and friendname like ?";
		CachedRowSet crs = execQuery(sql, myname, "%" + friendname + "%");
		try {
			while (crs.next()) {
				String str = crs.getString("friendname");
				list.add(str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * �ж������û��ǲ��Ǻ���
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public boolean isFriends(String str1, String str2) {
		String sql1 = "select * from friends where myname=? and friendname=?";
		String sql2 = "select * from friends where myname=? and friendname=?";
		CachedRowSet crs1 = execQuery(sql1, str1, str2);
		CachedRowSet crs2 = execQuery(sql1, str2, str1);
		try {
			if (crs1.next()) {
				return true;
			}
			if (crs2.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ��ȡ�ҵ����к���
	 * 
	 * @param myname
	 * @return
	 */
	public ArrayList<Users> getFriends(String myname) {
		ArrayList<String> list = new ArrayList<>();
		ArrayList<Users> usersList = new ArrayList<>();
		String sql = "select friendname from friends where myname=?";
		CachedRowSet crs = execQuery(sql, myname);
		try {
			while (crs.next()) {
				String friendname = crs.getString("friendname");
				list.add(friendname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (!list.isEmpty()) {
			UsersDao usersDao = new UsersDao();
			for (int i = 0; i < list.size(); i++) {
				Users us = usersDao.getUsersByName(list.get(i));
				// System.out.println(us);
				if (us != null) {
					usersList.add(us);
				}

			}
		}
		return usersList;
	}

	/**
	 * ������ѽ������ѹ�ϵ
	 * 
	 * @param f
	 */
	public void AddFriends(Friends f) {
		String sql = "insert into friends values(null,?,?)";
		execUpdate(sql, f.getMyname(), f.getFriendname());
	}

	public void deleteFriendRelation(String myname, String friendname) {
		String sql = "delete from friends where myname=? and friendname=?";
		execUpdate(sql, myname, friendname);
		execUpdate(sql, friendname, myname);
	}

	/**
	 * ͨ��idɾ������
	 * 
	 * @param id
	 */
	public void deleteFriendsById(int id) {
		String sql = "delete from friends where id =?";
		execUpdate(sql, id);
	}
}
