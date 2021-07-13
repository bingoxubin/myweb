package com.bingoabin.controller.test;

import com.bingoabin.controller.service.IUserService;
import com.bingoabin.controller.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xubin03
 * @date 2021/3/23 8:34 下午
 */
public class SpringTest {
	@Test
	public void Test(){
		//加载配置文件
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
		//获取对象
		IUserService us = (UserService) ac.getBean("userService");
		//调用方法
		us.findUsers();
	}
}
