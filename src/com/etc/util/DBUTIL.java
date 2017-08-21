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
	 * 得到一个数据库的连接对象
	 * 
	 * @return
	 */
	private static Connection getConn() {
		// ① 将驱动加载到JVM
		// com.mysql.jdbc.Driver
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			// ②获取连接对象 通过谁来获取连接对象?
			conn = DriverManager.getConnection(URL, USER, PWD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;

	}

	/**
	 * 执行增删改的通用方法 Object... params 可变长度的数组
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int execUpdate(String sql, Object... params) {

		// ②获取连接对象 通过谁来获取连接对象?
		Connection conn = getConn();
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = conn.prepareStatement(sql);

			// insert into book(bookName,bookPrice,bookAuthor,typeId)
			// values(?,?,?,?)
			// 将? 补充完整(补齐)
			if (params != null)
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}

			// .....
			// ⑤ 通过pstmt对象的方法得到得到一个受影响的行
			n = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源的调用，如果么有结果集对象 使用null
			closeAll(null, pstmt, conn);
		}
		return n;
	}

	/**
	 * 简单的查询操作的封装
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static CachedRowSet execQuery(String sql, Object... params) {

		// ②获取连接对象 通过谁来获取连接对象?
		Connection conn = getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {

			crs = new CachedRowSetImpl();
			pstmt = conn.prepareStatement(sql);

			// insert into book(bookName,bookPrice,bookAuthor,typeId)
			// values(?,?,?,?)
			// 将? 补充完整(补齐)
			if (params != null)
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}

			// .....
			// ⑤ 通过pstmt对象的方法得到一个结果集
			rs = pstmt.executeQuery();

			crs.populate(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源的调用，如果么有结果集对象 使用null
			closeAll(rs, pstmt, conn);
		}

		return crs;
	}

	/**
	 * 释放资源的代码
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
