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
 * 
 * @author zhuzhen
 * 
 */
public class GroupsDao extends DBUTIL {
	/**
	 * 通过群id加入该群
	 */

	public boolean intoGroupByGroupid(String groupid, String username) {
		String sql = "select * from where groupid=? and username =? and username =?";
		CachedRowSet crs1 = execQuery(sql, groupid,username);
		Groups g = null;
		try {
			if (crs1.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql2 = "select * from  groups where groupid=? ";
		CachedRowSet crs2 = execQuery(sql2, groupid);
		try {
			if (crs2.next()) {
				String groupname = crs2.getString("groupname");
				String groupimg = crs2.getString("groupimg");
				String remark = crs2.getString("remark");
				g = new Groups(groupid, groupname, username, 0, groupimg,remark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		addGroups(g);
		return true;
	}

	/**
	 * 通过群名加入该群
	 */
	public boolean intoGroupByGroupname(String groupname, String username) {
		String sql = "select * from groups where groupname=? and username =?";
		CachedRowSet crs1= execQuery(sql, groupname,username);
		Groups g = null;
		try {
			if (crs1.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql2 = "select * from groups where groupname=?";
		CachedRowSet crs2 = execQuery(sql2, groupname);
		try {
			if (crs2.next()) {
				String groupid = crs2.getString("groupid");
				String groupimg = crs2.getString("groupimg");
				String remark = crs2.getString("remark");
				g = new Groups(groupid, groupname, username, 0, groupimg,remark);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		addGroups(g);
		return true;
	}

	/**
	 * 通过群名进行模糊查找
	 * 
	 * @param groupname
	 * @return
	 */
	public ArrayList<String> getGroupsByGroupname(String groupname) {
		ArrayList<String> list = new ArrayList<>();
		String sql = "select * from groups where groupname like ?";
		CachedRowSet crs = execQuery(sql, "%" + groupname + "%");
		try {
			while (crs.next()) {
				String gn = crs.getString("groupname");
				if (!list.contains(gn)) {
					list.add(gn);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 通过groupid 获取groups
	 * 
	 * @param groupid
	 * @return
	 */
	public ArrayList<String> getGroupsByGroupid(String groupid) {
		ArrayList<String> list = new ArrayList<>();
		String sql = "select groupname from groups where groupid=?";
		CachedRowSet crs = execQuery(sql, groupid);
		try {
			while (crs.next()) {
				String gn = crs.getString("groupname");
				if (!list.contains(gn)) {
					list.add(gn);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 通过username获取他所在群的信息
	 * 
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
				int id = crs.getInt("id");
				String groupid = crs.getString("groupid");
				String groupname = crs.getString("groupname");
				int isadmin = crs.getInt("isadmin");
				String groupimg = crs.getString("groupimg");
				String remark=crs.getString("remark");
				Groups g = new Groups(id, groupid, groupname, username,
						isadmin, groupimg,remark);
				list.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * 通过groupid 获取该群的所有群成员
	 * 
	 * @param groupid
	 * @return
	 */
	public ArrayList<Users> getGroupsUserByGroupid(String groupid) {
		ArrayList<Users> list = new ArrayList<>();
		UsersDao usersDao = new UsersDao();
		String sql = "select username from groups where groupid=?";
		CachedRowSet crs = execQuery(sql, groupid);
		try {
			while (crs.next()) {
				String username = crs.getString("username");
				// System.out.println(username);
				list.add(usersDao.getUsersByName(username));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 添加加入群
	 * 
	 * @param g
	 */
	public void addGroups(Groups g) {
		if (g != null) {
			String sql = "insert into groups values(null,?,?,?,?,?,?)";
			execUpdate(sql,g.getGroupid(), g.getGroupname(), g.getUsername(),
					0,g.getGroupimg(),g.getRemark());
		}
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
	 * 
	 * @param id
	 */
	public void quitGroupsById(int id) {
		String sql = "delete from groups where id=?";
		execUpdate(sql, id);
	}

	/**
	 * 创建群
	 * 
	 * @param groupname
	 *            群昵称
	 * @param name
	 *            用户名
	 * @return 是否创建成功
	 */
	public synchronized boolean createGroups(String groupname, String username) {
		Date date = new Date();
		String groupid = date.getTime() + "";
		String sql = "insert into groups vlaues(null,?,?,?,?,?,'')";
		if (execUpdate(sql, groupid, groupname, username, 1, "/img/bb.jpg") == 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 管理员更新群头像
	 * 
	 * @param url
	 */
	public void updateGroupImgByGroupId(int groupid, String url) {
		String sql = "update groups set groupimg =? where groupid=?";
		execUpdate(sql, groupid, url);
	}
}
