package com.etc.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.etc.entity.Users;
import com.etc.util.DBUTIL;

public class UsersDao extends DBUTIL {

	public Users getUsersById(int id) {
		String sql = "select * from usertable where id =?";
		CachedRowSet crs = execQuery(sql, id);
		Users users = null;
		try {
			if (crs.next()) {
				users = new Users(crs.getInt("id"), crs.getString("name"),
						crs.getString("pwd"), crs.getString("remark"),
						crs.getString("headimg"), crs.getString("sex"),
						crs.getString("birthday"), crs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public Users getUsersByName(String name) {
		String sql = "select * from users where name = ?";
		CachedRowSet crs = execQuery(sql, name);
		Users users = null;
		try {
			if (crs.next()) {
				users = new Users(crs.getInt("id"), crs.getString("name"),
						crs.getString("pwd"), crs.getString("remark"),
						crs.getString("headimg"), crs.getString("sex"),
						crs.getString("birthday"), crs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 模糊查询用户名返回一个Users
	 * 
	 * @param name
	 * @return
	 */
	public ArrayList<Users> geteVagueUsersByName(String name) {
		ArrayList<Users> list = new ArrayList<>();
		String sql = "select * from users where name like ?";
		CachedRowSet crs = execQuery(sql, "%" + name + "%");
		try {
			while (crs.next()) {
				Users users = new Users(crs.getInt("id"),
						crs.getString("name"), crs.getString("pwd"),
						crs.getString("remark"), crs.getString("headimg"),
						crs.getString("sex"), crs.getString("birthday"),
						crs.getString("email"));
				list.add(users);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
