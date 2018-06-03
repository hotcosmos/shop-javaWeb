package com.hotcosmos.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.hotcosmos.domain.User;
import com.hotcosmos.service.UserService;
import com.hotcosmos.utils.CommonUtils;
import com.hotcosmos.utils.MD5Utils;
import com.hotcosmos.utils.MailUtils;

/**
 * 抽取和用户注册和登录等相关的servlet
 * 
 * @author Administrator
 * @date 2018年5月19日
 */
public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 7180533456735528850L;

	/**
	 * 用户注册：封装数据，发送激活邮件
	 * 
	 * @author Administrator
	 * @date 2018年5月15日
	 */
	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 解决中文乱码问题
		request.setCharacterEncoding("UTF-8");
		// 接收页面提交数据
		Map<String, String[]> properties = request.getParameterMap();
		User user = new User();
		try {
			// 定义一个类型转换器（BeanUtils中的ConvertUtils），将birthday的String类型转为Date类型
			ConvertUtils.register(new Converter() {
				@Override
				public Object convert(@SuppressWarnings("rawtypes") Class clazz, Object value) {
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
		//对密码进行加密
		user.setPassword(MD5Utils.md5(user.getPassword()));
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
			// 发送激活邮件
			String emailMsg = "<h3>恭喜您注册成功！</h3><br />请点击下面这个链接进行&nbsp;&nbsp;<a href='http://127.0.0.1:8080/shop/user?methodName=active&activeCode="
					+ activeCode + "'>用户激活</a>";
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

	/**
	 * 用户注册之后进入邮件激活，接收用户激活码，将数据库中用户状态改为激活
	 * 
	 * @author Administrator
	 * @date 2018年5月15日
	 */
	public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 解决中文乱码问题
		request.setCharacterEncoding("UTF-8");
		// 接收页面提交的激活码
		String activeCode = request.getParameter("activeCode");

		// 将激活码传送到dao层进行激活
		UserService userService = new UserService();
		userService.actice(activeCode);

		// 激活成功后跳转到登录页面
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	/**
	 * 在用户注册时，检查用户名或邮箱是否已经存在
	 * 
	 * @author Administrator
	 * @date 2018年5月15日
	 */
	public void isExistField(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 获取需要判断的字段名
		String isExistField = request.getParameter("isExistField");
		// 获取需要判断的提交的值
		String isExistValue = request.getParameter("isExistValue");
		String field = null;
		switch (isExistField) {
		case "username":
			field = isExistField;
			break;
		case "email":
			field = isExistField;
			break;
		default:
			break;
		}
		if (field != null) {
			UserService userService = new UserService();
			boolean isExist = userService.isExistField(field, isExistValue);
			String json = "{\"isExist\":" + isExist + "}";
			response.getWriter().write(json);
		} else {
			String json = "{\"isExist\":true}";
			response.getWriter().write(json);
		}
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) {
		//获取用户名和密码
		String username = request.getParameter("username");
		String password = MD5Utils.md5(request.getParameter("password"));
		
		if(username == null || password == null) {
			return;
		}
		//将用户名和密码传送到service层判断用户名和密码是否存在
		UserService userService = new UserService();
		User user =  userService.login(username,password);
		if(user != null) {
			//将用户信息存储到session中
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			//跳转到首页
			try {
				response.sendRedirect(request.getContextPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			//登录失败，给出提示信息
			request.setAttribute("errorInfo", "用户名或密码不正确");
			try {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 */
	public void quit(HttpServletRequest request, HttpServletResponse response) {
		//从session中删除user
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return;
		}
		session.removeAttribute("user");
		//跳转到登录页
		try {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}











