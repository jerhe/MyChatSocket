package com.etc.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.etc.entity.Users;
import com.etc.util.DBUTIL;

public class UsersDao extends DBUTIL {

	/**
	 * 通过用户名修改密码
	 * @param name
	 * @param pwd
	 */
	public void resetPwdByName(String name,String pwd){
		String sql="update users set pwd=? where name=?";
		execUpdate(sql, pwd,name);
	}
	
	/**
	 * 用户注册
	 * @param name
	 * @param pwd
	 * @param email
	 * @return
	 */
	public boolean addUsers(String name, String pwd, String email) {
		String sql = "insert into users(id,name,pwd,email,headimg,isonline) values(null,?,?,?,'img/bb.jpg',0)";
		if (execUpdate(sql, name, pwd, email) == 1)
			return true;
		return false;
	}

	/**
	 * 修改用户资料
	 * 
	 * @param user
	 */
	public void updateUsers(Users user, String oldname) {
		String sql = "update users set remark=?,birthday=?,sex=?,isonline=0 where id=?";
		execUpdate(sql, user.getName(), user.getRemark(), user.getBirthday(),
				user.getSex(), user.getId());
	}

	/**
	 * 设置为在线
	 * 
	 * @param id
	 */
	public void setOnlineById(int id) {
		String sql = "update users set isonline = 1 where id =?";
		execUpdate(sql, id);
	}

	public void setUnOnlineById(int id) {
		String sql = "update users set isonline = 0 where id =?";
		execUpdate(sql, id);
	}

	/**
	 * 通过id获取User
	 * 
	 * @param id
	 * @return
	 */
	public Users getUsersById(int id) {
		String sql = "select * from users where id =?";
		CachedRowSet crs = execQuery(sql, id);
		Users users = null;
		try {
			if (crs.next()) {
				users = new Users(crs.getInt("id"), crs.getString("name"),
						crs.getString("pwd"), crs.getString("remark"),
						crs.getString("headimg"), crs.getString("sex"),
						crs.getString("birthday"), crs.getString("email"),
						crs.getInt("isonline"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 通过用户名查询到user
	 * 
	 * @param name
	 * @return
	 */
	public Users getUsersByName(String name) {
		String sql = "select * from users where name = ?";
		CachedRowSet crs = execQuery(sql, name);
		Users users = null;
		try {
			if (crs.next()) {
				users = new Users(crs.getInt("id"), crs.getString("name"),
						crs.getString("pwd"), crs.getString("remark"),
						crs.getString("headimg"), crs.getString("sex"),
						crs.getString("birthday"), crs.getString("email"),
						crs.getInt("isonline"));
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
						crs.getString("email"), crs.getInt("isonline"));
				list.add(users);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 判断用户是否存在
	 * @param name
	 * @return
	 */
	public boolean isExistence(String name){
		String sql ="select * from users where name =?";
		CachedRowSet crs = execQuery(sql, name);
		try {
			if(crs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
