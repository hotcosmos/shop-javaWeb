package com.hotcosmos.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotcosmos.domain.PageBean;
import com.hotcosmos.domain.Product;
import com.hotcosmos.service.ProductService;

/**
 * 1.根据商品分类，获取相关商品，并进行分页，每页12条数据
 * 2.获取cookie数据，获取pids相关的商品，将其显示在已浏览的商品列表中
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
		
		//获取cookie中的数据
		Cookie[] cookies = request.getCookies();
		List<Product> productList = new ArrayList<Product>();
		if(cookies != null) {
			//判断是否存在pids
			for(Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())) {
					//获取pids中的数据
					String pids = cookie.getValue();
					//截取字符串
					String[] split = pids.split("/");
					//从数据库中动态获取pid相关的商品详细信息
					for(String pid : split) {
						Product product = productService.getProductInfoByPid(pid);
						//组合为list集合
						productList.add(product);
					}
				}
			}
		}
		
		//将数据封装到request域中进行转发
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		request.setAttribute("productList", productList);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}