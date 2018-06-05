package com.hotcosmos.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

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

	/**
	 * 确认订单----1.更新收货人信息，
	 * @param order
	 * @return
	 * @throws SQLException 
	 */
	public int updateOrder(Order order) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set address=?,name=?,telephone=? where oid=?";
		int row =  queryRunner.update(sql, order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
		return row;
	}

	/**
	 * 修改order表中 订单的支付状态
	 * @param r6_Order
	 * @throws SQLException 
	 */
	public void updateOrderState(String r6_Order) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set state=1 where oid=?";
		queryRunner.update(sql,r6_Order);
	}

	/**
	 * 获取用户的全部订单列表
	 * @param uid
	 * @return
	 * @throws SQLException 
	 */
	public List<Order> getOrderListByUid(String uid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from orders where uid=?";
		return queryRunner.query(sql, new BeanListHandler<Order>(Order.class), uid);
	}

	/**
	 * 获取订单中的订单项和商品信息
	 * @param oid
	 * @return
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> getOrderItemMapListByOid(String oid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orderitem i,product p where i.pid=p.pid and i.oid=?";
		return queryRunner.query(sql, new MapListHandler(), oid);
	}
}








