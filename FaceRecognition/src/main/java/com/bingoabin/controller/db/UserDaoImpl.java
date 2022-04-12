package com.bingoabin.controller.db;

import com.bingoabin.controller.bean.User;
import com.bingoabin.controller.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xubin03
 * @date 2021/4/11 1:55 上午
 */
//尽可能晚的连接数据库，尽可能早的释放数据库
public class UserDaoImpl implements UserDao {

	private static final String SQL_INSERT = "insert into user(face_id,city,logintime) values (?,?,?)";
	private static final String SQL_UPDATE_USER_BY_FACEID = "update user set username = ? ,description = ? where face_id = ?";
	private static final String SQL_UPDATE_COUNT_BY_FACEID = "update user set count = ? ,logintime = ? where face_id = ?";
	private static final String SQL_FIND_USER_BY_FACEID = "select * from user where face_id = ?";

	@Override
	public int insert(User user) {
		//获取数据库连接
		Connection conn = DBUtil.getConn();
		//预编译执行sql的环境
		PreparedStatement state = null;
		try {
			state = conn.prepareStatement(SQL_INSERT);
			//填充预编译的参数
			state.setString(1, user.getFace_id());
			state.setString(2, user.getCity());
			state.setLong(3, System.currentTimeMillis());
			//执行存储
			int rowCount = state.executeUpdate();
			return rowCount;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			//释放连接
			DBUtil.close(conn, state, null);
		}
		return -1;
	}

	@Override
	public User count(String face_id) {
		//查询信息
		User user = findUserByFaceId(face_id);
		//判断时间间隔是否符合要求,符合则收录,比如一分钟收录一次
		if (System.currentTimeMillis() - user.getLongtime() > 60000) {
			user.setCount(user.getCount() + 1);
			//修改次数
			updateCountByFaceId(face_id, user.getCount());
		}
		return user;
	}

	@Override
	public int updateUserByFaceId(String face_id, User user) {
		//获取数据库连接
		Connection conn = DBUtil.getConn();
		//预编译执行sql的环境
		PreparedStatement state = null;
		try {
			state = conn.prepareStatement(SQL_UPDATE_USER_BY_FACEID);
			//填充预编译的参数
			state.setString(1, user.getUsername());
			state.setString(2, user.getDescription());
			state.setString(3, face_id);
			//执行存储
			int rowCount = state.executeUpdate();
			return rowCount;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			//释放连接
			DBUtil.close(conn, state, null);
		}
		return -1;
	}

	@Override
	public int updateCountByFaceId(String face_id, int count) {
		//获取数据库连接
		Connection conn = DBUtil.getConn();
		//预编译执行sql的环境
		PreparedStatement state = null;
		try {
			state = conn.prepareStatement(SQL_UPDATE_COUNT_BY_FACEID);
			//填充预编译的参数
			state.setInt(1, count);
			state.setString(2, face_id);
			state.setLong(3, System.currentTimeMillis());
			//执行存储
			int rowCount = state.executeUpdate();
			return rowCount;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			//释放连接
			DBUtil.close(conn, state, null);
		}
		return -1;
	}

	@Override
	public User findUserByFaceId(String face_id) {
		//获取数据库连接
		Connection conn = DBUtil.getConn();
		//预编译执行sql的环境
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			state = conn.prepareStatement(SQL_FIND_USER_BY_FACEID);
			//填充预编译的参数
			state.setString(1, face_id);
			//执行查询
			rs = state.executeQuery();
			//判断结果是否存在，存在就取出信息
			if (rs.next()) {
				int id = rs.getInt("id");
				int count = rs.getInt("count");
				long longTime = rs.getLong("logintime");
				String username = rs.getString("username");
				String city = rs.getString("city");
				String description = rs.getString("description");
				return new User(id, face_id, username, description, city, count, longTime);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			//释放连接
			DBUtil.close(conn, state, rs);
		}
		return null;
	}
}
