package com.bingoabin.service;

import com.bingoabin.entity.User;
import com.bingoabin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xubin34
 * @date 2021/7/16 11:42 上午
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Override
	public List<User> findAll() {
		return userMapper.findAll();
	}

	@Override
	public User findUserById(int id) {
		return userMapper.findUserById(id);
	}

	@Override
	public boolean addUser(User user) {
		return userMapper.addUser(user);
	}

	@Override
	public boolean deleteUserById(int id) {
		return userMapper.deleteUserById(id);
	}

	@Override
	public boolean updateUser(User user) {
		return userMapper.updateUser(user);
	}
}
