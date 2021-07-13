package com.bingoabin.controller.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author xubin03
 * @date 2021/3/22 7:57 下午
 */
public class Test {
	public static void main(String[] args) throws Exception{
		//1.加载配置文件
		Properties pro = new Properties();
		//使用ClassLoader加载配置文件，获取字节输入流
		InputStream is = DataBaseUtil.class.getClassLoader().getResourceAsStream("druid.properties");
		pro.load(is);
		System.out.println(pro.getProperty("username"));
	}
}
