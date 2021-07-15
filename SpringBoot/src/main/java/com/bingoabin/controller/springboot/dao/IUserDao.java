package com.bingoabin.controller.springboot.dao;

import com.bingoabin.controller.springboot.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IUserDao {
	//查询所有用户
	@Select("select * from usermvc")
	List<User> getAllUser();

	//用户注册
	@Insert("INSERT INTO usermvc (username,PASSWORD) VALUES(#{username},#{PASSWORD})")
	public void insertUser(User user);

	//用户登录
	@Select("select * from usermvc where username=#{username} and PASSWORD=#{PASSWORD}")
	public User login(User user);
}
