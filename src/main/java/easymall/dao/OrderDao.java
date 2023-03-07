package easymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import easymall.po.Orders;

@Repository("orderDao")
@Mapper
public interface OrderDao {
	void addOrder(Orders myOrder);
	List<Orders> findOrderByUserId(Integer user_id);
	void delorder(String id);
	void payorder(String id);
//	鍚庡彴淇敼
	List<Orders> orderlist();
	void sendorder(String id);
	void yesgetPro(String id);

}
