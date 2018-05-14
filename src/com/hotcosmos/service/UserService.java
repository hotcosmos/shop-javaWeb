package com.hotcosmos.service;

import com.hotcosmos.dao.UserDao;
import com.hotcosmos.domain.User;

public class UserService {

	public boolean register(User user) {
		UserDao userdao = new UserDao();
		int row = userdao.register(user);
		return row>0?true:false;
	}

}
