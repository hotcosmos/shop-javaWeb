package com.hotcosmos.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotcosmos.domain.PageBean;
import com.hotcosmos.domain.Product;
import com.hotcosmos.service.ProductService;

/**
 * 根据商品分类，获取相关商品，并进行分页，每页12条数据
 * @author Administrator
 * @date 2018年5月17日
 */
public class ProductListByCategoryServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");
		
		//根据cid获取数据
		ProductService productService = new ProductService();
		PageBean<Product> pageBean = productService.getProductListByCategory(cid,currentPage);
		
		//将数据封装到request域中进行转发
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}