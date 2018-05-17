package com.hotcosmos.service;

import java.sql.SQLException;
import java.util.List;

import com.hotcosmos.dao.ProductDao;
import com.hotcosmos.domain.Category;
import com.hotcosmos.domain.Product;

/**
 * 商品相关的业务逻辑
 * @author Administrator
 * @date 2018年5月15日
 */
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
	
	/**
	 * 获取商品分类信息
	 * @return
	 */
	public List<Category> getCategoryList() {
		ProductDao productDao = new ProductDao();
		List<Category> categoryList = null;
		try {
			categoryList = productDao.getcategoryList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

}
