package com.hotcosmos.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.hotcosmos.domain.User;
import com.hotcosmos.service.UserService;
import com.hotcosmos.utils.CommonUtils;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//解决中文乱码问题
		request.setCharacterEncoding("UTF-8");
		//接收页面提交数据
		Map<String, String[]> properties = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		//private String uid;
		user.setUid(CommonUtils.getUUID());
		//private String telephone;
		user.setBirthday(null);
		//private String state;
		user.setState(0);
		//private String code;
		user.setCode(CommonUtils.getUUID());
		
		//将user传送到service层--dao层进行存储
		UserService userService = new UserService();
		boolean isRegisterSuccess = userService.register(user);
		
		//判断是否激活成功
		if(isRegisterSuccess) {
			
		}else {
			
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}