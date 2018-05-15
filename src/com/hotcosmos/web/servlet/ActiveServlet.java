package com.hotcosmos.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotcosmos.service.UserService;

public class ActiveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 解决中文乱码问题
		request.setCharacterEncoding("UTF-8");
		//接收页面提交的激活码
		String activeCode = request.getParameter("activeCode");
		
		//将激活码传送到dao层进行激活
		UserService userService = new UserService();
		userService.actice(activeCode);
		
		//激活成功后跳转到登录页面
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}