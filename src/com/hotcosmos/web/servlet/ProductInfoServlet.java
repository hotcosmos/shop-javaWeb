package com.hotcosmos.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotcosmos.domain.Product;
import com.hotcosmos.service.ProductService;

/**
 * 根据商品的pid在数据库中获取相关信息，用于在页面中显示商品详细信息
 * @author Administrator
 * @date 2018年5月17日
 */
public class ProductInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String pid = request.getParameter("pid");
		
		ProductService productService = new ProductService();
		Product product = productService.getProductInfoByPid(pid);
		
		request.setAttribute("product", product);
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}