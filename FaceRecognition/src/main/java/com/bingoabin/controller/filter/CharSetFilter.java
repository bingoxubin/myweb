package com.bingoabin.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

//过滤器，过滤所有外部的请求
@WebFilter("/*")
public class CharSetFilter implements Filter {
	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8");
		chain.doFilter(request, response);
	}
}
