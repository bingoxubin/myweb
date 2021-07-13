package com.bingoabin.controller.controller;

import com.bingoabin.controller.service.IUserService;
import com.bingoabin.controller.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author xubin03
 * @date 2021/3/22 8:35 下午
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;

	@RequestMapping("/findUser")
	public String findUsers(Model model) {
		System.out.println("表现层：查询用户");
		List<User> list = userService.findUsers();
		model.addAttribute("list", list);
		return "User";
	}

	@RequestMapping("/insert")
	public String insert(User user) {
		System.out.println("注册");
		userService.insertUsers(user);
		return "success";
	}

	@RequestMapping("/login")
	public String login(User user) {
		System.out.println("登录");
		if (userService.login(user)) {
			return "successlogin";
		} else {
			return "falselogin";
		}
	}
}
