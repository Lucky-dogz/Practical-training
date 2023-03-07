package easymall.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import easymall.dao.CartDao;
import easymall.dao.OrderDao;
import easymall.dao.OrderItemDao;
import easymall.dao.ProductsDao;
import easymall.po.OrderItem;
import easymall.po.Orders;
import easymall.pojo.MyCart;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private ProductsDao productsDao;
	@Autowired
	private CartDao cartDao;
	@Autowired
	private OrderItemDao orderItemDao;
	@Autowired
	private OrderDao orderDao;
	
	@Override
	@Transactional
	public void addOrder(String cartIds, Orders myOrder) {
		String[] arrCartIds=cartIds.split(",");
		Double sum=0.0;
		for (String cartID:arrCartIds) {
			Integer cid=Integer.parseInt(cartID);
			MyCart mycart=cartDao.findByCartID(cid);
			String pid=mycart.getPid();
			int buynum=mycart.getNum();
			
			Double price=mycart.getPrice();
			sum+=buynum*price;
			OrderItem orderItem=new OrderItem();
			orderItem.setOrder_id(myOrder.getId());
			orderItem.setProduct_id(pid);
			orderItem.setBuynum(buynum);
			orderItemDao.addOrderItem(orderItem);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("pid", pid);
			map.put("buynum", buynum);
			productsDao.updateSoldNum(map);
			cartDao.delCart(cid);
		}
		myOrder.setMoney(sum);
		orderDao.addOrder(myOrder);
	}

	@Override
	public List<Orders> findOrderByUserId(Integer user_id) {
		
		return orderDao.findOrderByUserId(user_id);
	}

	@Override
	public List<OrderItem> orderitem(String order_id) {
		
		return orderItemDao.orderitem(order_id);
	}

	@Override
	public void delorder(String id) {
		List<OrderItem> orderItems=orderItemDao.orderitem(id);
		for (OrderItem orderItem:orderItems) {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("pid", orderItem.getProduct_id());
			map.put("buynum", -orderItem.getBuynum());
			productsDao.updateSoldNum(map);
		}
		orderItemDao.delorderitem(id);
		orderDao.delorder(id);
	}

	@Override
	public void payorder(String id) {

		orderDao.payorder(id);
	}

	// 鍚庡彴绠＄悊
	@Override
	public List<Orders> orderlist() {		
		return orderDao.orderlist();
	}

	@Override
	public void sendorder(String id) {
		orderDao.sendorder(id);
	}

	@Override
	public void yesgetPro(String id) {
		orderDao.yesgetPro(id);
		
	}

}
