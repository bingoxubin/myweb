package com.bingoabin.springboot.service;

import com.bingoabin.springboot.domain.User;
import com.bingoabin.springboot.dao.IUserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xubin03
 * @date 2021/3/23 9:49 下午
 */
@Service
public class UserService implements IUserService{
	@Resource
	IUserDao userDao;

	@Override
	public List<User> getAllUser() {
		System.out.println("业务层：查询用户");
		return userDao.getAllUser();
	}

	@Override
	public void insertUser(User user){
		System.out.println("业务层：注册用户");
		userDao.insertUser(user);
	}

	@Override
	public boolean login(User user) {
		System.out.println("业务层：用户登录");
		if(userDao.login(user) == null){
			System.out.println("false");
			return false;
		}else{
			System.out.println("true");
			return true;
		}
	}
}
