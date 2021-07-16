package com.bingoabin.springboot.controller;

import com.bingoabin.springboot.domain.User;
import com.bingoabin.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author xubin03
 * @date 2021/3/23 9:50 下午
 */
@Controller
public class UserController {
	@Autowired
	IUserService userService;

	@RequestMapping("/findUser")
	public String findUser(Model model) {
		System.out.println("表现层：查询用户");
		List<User> list = userService.getAllUser();
		model.addAttribute("list", list);
		System.out.println(list);
		return "index";
	}

	@RequestMapping("/insert")
	public String insert(User user) {
		System.out.println("表现层：注册用户");
		userService.insertUser(user);
		return "success";
	}

	@RequestMapping("/login")
	public String login(User user) {
		System.out.println("表现层：用户登录");
		// 调用注入的 usersService 调用 login 方法
		if (userService.login(user)) {
			return "successlogin";
		} else {
			return "falselogin";
		}
	}
}
