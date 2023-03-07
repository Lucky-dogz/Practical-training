package easymall.service;

import java.util.List;

import easymall.po.OrderItem;
import easymall.po.Orders;

public interface OrderService {
	public void addOrder(String cartIds,Orders myOrder);
	public List<Orders> findOrderByUserId(Integer user_id);
	public List<OrderItem> orderitem(String order_id);
	public void delorder(String id);
	public void payorder(String id);
	
	// 鍚庡彴璁㈠崟
	public List<Orders> orderlist();
	public void sendorder(String id);
	public void yesgetPro(String id);
}
