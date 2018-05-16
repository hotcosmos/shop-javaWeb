package com.hotcosmos.service;

import java.sql.SQLException;
import java.util.List;

import com.hotcosmos.dao.ProductDao;
import com.hotcosmos.domain.Product;

public class ProductService {
	/**
	 * 获取最热商品 9个
	 * @return
	 */
	public List<Product> getHotProductList() {
		ProductDao productDao = new ProductDao();
		List<Product> hotProductList = null;
		try {
			hotProductList = productDao.getHotProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotProductList;
	}
	/**
	 * 获得最新商品 9个
	 * @return
	 */
	public List<Product> getNewProductList() {
		ProductDao productDao = new ProductDao();
		List<Product> newProductList = null;
		try {
			newProductList = productDao.getNewProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newProductList;
	}

}
