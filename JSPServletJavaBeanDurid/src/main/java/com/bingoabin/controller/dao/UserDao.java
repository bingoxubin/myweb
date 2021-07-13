package com.bingoabin.controller.dao;

import com.bingoabin.controller.bean.User;
import com.bingoabin.controller.util.DataBaseUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author xubin03
 * @date 2021/3/22 10:02 上午
 */
public class UserDao {
	private JdbcTemplate template = new JdbcTemplate(DataBaseUtil.getDataSource());

	//查询数据库信息

	/**
	 * 在用户提交注册信息是，需要判断该用户名是否存在
	 *
	 * @param username
	 * @return
	 */
	public boolean userExist(String username) {
		//根据指定的用户名查询信息
		String sql = "select username from user";
		List<Map<String, Object>> maps = template.queryForList(sql);
		for(Map<String,Object> map:maps){
			for (Object existname : map.values()) {
				if(username.equals(existname)){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 在用户提交注册信息时，如果注册成功需要将，需要将用户注册的信息存入数据库
	 */
	public void saveUser(User user) {
		//插入信息的sql语句
		String sql = "insert into user(username,password,sex,question,answer,email) values(?,?,?,?,?,?)";
		template.update(sql, user.getUsername(), user.getPassword(), user.getSex(), user.getQuestion(), user.getAnswer(), user.getEmail());
	}

	/**
	 * 注册成功后，用户既可通过注册的用户及密码进行登录，对于程序而言，此操作实质是根据
	 * 用户所提供的用户名及密码在数据库进行查询，如果查询成功，则登录成功
	 */
	public User login(String username, String password) {
		String sql = "select * from user where username = ? and password = ?";
		User user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
		return user;
	}
}

