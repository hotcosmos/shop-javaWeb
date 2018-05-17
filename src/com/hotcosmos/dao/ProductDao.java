package com.hotcosmos.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.hotcosmos.domain.Category;
import com.hotcosmos.domain.Product;
import com.hotcosmos.utils.DataSourceUtils;

/**
 * 商品相关的数据库操作
 * @author Administrator
 * @date 2018年5月15日
 */
public class ProductDao {

	/**
	 * 获得最热商品 9个
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Product> getHotProductList() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot=? limit ?,?";
		List<Product> hotProductList = queryRunner.query(sql, new BeanListHandler<Product>(Product.class), 1, 0, 9);
		return hotProductList;
	}

	/**
	 * 获得最新商品 9个
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Product> getNewProductList() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate desc limit ?,?";
		List<Product> newProductList = queryRunner.query(sql, new BeanListHandler<Product>(Product.class), 0, 9);
		return newProductList;
	}

	/**
	 * 获得分类信息
	 * @return
	 * @throws SQLException
	 */
	public List<Category> getcategoryList() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		List<Category> categoryList = queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
		return categoryList;
	}

}
