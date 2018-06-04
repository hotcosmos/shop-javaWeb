package com.hotcosmos.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	// `oid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	// `ordertime` datetime(0) DEFAULT NULL,
	// `total` double DEFAULT NULL,
	// `state` int(11) DEFAULT NULL,
	// `address` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT
	// NULL,
	// `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
	// `telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT
	// NULL,
	// `uid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
	private String oid; // 订单编号
	private Date ordertime; // 订单生成时间
	private double total; // 订单总计
	private int state; // 订单支付状态 1:已支付 0:未支付
	private String address; // 收货地址
	private String name; // 收货人
	private String telephone; // 收货人联系方式
	private User user; // 订单属于哪个用户

	private List<OrderItem> orderItem = new ArrayList<OrderItem>();  //存储订单中的所有订单项

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(List<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}

}
