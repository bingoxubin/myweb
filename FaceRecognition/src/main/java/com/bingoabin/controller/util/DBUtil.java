package com.bingoabin.controller.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author xubin03
 * @date 2021/4/10 11:18 下午
 */
public class DBUtil {
	private static String url;
	private static String user;
	private static String password;

	static {
		//加载配置文件
		Properties ppt = new Properties();
		InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
		try {
			ppt.load(is);
			url = ppt.getProperty("url");
			user = ppt.getProperty("user");
			password = ppt.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConn() {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
