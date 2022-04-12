package com.bingoabin.springboot.service;

import com.bingoabin.springboot.domain.User;

import java.util.List;

/**
 * @author xubin03
 * @date 2021/3/23 9:47 下午
 */
public interface IUserService {
	//查询所有用户
	List<User> getAllUser();

	//用户注册
	public void insertUser(User user);

	//用户登录
	public boolean login(User user);
}
