package com.hotcosmos.service;

import java.sql.SQLException;

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

}