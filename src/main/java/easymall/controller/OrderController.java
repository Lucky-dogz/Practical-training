package easymall.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import easymall.service.CartService;
import easymall.service.OrderService;
import easymall.service.ProductsService;
import easymall.po.OrderItem;
import easymall.po.Orders;
import easymall.po.User;
import easymall.pojo.MyCart;
import easymall.pojo.OrderInfo;
import easymall.po.Products;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductsService productsService;

	@RequestMapping("/order_add")
	public String order_add(String cartIds, Model model) {

		String[] arrCartIds = cartIds.split(",");
		List<MyCart> carts = new ArrayList<MyCart>();
		for (String cid : arrCartIds) {
			Integer cartID = Integer.parseInt(cid);
			carts.add(cartService.findByCartID(cartID));
		}
		model.addAttribute("carts", carts);
		model.addAttribute("cartIds", cartIds);
		return "order_add";
	}

	@RequestMapping("/addOrder")
	public String addOrder(String receiverinfo, String cartIds, HttpSession session) {
		String orderId = UUID.randomUUID().toString();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		Timestamp timestamp = Timestamp.valueOf(time);
		User user = (User) session.getAttribute("user");
		Orders myOrder = new Orders(orderId, 0.0, receiverinfo, 0, 0, timestamp, user.getId());

		orderService.addOrder(cartIds, myOrder);
		return "forward:/order/showorder";
	}

	@RequestMapping("/showorder")
	public String showorder(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<OrderInfo> orderInfoList = findOrderInfoByUserId(user.getId());
		model.addAttribute("orderInfos", orderInfoList);
		return "order_list";
	}

	private List<OrderInfo> findOrderInfoByUserId(Integer userId) {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		List<Orders> orderList = orderService.findOrderByUserId(userId);

		for (Orders order : orderList) {
			List<OrderItem> orderItems = orderService.orderitem(order.getId());
			Map<Products, Integer> map = new HashMap<Products, Integer>();
			for (OrderItem orderItem : orderItems) {
				Products product = productsService.oneProduct(orderItem.getProduct_id());
				map.put(product, orderItem.getBuynum());
			}
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(order);
			orderInfo.setMap(map);

			orderInfoList.add(orderInfo);
		}
		return orderInfoList;
	}

	@RequestMapping("/payorder")
	public String payorder(String id) {
		orderService.payorder(id);
		return "forward:/order/showorder";
	}

	@RequestMapping("/yesgetPro")
	public String yesgetPro(String id) {
		orderService.yesgetPro(id);
		return "forward:/order/showorder";
	}
	
	@RequestMapping("/delorder")
	public String delorder(String id) {
		orderService.delorder(id);
		return "forward:/order/showorder";

	}
}
