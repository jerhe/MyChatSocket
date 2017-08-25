package com.etc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;

import com.etc.entity.Groups;
import com.etc.entity.Users;
import com.etc.util.DBUTIL;

/**
 * Ⱥ�����ݲ���
 * 
 * @author zhuzhen
 * 
 */
public class GroupsDao extends DBUTIL {
	/**
	 * ͨ��Ⱥid�����Ⱥ
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
	 * ͨ��Ⱥ�������Ⱥ
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
	 * ͨ��Ⱥ������ģ������
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
	 * ͨ��groupid ��ȡgroups
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
	 * ͨ��username��ȡ������Ⱥ����Ϣ
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
	 * ͨ��groupid ��ȡ��Ⱥ������Ⱥ��Ա
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
	 * ��Ӽ���Ⱥ
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
	 * ɾ��Ⱥ
	 * 
	 * @param groupid
	 */
	public void deleteGroupsByGroupId(String groupid) {
		String sql = "delete from groups where groupid = ?";
		execUpdate(sql, groupid);
	}

	/**
	 * �˳�Ⱥ
	 * 
	 * @param id
	 */
	public void quitGroupsById(int id) {
		String sql = "delete from groups where id=?";
		execUpdate(sql, id);
	}

	/**
	 * ����Ⱥ
	 * 
	 * @param groupname
	 *            Ⱥ�ǳ�
	 * @param name
	 *            �û���
	 * @return �Ƿ񴴽��ɹ�
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
	 * ����Ա����Ⱥͷ��
	 * 
	 * @param url
	 */
	public void updateGroupImgByGroupId(int groupid, String url) {
		String sql = "update groups set groupimg =? where groupid=?";
		execUpdate(sql, groupid, url);
	}
}
