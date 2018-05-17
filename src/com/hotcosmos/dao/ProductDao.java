package com.hotcosmos.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

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
	
	/**
	 * 根据分类获取商品信息并分页，获取当前分类的相关商品总条数
	 * @param cid
	 * @return
	 * @throws SQLException 
	 */
	public int getTotalCountProductListByCategory(String cid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid=?";
		Long query = (Long) queryRunner.query(sql, new ScalarHandler(), cid);
		return query.intValue();
	}

	/**
	 * 根据分类获取商品信息并分页, 获取每页的商品列表
	 * @param cid  商品分类
	 * @param index  当前页的起始索引
	 * @param currentCount  当前页的终止索引
	 * @return
	 * @throws SQLException
	 */
	public List<Product> getProductListByCategory(String cid, int index, int currentCount) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid=? limit ?,?";
		List<Product> ProductList = queryRunner.query(sql, new BeanListHandler<Product>(Product.class), cid, index,currentCount);
		return ProductList;
	}

	/**
	 * 根据商品的pid在数据库中获取相关信息
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public Product getProductInfoByPid(String pid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid=?";
		Product product = queryRunner.query(sql, new BeanHandler<Product>(Product.class), pid);
		return product;
	}

}
