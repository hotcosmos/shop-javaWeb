package com.hotcosmos.web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotcosmos.domain.Product;
import com.hotcosmos.service.ProductService;

/**
 * 1.根据商品的pid在数据库中获取相关信息，用于在页面中显示商品详细信息
 * 2.使用cookie记录访问的商品详细信息，用于商品列表页显示已经浏览过的商品(访问历史)
 * @author Administrator
 * @date 2018年5月17日
 */
public class ProductInfoServlet extends HttpServlet {

	private static final long serialVersionUID = -5418467119004016947L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取数据
		request.setCharacterEncoding("UTF-8");
		String pid = request.getParameter("pid");
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");
		
		//根据商品的pid在数据库中获取相关信息
		ProductService productService = new ProductService();
		Product product = productService.getProductInfoByPid(pid);
		
		//获取页面的cookie数据
		Cookie[] cookies = request.getCookies();
		String pids = pid;
		//判断是否已经存在cookie并且cookie名是pids
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())) {
					//获取给pids中存的值  1/2/3
					pids = cookie.getValue();
					//根据 / 截取字符串到linkedList集合中，方便操作
					String[] split = pids.split("/");
					List<String> asList = Arrays.asList(split);
					LinkedList<String> linkedList = new LinkedList<String>(asList);
					//判断是否有已经存在的，有则将其删除，将最新访问的商品pid插入到最前方
					if(linkedList.contains(pid)) {
						linkedList.remove(pid);
					}
					linkedList.addFirst(pid);
					//遍历集合 ，将数据拼为字符长，以 / 隔开，总共记录最新的7个记录即可
					StringBuffer stringBuffer = new StringBuffer();
					for(int i = 0; i < linkedList.size() && i < 7; i++) {
						stringBuffer.append(linkedList.get(i));
						stringBuffer.append("/");
					}
					//去掉最后一个 / 符号, 便是新的 pids
					pids = stringBuffer.substring(0, stringBuffer.length() - 1);
					
				}
			}
		}
		//将pids写入Cookie中
		Cookie newCookie = new Cookie("pids",pids);
		response.addCookie(newCookie);
		
		//将数据写入request域中进行转发
		request.setAttribute("product", product);
		request.setAttribute("cid", cid);
		request.setAttribute("currentPage", currentPage);
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}