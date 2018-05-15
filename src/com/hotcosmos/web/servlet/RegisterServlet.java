package com.hotcosmos.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.hotcosmos.domain.User;
import com.hotcosmos.service.UserService;
import com.hotcosmos.utils.CommonUtils;
import com.hotcosmos.utils.MailUtils;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 解决中文乱码问题
		request.setCharacterEncoding("UTF-8");
		// 接收页面提交数据
		Map<String, String[]> properties = request.getParameterMap();
		User user = new User();
		try {
			// 定义一个类型转换器（BeanUtils中的ConvertUtils），将birthday的String类型转为Date类型
			ConvertUtils.register(new Converter() {
				@Override
				public Object convert(Class clazz, Object value) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date parse = null;
					try {
						parse = format.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return parse;
				}
			}, Date.class); // 第二个参数Date.class指定需要转换的字段
			// 将提交的数据封装到user实体中
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		// private String uid;
		user.setUid(CommonUtils.getUUID());
		// private String telephone;
		user.setBirthday(null);
		// private String state;
		user.setState(0);
		// private String code;
		String activeCode = CommonUtils.getUUID();
		user.setCode(activeCode);

		// 将user传送到service层--dao层进行存储
		UserService userService = new UserService();
		boolean isRegisterSuccess = userService.register(user);

		// 判断是否激活成功
		if (isRegisterSuccess) {
			//发送激活邮件
			String emailMsg = "<h3>恭喜您注册成功！</h3><br />请点击下面这个链接进行&nbsp;&nbsp;<a href='http://127.0.0.1:8080/shop/active?activeCode="+activeCode+"'>用户激活</a>";
			try {
				MailUtils.sendMail(user.getEmail(), "用户激活--网上商城", emailMsg);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			// 注册成功，跳转到注册成功页面
			response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
		} else {
			// 注册失败，跳转到注册失败页面
			response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}