package com.hotcosmos.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 所有servlet的父类
 * 使用反射，抽取servlet中的公用代码：通过参数直接调用对应的方法
 * @author Administrator
 * @date 2018年5月19日
 */
public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 8751494782338933822L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//获得请求的methodName
		String methodName = request.getParameter("methodName");
		try {
			//获得当前被访问的servlet对象的字节码文件对象
			Class<? extends BaseServlet> clazz = this.getClass();
			//获得此字节码文件对象中的方法
			Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//执行相应的方法
			method.invoke(this, request,response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
}