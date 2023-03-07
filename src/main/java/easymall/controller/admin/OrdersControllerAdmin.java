package easymall.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import easymall.po.OrderItem;
import easymall.po.Orders;
import easymall.service.OrderService;
import easymall.service.ProductsService;

@Controller
@RequestMapping("/admin")
public class OrdersControllerAdmin {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductsService productsService;
	
	@RequestMapping("/orderlist")
	public String orderlist(Model model){
		List<Orders> orders = orderService.orderlist();
		model.addAttribute("orders", orders);
		return "/admin/order_list";
	}
	
	@RequestMapping("/sendorder")
	public String sendorder(String id,Model model){
		orderService.sendorder(id);
		return "redirect:/admin/orderlist";
	}
	
}
