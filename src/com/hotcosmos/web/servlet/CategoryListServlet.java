package com.hotcosmos.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hotcosmos.domain.Category;
import com.hotcosmos.domain.Product;
import com.hotcosmos.service.ProductService;
import com.hotcosmos.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;

public class CategoryListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//创建Jedis对象连接redis数据库,并查看数据库中是否有对应的数据，有则直接使用，否则在mysql数据库中查找
		Jedis jedis = JedisPoolUtils.getJedis();
		String shop_categoryListJson = jedis.get("shop_categoryListJson");
		if(shop_categoryListJson == null) {
			//从数据库中获取分类的信息
			ProductService productService = new ProductService();
			List<Category> categoryList = productService.getCategoryList();
			
			//将数据转为json格式字符串
			Gson gson = new Gson();
			shop_categoryListJson = gson.toJson(categoryList);
			
			//将数据写入redis数据库中
			jedis.set("shop_categoryListJson", shop_categoryListJson);
		}
		
		//将json字符串写会页面中
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(shop_categoryListJson);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}