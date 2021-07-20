package com.bingoabin.springboot.controller;

/**
 * @author xubin34
 * @date 2021/7/20 11:13 上午
 */

import com.bingoabin.springboot.domain.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/index")
public class LoginController {

	//GET 单个值
	@RequestMapping(value = "/home")
	public String home() {
		return "index home";
	}

	//GET 一个参数
	@GetMapping(value = "/{name}")
	public String index(@PathVariable String name) {
		return "oh you are " + name + "<br> nice to meet you";// \n不起作用了,那就直接用html中的标签吧
	}

	//POST 请求
	@RequestMapping(value = "/testpost", method = RequestMethod.POST)
	public String testpost() {
		System.out.println("hello  test post");
		return "ok";
	}

	//GET 两个参数
	@GetMapping(value = "/login/{name}&{pwd}")
	public String login(@PathVariable String name, @PathVariable String pwd) {
		if (name.equals("admin") && pwd.equals("admin")) {
			return "hello welcome admin";
		} else {
			return "oh sorry user name or password is wrong";
		}
	}

	//通过 GET请求
	@RequestMapping(value = "/loginbyget", method = RequestMethod.GET)
	public String loginByGet(@RequestParam(value = "name", required = true) String name,
	                         @RequestParam(value = "pwd", required = true) String pwd) {
		System.out.println("hello get");
		return name + " " + pwd;
	}

	//通过 POST请求
	@RequestMapping(value = "/loginbypost", method = RequestMethod.POST)
	public String loginByPost(@RequestParam(value = "name", required = true) String name,
	                          @RequestParam(value = "pwd", required = true) String pwd) {
		System.out.println("hello post");
		return name + " " + pwd;
	}

	//参数为一个bean对象.spring会自动为我们关联映射
	@RequestMapping(value = "/loginbypost1", method = {RequestMethod.POST, RequestMethod.GET})
	public String loginByPost1(User user) {
		if (null != user) {
			return user.getUsername() + " " + user.getPASSWORD();
		} else {
			return "error";
		}
	}

	//请求内容是一个json串,spring会自动把他和我们的参数bean对应起来,不过要加@RequestBody注解
	@RequestMapping(value = "/loginbypost2", method = {RequestMethod.POST, RequestMethod.GET})
	public String loginByPost2(@RequestBody User user) {
		if (null != user) {
			return user.getUsername() + " " + user.getPASSWORD();
		} else {
			return "error";
		}
	}
}
