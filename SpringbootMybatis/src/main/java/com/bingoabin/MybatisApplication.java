package com.bingoabin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xubin34
 * @date 2021/7/16 11:36 上午
 */
@SpringBootApplication
@MapperScan("com.bingoabin.mapper")//使用MapperScan批量扫描所有的Mapper接口；
public class MybatisApplication {

	// http://localhost:8081/user/findall
	// http://localhost:8081/user/findByid
	// http://localhost:8081/user/addUser
	// http://localhost:8081/user/deleteUserById
	// http://localhost:8081/user/updateUser
	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}
}
