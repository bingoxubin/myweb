package com.bingoabin.mapper;

import com.bingoabin.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper//指定这是一个操作数据库的mapper
public interface UserMapper {
	List<User> findAll();

	User findUserById(int id);

	boolean addUser(User user);

	boolean deleteUserById(int id);

	boolean updateUser(User user);
}
