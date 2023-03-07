package easymall.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import easymall.po.Cart;
import easymall.pojo.MyCart;
import easymall.po.User;
import easymall.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController{

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/addCart")
	public String addCart(String pid,String buyNum,HttpSession session){
		User user=(User)session.getAttribute("user");
		int num=Integer.parseInt(buyNum);
		Cart cart = new Cart(user.getId(),pid,num);
		Cart _cart=cartService.findCart(cart);
		if(_cart==null){
			cartService.addCart(cart);
		}else{
			cart.setCartID(_cart.getCartID());
			cartService.updateCart(cart);
		}
		return "forward:/cart/showcart";
	}
	
	@RequestMapping("/showcart")
	public String showcart(HttpSession session,Model model){
		User user=(User)session.getAttribute("user");
		List<MyCart> carts=cartService.showcart(user.getId());
		model.addAttribute("carts",carts);
		return "cart";
	}
	
	@RequestMapping("/updateBuyNum")
	public void updateBuyNum(Integer cartID,Integer buyNum){
		Cart newcart=new Cart();
		newcart.setCartID(cartID);
		newcart.setNum(buyNum);
		cartService.updateBuyNum(newcart);
	}
	
	@RequestMapping("/delCart")
	public void delCart(Integer cartID){		
		cartService.delCart(cartID);
	}
}
