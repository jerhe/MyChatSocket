package com.etc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;

import com.etc.entity.Groups;
import com.etc.entity.Users;
import com.etc.util.DBUTIL;

/**
 * 群的数据操作
 * @author zhuzhen
 *
 */
public class GroupsDao extends DBUTIL {
	
	/**
	 * 通过username获取他所在群的信息
	 * @param username
	 * @return
	 */
	public ArrayList<Groups> getGroupsByUsername(String username) {
		ArrayList<Groups> list = new ArrayList<>();
		UsersDao usersDao = new UsersDao();
		String sql = "select * from groups where username=?";
		CachedRowSet crs = execQuery(sql, username);
		try {
			while (crs.next()) {
				int id =crs.getInt("id");
				String groupid =crs.getString("groupid");
				String groupname=crs.getString("groupname");
				int isadmin=crs.getInt("isadmin");
				String groupimg =crs.getString("groupimg");
				Groups g= new Groups(id,groupid,groupname,username,isadmin,groupimg);
				list.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 通过groupid 获取该群的所有群成员
	 * @param groupid
	 * @return
	 */
	public ArrayList<Users> getGroupsByGroupid(String groupid) {
		ArrayList<Users> list = new ArrayList<>();
		UsersDao usersDao = new UsersDao();
		String sql = "select username from groups where groupid=?";
		CachedRowSet crs = execQuery(sql, groupid);
		try {
			while (crs.next()) {
				String username = crs.getString("username");
				
				list.add(usersDao.getUsersByName(username));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 添加加入群
	 * @param g
	 */
	public void addGroups(Groups g) {
		String sql = "insert into groups vlaues(null,?,?,?,?,?)";
		execUpdate(sql, g.getGroupid(), g.getGroupname(), g.getUsername(),0);
	}

	/**
	 * 删除群
	 * 
	 * @param groupid
	 */
	public void deleteGroupsByGroupId(String groupid) {
		String sql = "delete from groups where groupid = ?";
		execUpdate(sql, groupid);
	}
	
	/**
	 * 退出群
	 * @param id
	 */
	public void quitGroupsById(int id){
		String sql = "delete from groups where id=?";
		execUpdate(sql, id);
	}
	
	/**
	 * 创建群
	 * @param groupname 群昵称
	 * @param name 用户名
	 * @return 是否创建成功
	 */
	public synchronized boolean createGroups(String groupname,String username){
		Date date= new Date();
		String groupid = date.getTime()+"";
		String sql = "insert into groups vlaues(null,?,?,?,?,?)";
		if(execUpdate(sql,groupid,groupname,username,1,"/img/bb.jpg")==0){
			return false;
		}else{
			return true;
		}
		
	}
	
	/**
	 * 管理员更新群头像
	 * @param url
	 */
	public void updateGroupImgByGroupId(int groupid ,String url){
		String sql ="update groups set groupimg =? where groupid=?";
		execUpdate(sql, groupid,url);
	}
}
