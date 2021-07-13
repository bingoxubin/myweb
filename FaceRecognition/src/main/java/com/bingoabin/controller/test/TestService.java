package com.bingoabin.controller.test;

import com.bingoabin.controller.bean.User;
import com.bingoabin.controller.service.UserService;

/**
 * @author xubin03
 * @date 2021/4/11 2:43 上午
 */
public class TestService {
	public static void main(String[] args){
	    User user = new User();
	    user.setFace_id("128");
	    user.setCity("杭州");
		UserService.insert(user);

		UserService.count("128");

		User user1 = new User();
		user1.setUsername("老王");
		user1.setDescription("锄禾日当午，汗滴禾下土");
		UserService.updateUserByFaceId("128", user1);
	}
}
