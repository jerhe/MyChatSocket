package com.etc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

/***
 * 
 * @author Administrator
 * 
 */
public class DBUTIL {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/chat?useSSL=false&characterEncoding=utf-8";
	private static final String USER = "root";
	private static final String PWD = "zhuzhen";

	/**
	 * �õ�һ�����ݿ�����Ӷ���
	 * 
	 * @return
	 */
	private static Connection getConn() {
		// �� ���������ص�JVM
		// com.mysql.jdbc.Driver
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			// �ڻ�ȡ���Ӷ��� ͨ��˭����ȡ���Ӷ���?
			conn = DriverManager.getConnection(URL, USER, PWD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;

	}

	/**
	 * ִ����ɾ�ĵ�ͨ�÷��� Object... params �ɱ䳤�ȵ�����
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int execUpdate(String sql, Object... params) {

		// �ڻ�ȡ���Ӷ��� ͨ��˭����ȡ���Ӷ���?
		Connection conn = getConn();
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = conn.prepareStatement(sql);

			// insert into book(bookName,bookPrice,bookAuthor,typeId)
			// values(?,?,?,?)
			// ��? ��������(����)
			if (params != null)
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}

			// .....
			// �� ͨ��pstmt����ķ����õ��õ�һ����Ӱ�����
			n = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// �ͷ���Դ�ĵ��ã����ô�н�������� ʹ��null
			closeAll(null, pstmt, conn);
		}
		return n;
	}

	/**
	 * �򵥵Ĳ�ѯ�����ķ�װ
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static CachedRowSet execQuery(String sql, Object... params) {

		// �ڻ�ȡ���Ӷ��� ͨ��˭����ȡ���Ӷ���?
		Connection conn = getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {

			crs = new CachedRowSetImpl();
			pstmt = conn.prepareStatement(sql);

			// insert into book(bookName,bookPrice,bookAuthor,typeId)
			// values(?,?,?,?)
			// ��? ��������(����)
			if (params != null)
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}

			// .....
			// �� ͨ��pstmt����ķ����õ�һ�������
			rs = pstmt.executeQuery();

			crs.populate(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// �ͷ���Դ�ĵ��ã����ô�н�������� ʹ��null
			closeAll(rs, pstmt, conn);
		}

		return crs;
	}

	/**
	 * �ͷ���Դ�Ĵ���
	 * 
	 * @param rs
	 * @param pstmt
	 * @param conn
	 */
	public static void closeAll(ResultSet rs, PreparedStatement pstmt,
			Connection conn) {

		if (null != rs)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (null != pstmt)
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (null != conn)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

	}
}
