package com.bingoabin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xubin03
 * @date 2021/4/11 11:27 下午
 */
@Controller
public class JSPController {
	//http://localhost:8080/getindex   返回index，去找index.jsp文件，需要确保编译后target下面有index.jsp
	@RequestMapping("/getindex")
	public String index() {
		return "index"; //return 后面是页面名称
	}
}
