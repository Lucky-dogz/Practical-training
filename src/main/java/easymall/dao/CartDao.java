package easymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import easymall.po.Cart;
import easymall.pojo.MyCart;

@Repository
@Mapper
public interface CartDao {

	//查找购物车是否存在该商品
	public Cart findCart(Cart cart);
	//添加购物车
	public void addCart(Cart cart);
	//修改购物车商品数量
	public void updateCart(Cart cart);
	//显示购物车
	public List<MyCart> showcart(Integer user_id);
	//修改数量	
	public void updateBuyNum(Cart cart);
	//删除指定商品
	public void delCart(Integer cartID);

	//根据CartID查找购物车
	public MyCart findByCartID(Integer cartID);
}
