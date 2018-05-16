package com.hotcosmos.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotcosmos.domain.Product;
import com.hotcosmos.service.ProductService;

public class IndexServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductService productService = new ProductService();
		
		//获得最热商品信息9个
		List<Product> hotProductList = productService.getHotProductList();
		//获得最新商品信息9个
		List<Product> newProductList = productService.getNewProductList();
		
		//将获得到的数据封装到request域中进行转发
		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}