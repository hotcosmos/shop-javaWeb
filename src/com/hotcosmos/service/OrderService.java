package com.hotcosmos.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hotcosmos.dao.OrderDao;
import com.hotcosmos.domain.Order;
import com.hotcosmos.utils.DataSourceUtils;

/**
 * 订单模块
 * @author Administrator
 * @date 2018年6月3日
 */
public class OrderService {

	/**
	 * 通过购物车提交订单
	 * @param order
	 * @return
	 */
	public boolean submitOrder(Order order) {
		OrderDao orderDao = new OrderDao();
		int row1 = 0;
		int row2 = 0;
		
		try {
			//开启事务
			DataSourceUtils.startTransaction();
			//插入orders表数据
			row1 = orderDao.insertOrder(order);
			//插入orderItem表数据
			row2 = orderDao.insertOrderItem(order.getOrderItem());
		} catch (SQLException e) {
			//事务回滚
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return row1>0 && row2>0 ? true:false;
	}
	
	/**
	 * 确认订单----1.更新收货人信息，
	 * @param order
	 * @return
	 */
	public boolean updateOrder(Order order) {
		OrderDao orderDao = new OrderDao();
		int row = 0;
		try {
			row = orderDao.updateOrder(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row>0?true:false;
	}

	/**
	 * 修改order表中 订单的支付状态
	 * @param r6_Order
	 */
	public void updateOrderState(String r6_Order) {
		OrderDao orderDao = new OrderDao();
		try {
			orderDao.updateOrderState(r6_Order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户的全部订单列表
	 * @param uid
	 * @return
	 */
	public List<Order> getOrderListByUid(String uid) {
		OrderDao orderDao = new OrderDao();
		List<Order> orderList = null;
		try {
			orderList = orderDao.getOrderListByUid(uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	/**
	 * 获取订单中的订单项和商品信息
	 * @param oid
	 * @return
	 */
	public List<Map<String, Object>> getOrderItemMapListByOid(String oid) {
		OrderDao orderDao = new OrderDao();
		List<Map<String, Object>> mapList = null;
		try {
			mapList = orderDao.getOrderItemMapListByOid(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapList;
	}

}











