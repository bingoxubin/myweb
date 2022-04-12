package com.bingoabin.controller.dao;

import com.bingoabin.controller.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {
	//查询所有用户
	@Select("select * from usermvc")
	public List<User> findUsers();

	//用户注册
	@Insert("INSERT INTO usermvc (username,PASSWORD) VALUES(#{username},#{PASSWORD})")
	public void insertUsers(User users);

	//用户登录
	@Select("select * from usermvc where username=#{username} and PASSWORD=#{PASSWORD}")
	public User login(User users);
}