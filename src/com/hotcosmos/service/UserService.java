package com.hotcosmos.service;

import com.hotcosmos.dao.UserDao;
import com.hotcosmos.domain.User;

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

}
