package com.hotcosmos.domain;

public class OrderItem {
	// `itemid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	// `count` int(11) DEFAULT NULL,
	// `subtotal` double DEFAULT NULL,
	// `pid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
	// `oid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
	private String itemid; // 订单项id
	private int count; // 商品数量
	private double subtotal; // 订单项中商品小计
	private Product product; // 订单项中的商品对象
	private Order order; // 订单项属于哪个订单

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
