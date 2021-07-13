package com.bingoabin.controller.service;

/**
 * @author xubin03
 * @date 2021/3/22 8:35 下午
 */

import com.bingoabin.controller.dao.IUserDao;
import com.bingoabin.controller.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService implements IUserService {
	@Autowired
	private IUserDao userDao;

	@Override
	public List<User> findUsers() {
		System.out.println("业务层：查询用户");
		return userDao.findUsers();
	}

	@Override
	public void insertUsers(User users) {
		System.out.println("业务层：用户注册");
		userDao.insertUsers(users);
	}

	@Override
	public boolean login(User users) {
		System.out.println("业务层：用户登录");
		if (userDao.login(users) == null) {
			return false;
		} else {
			return true;
		}
	}
}