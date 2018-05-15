package com.hotcosmos.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotcosmos.service.UserService;

public class IsExistFieldServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//获取需要判断的字段名
		String isExistField = request.getParameter("isExistField");
		//获取需要判断的提交的值
		String isExistValue = request.getParameter("isExistValue");
		String field = null;
		switch(isExistField) {
			case "username":
				field = isExistField;
				break;
			case "email":
				field = isExistField;
				break;
			default:
				break;
		}
		if(field != null) {
			UserService userService = new UserService();
			boolean isExist = userService.isExistField(field,isExistValue);
			String json = "{\"isExist\":"+isExist+"}";
			response.getWriter().write(json);
		}else {
			String json = "{\"isExist\":true}";
			response.getWriter().write(json);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}