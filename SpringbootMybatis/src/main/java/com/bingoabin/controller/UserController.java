package com.bingoabin.controller;

import com.bingoabin.entity.User;
import com.bingoabin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xubin34
 * @date 2021/7/16 11:43 上午
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/findall")
	List<User> findall() {
		System.out.println("findall");
		return userService.findAll();
	}

	@GetMapping("/findByid")
	User findByid() {
		int id = 1;
		return userService.findUserById(id);
	}

	@GetMapping("/addUser")
	boolean addUser() {
		User user = new User();
		user.setId(100);
		user.setUsername("asin");
		user.setPassword("asin_password");
		return userService.addUser(user);
	}

	@GetMapping("/deleteUserById")
	boolean deleteUserById() {
		int id = 1;
		return userService.deleteUserById(id);
	}

	@GetMapping("/updateUser")
	boolean updateUser() {
		User user = new User();
		user.setId(100);
		user.setUsername("root");
		user.setPassword("root_password");
		return userService.updateUser(user);
	}
}