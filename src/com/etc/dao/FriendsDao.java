package com.etc.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.etc.entity.Friends;
import com.etc.entity.Users;
import com.etc.util.DBUTIL;

/**
 * 好友表数据
 * 
 * @author zhuzhen
 * 
 */
public class FriendsDao extends DBUTIL {

	/**
	 * 获取我的所有好友
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
			for (int i=0 ;i<list.size();i++) {
				Users us = usersDao.getUsersByName(list.get(i));
				//System.out.println(us);
				if(us!=null){
					usersList.add(us);
				}
				
			}
		}
		return usersList;
	}

	/**
	 * 添加朋友建立好友关系
	 * 
	 * @param f
	 */
	public void AddFriends(Friends f) {
		String sql = "insert into friends vlaues(null,?,?)";
		execUpdate(sql, f.getMyname(), f.getFriendname());
	}

	/**
	 * 通过id删除好友
	 * 
	 * @param id
	 */
	public void deleteFriendsById(int id) {
		String sql = "delete from friends where id =?";
		execUpdate(sql, id);
	}
}
