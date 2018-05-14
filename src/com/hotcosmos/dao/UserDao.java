package com.hotcosmos.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.hotcosmos.domain.User;

public class UserDao {

	public int register(User user) {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		Object[] param = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		int row = 0;
		try {
			row = queryRunner.update(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}

}
