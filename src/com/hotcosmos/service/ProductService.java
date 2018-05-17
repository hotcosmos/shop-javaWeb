package com.hotcosmos.service;

import java.sql.SQLException;
import java.util.List;

import com.hotcosmos.dao.ProductDao;
import com.hotcosmos.domain.Category;
import com.hotcosmos.domain.PageBean;
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
	
	/**
	 * 根据商品分类，获取相关商品信息，并将其封装在PageBean中进行分页
	 * @param cid  分类id
	 * @return  PageBean分页
	 */
	public PageBean<Product> getProductListByCategory(String cid,String currentPageStr) {
		ProductDao productDao = new ProductDao();
		PageBean<Product> pageBean = new PageBean<Product>();
		//封装数据：private int currentPage; // 当前页
		int currentPage = 1;
		if(currentPageStr != null) {
			currentPage = Integer.parseInt(currentPageStr);
		}
		pageBean.setCurrentPage(currentPage);
		//封装数据：private int currentCount; // 当前页显示条数
		int currentCount = 12;
		pageBean.setCurrentCount(currentCount);
		//封装数据：private int totalCount; // 此分类总条数
		int totalCount = 0;
		try {
			totalCount = productDao.getTotalCountProductListByCategory(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		//封装数据：private int totalPage; // 从分类总页数
		int totalPage = 1;
		if(totalCount != 0) {
			totalPage =  (int) Math.ceil(1.0*(totalCount / currentCount));
		}
		pageBean.setTotalPage(totalPage);
		//封装数据：private List<T> list;  //商品信息
		int index = (currentPage - 1) * currentCount;  //起始索引
		List<Product> list = null;
		try {
			list = productDao.getProductListByCategory(cid,index,currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setList(list);
		return pageBean;
	}

}
