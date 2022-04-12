package com.bingoabin.controller.controller;

import com.bingoabin.controller.bean.Result;
import com.bingoabin.controller.bean.User;
import com.bingoabin.controller.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/v1/user/count")
public class CountServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.处理请求
		//接收传来的参数
		String face_id = request.getParameter("face_id");

		//将传来的数据进行校验，封装
		// User user = new User();
		// user.setFace_id(face_id);
		// user.setCity(city);
		//将封装后的参数，传递给service，进行数据库的存储
		User user = UserService.count(face_id);
		//2.在这里响应
		//根据存储的结果准备不同的响应内容
		Result result = null;
		if (user != null) {
			result = new Result(user, 0, "更新成功");
		} else {
			result = new Result(-1, "添加失败");
		}
		//进行内容的json格式转换
		String json = result.toString();
		//将内容响应给小程序
		response.getWriter().append(json);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
