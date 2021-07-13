package com.bingoabin.controller.service;

import com.bingoabin.controller.domain.User;

import java.util.List;

public interface IUserService {
	//查询所有用户
	public List<User> findUsers();

	//用户注册
	public void insertUsers(User users);

	//用户登录
	public boolean login(User users);
}
