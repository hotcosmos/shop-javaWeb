package com.hotcosmos.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 购物车
 * 
 * @author Administrator
 * @date 2018年5月31日
 */
public class Cart {
	private Map<String, CartItem> cartItem = new HashMap<String, CartItem>(); // 封装n个购物车项对象
	private double total; // 当前购物车内价格总计

	public Map<String, CartItem> getCartItem() {
		return cartItem;
	}

	public void setCartItem(Map<String, CartItem> cartItem) {
		this.cartItem = cartItem;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
