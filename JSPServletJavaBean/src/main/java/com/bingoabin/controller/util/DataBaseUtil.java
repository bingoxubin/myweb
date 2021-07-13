package com.bingoabin.controller.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author xubin03
 * @date 2021/3/22 9:54 上午
 */
public class DataBaseUtil {

	//连接数据库
	public static Connection getConn() {
		Connection conn = null;
		try {
			//加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/myweb?useUnicode=true&characterEncoding=UTF-8";
		try {
			conn = DriverManager.getConnection(url, "root", "111111");
			if (conn != null) {
				System.out.println(1123);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	//关闭数据库
	public static void closeConn(Connection conn){
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
