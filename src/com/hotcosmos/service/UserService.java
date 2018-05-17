package com.hotcosmos.service;

import java.sql.SQLException;

import com.hotcosmos.dao.UserDao;
import com.hotcosmos.domain.User;

/**
 * 用户相关的业务逻辑
 * @author Administrator
 * @date 2018年5月15日
 */
public class UserService {
	/**
	 * 这是用于用户注册的方法
	 * @param user
	 * @return
	 */
	public boolean register(User user) {
		UserDao userdao = new UserDao();
		int row = userdao.register(user);
		return row>0?true:false;
	}
	/**
	 * 这是用于邮件激活的方法
	 * @param activeCode
	 */
	public void actice(String activeCode) {
		UserDao userdao = new UserDao();
		userdao.active(activeCode);
	}
	/**
	 * 这是用来ajax异步判断表单提交的用户名或邮箱是否存在的方法
	 * @param field  判断的是哪个字段（用户名或邮箱）
	 * @param isExistValue  判断的值
	 * @return
	 */
	public boolean isExistField(String field, String isExistValue) {
		UserDao userdao = new UserDao();
		Long row = 0L;
		try {
			row = userdao.isExistField(field,isExistValue);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row>0?true:false;
	}

}
