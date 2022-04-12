package com.bingoabin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xubin03
 * @date 2021/4/12 1:15 上午
 */
@RestController
public class HelloController {
	@RequestMapping("/hello")
	public String hello() {
		return "my helloworld";
	}
}
