package com.hotcosmos.domain;

/**
 * 购物车项
 * 
 * @author Administrator
 * @date 2018年5月31日
 */
public class CartItem {
	private Product product; // 商品对象
	private int buyNum; // 购买数量
	private double subTotal; // 小计

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
}
