package com.hotcosmos.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.hotcosmos.domain.User;
import com.hotcosmos.utils.DataSourceUtils;

public class UserDao {
	/**
	 * 用户注册方法
	 * @param user
	 * @return
	 */
	public int register(User user) {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
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
	/**
	 * 使用邮件激活码激活用户
	 * @param activeCode
	 */
	public void active(String activeCode) {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set state=? where code=?";
		try {
			queryRunner.update(sql, 1,activeCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 这是用来ajax异步判断表单提交的用户名或邮箱是否存在的方法
	 * @param field
	 * @param isExistValue
	 * @return
	 * @throws SQLException 
	 */
	public Long isExistField(String field, String isExistValue) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = null;
		if(field.equals("username")) {
			sql = "select count(*) from user where username=?";
		}else {
			sql = "select count(*) from user where email=?";
		}
		Long query =(Long) queryRunner.query(sql,new ScalarHandler(),isExistValue);
		return query;
	}

}
