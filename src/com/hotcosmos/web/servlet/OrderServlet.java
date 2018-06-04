package com.hotcosmos.web.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hotcosmos.domain.Cart;
import com.hotcosmos.domain.CartItem;
import com.hotcosmos.domain.Order;
import com.hotcosmos.domain.OrderItem;
import com.hotcosmos.domain.User;
import com.hotcosmos.service.OrderService;
import com.hotcosmos.utils.CommonUtils;

/**
 * 订单模块
 * 
 * @author Administrator
 * @date 2018年6月3日
 */
public class OrderServlet extends BaseServlet {

	private static final long serialVersionUID = -8246403032007404638L;

	/**
	 * 通过购物车提交订单
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void submitOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取session
		HttpSession session = request.getSession();

		// 检查用户是否登录，未登录不能提交订单
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		// 获取session中的购物车 cart
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			return;
		}

		// 封装数据: order实体
		Order order = new Order();
		// 1. private String oid; // 订单编号
		order.setOid(CommonUtils.getUUID());
		// 2. private Date ordertime; // 订单生成时间
		Date parse = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			parse = format.parse(format.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		order.setOrdertime(parse);
		// 3. private double total; // 订单总计
		order.setTotal(cart.getTotal());
		// 4. private int state; // 订单支付状态 1:已支付 0:未支付
		order.setState(0);
		// 5. private String address; // 收货地址
		order.setAddress(null);
		// 6. private String name; // 收货人
		order.setName(null);
		// 7. private String telephone; // 收货人联系方式
		order.setTelephone(null);
		// 8. private User user; // 订单属于哪个用户
		order.setUser(user);
		// 9. private List<OrderItem> orderItem = new ArrayList<OrderItem>(); 存储订单中的所有订单项
		// (1) 获取购物车中的所有购物车项
		Map<String, CartItem> cartItem = cart.getCartItem();
		if (cartItem == null) {
			return;
		}
		// (2) 遍历所有的购物车项，将其封装在订单项中
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for (Map.Entry<String, CartItem> cartItems : cart.getCartItem().entrySet()) {
			OrderItem orderItem = new OrderItem();
			// <1> private String itemid; // 订单项id
			orderItem.setItemid(CommonUtils.getUUID());
			// <2> private int count; // 商品数量
			orderItem.setCount(cartItems.getValue().getBuyNum());
			// <3> private double subtotal; // 订单项中商品小计
			orderItem.setSubtotal(cartItems.getValue().getSubTotal());
			// <4> private Product product; // 订单项中的商品对象
			orderItem.setProduct(cartItems.getValue().getProduct());
			// <5> private Order order; // 订单项属于哪个订单
			orderItem.setOrder(order);
			// 将订单项封装到List集合中
			orderItemList.add(orderItem);
		}
		// (3) 将订单项封装到订单中
		order.setOrderItem(orderItemList);
		
		//将数据传送到service层进行存储
		OrderService orderService = new OrderService();
		boolean isSuccess = orderService.submitOrder(order);
		if(isSuccess) {
			//订单提交成功，将order封装到session中，跳转到order_info.jsp页面
			session.setAttribute("order", order);
			response.sendRedirect(request.getContextPath() + "/order_info.jsp");
		}else {
			//给出错误提示信息
			request.setAttribute("errorInfo", "发生未知错误，订单提交失败，请稍后重试");
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		}
	}
	
	/**
	 * 确认订单
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}












