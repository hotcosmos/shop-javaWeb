package com.hotcosmos.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.hotcosmos.domain.Order;
import com.hotcosmos.domain.OrderItem;
import com.hotcosmos.utils.DataSourceUtils;

public class OrderDao {

	public int insertOrder(Order order) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Connection conn = DataSourceUtils.getConnection();
		int row = queryRunner.update(conn, sql, order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());
		return row;
	}

	public int insertOrderItem(List<OrderItem> orderItem) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Connection conn = DataSourceUtils.getConnection();
		int row = 0;
		int rows= 1;
		for(OrderItem orderItems : orderItem) {
			row = queryRunner.update(conn, sql, orderItems.getItemid(),orderItems.getCount(),orderItems.getSubtotal(),orderItems.getProduct().getPid(),orderItems.getOrder().getOid());
			if(row == 0) {
				rows = 0;
			}
		}
		return rows>0?1:0;
	}

}
