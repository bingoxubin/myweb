package com.bingoabin.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xubin03
 * @date 2021/4/11 10:07 下午
 */
@RestController
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);
	//http://localhost:8080/hello
	@RequestMapping("/hello")
	public String hello(){
		logger.info("im info");
		logger.debug("im debug");
		logger.error("im error");
		return "hello world!";
	}
}
