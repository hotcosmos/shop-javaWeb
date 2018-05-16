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

public class CategoryListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//从数据库中获取分类的信息
		ProductService productService = new ProductService();
		List<Category> categoryList = productService.getCategoryList();
		
		//将数据转为json格式字符串
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		
		//将json字符串写会页面中
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}