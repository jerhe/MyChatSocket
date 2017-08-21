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
 * @author zhuzhen
 *
 */
public class GroupsDao extends DBUTIL {
	
	/**
	 * ͨ��username��ȡ������Ⱥ����Ϣ
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
	 * ͨ��groupid ��ȡ��Ⱥ������Ⱥ��Ա
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
	 * ��Ӽ���Ⱥ
	 * @param g
	 */
	public void addGroups(Groups g) {
		String sql = "insert into groups vlaues(null,?,?,?,?,?)";
		execUpdate(sql, g.getGroupid(), g.getGroupname(), g.getUsername(),0);
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
	 * @param id
	 */
	public void quitGroupsById(int id){
		String sql = "delete from groups where id=?";
		execUpdate(sql, id);
	}
	
	/**
	 * ����Ⱥ
	 * @param groupname Ⱥ�ǳ�
	 * @param name �û���
	 * @return �Ƿ񴴽��ɹ�
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
	 * ����Ա����Ⱥͷ��
	 * @param url
	 */
	public void updateGroupImgByGroupId(int groupid ,String url){
		String sql ="update groups set groupimg =? where groupid=?";
		execUpdate(sql, groupid,url);
	}
}
