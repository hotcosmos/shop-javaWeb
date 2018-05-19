package com.hotcosmos.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hotcosmos.domain.Category;
import com.hotcosmos.domain.PageBean;
import com.hotcosmos.domain.Product;
import com.hotcosmos.service.ProductService;
import com.hotcosmos.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;

/**
 * 抽取和商品相关的servlet
 * @author Administrator
 * @date 2018年5月19日
 */
public class ProductServlet extends BaseServlet {

	private static final long serialVersionUID = -559398686161165220L;
	
	/**
	 * 获取首页中动态获取的相关商品信息，显示在页面中
	 * @author Administrator
	 * @date 2018年5月15日
	 */
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
	
	/**
	 * 1.根据商品的pid在数据库中获取相关信息，用于在页面中显示商品详细信息
	 * 2.使用cookie记录访问的商品详细信息，用于商品列表页显示已经浏览过的商品(访问历史)
	 * @author Administrator
	 * @date 2018年5月17日
	 */
	public void productInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

	/**
	 * 1.根据商品分类，获取相关商品，并进行分页，每页12条数据
	 * 2.获取cookie数据，获取pids相关的商品，将其显示在已浏览的商品列表中
	 * @author Administrator
	 * @date 2018年5月17日
	 */
	public void productListByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

	/**
	 * 从redis缓存或数据库中获取商品分类信息，将其显示在导航条中
	 * @author Administrator
	 * @date 2018年5月16日
	 */
	public void categoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
}