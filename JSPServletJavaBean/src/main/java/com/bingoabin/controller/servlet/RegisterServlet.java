package com.bingoabin.controller.servlet;

import com.bingoabin.controller.bean.User;
import com.bingoabin.controller.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xubin03
 * @date 2021/3/22 10:06 上午
 */
// @WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");

		//获取用户注册信息
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String sex = req.getParameter("sex");
		String question = req.getParameter("question");
		String answer = req.getParameter("answer");
		String email = req.getParameter("email");

		//实例化UserDao对象
		boolean flag = new UserDao().userExist(username);
		if (!flag) {
			UserDao userDao = new UserDao();
			//实例化一个User对象
			User user = new User();
			//对用户对象的属性赋值
			user.setUsername(username);
			user.setPassword(password);
			user.setSex(sex);
			user.setQuestion(question);
			user.setAnswer(answer);
			user.setEmail(email);
			userDao.saveUser(user);
			req.setAttribute("info", "注册成功！ <br>");
		} else {
			req.setAttribute("info", "此用户一存在！<br>");
		}
		//转发到message.jsp页面
		req.getRequestDispatcher("message.jsp").forward(req, resp);
	}
}

