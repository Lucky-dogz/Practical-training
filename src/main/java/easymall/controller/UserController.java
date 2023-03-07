package easymall.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import easymall.po.User;
import easymall.service.UserService;

@Controller("userController")
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping("/login")
	public String login(User user,HttpSession session,Model model){
		User user1=userService.login(user.getUsername());
		if(user1==null){
			model.addAttribute("message","您输入的用户名错误");
			return "login";	
		}
		else if(user1.getPassword().equals(user.getPassword())){
			session.setAttribute("user",user1);
			return "redirect:/index.jsp";
		}
		else{
			model.addAttribute("message","您输入的密码错误");
			return "login";
		}
	}
	@RequestMapping("/checkUser")
	public void checkUser(String username,HttpServletResponse response) throws IOException{
		if(userService.login(username)==null){
			response.getWriter().print("恭喜您，用户名可以使用!");
		}else{
			response.getWriter().print("用户名已被使用!");
		}
	}
	
	@RequestMapping("/regist")
	public String regist(User user,String valistr,HttpSession session,Model model){
		if("".equals(user.getUsername()) ||  user.getUsername()==null){
			model.addAttribute("msg","请输入用户名！");
		}
		if("".equals(user.getPassword()) ||  user.getPassword()==null){
			model.addAttribute("msg","请输入密码！");
		}
		if(!valistr.equals(session.getAttribute("code").toString())){
			model.addAttribute("msg","验证码输入错误！");
		}
		if(userService.regist(user)>0){
			session.setAttribute("user", user);
			model.addAttribute("msg","注册成功！");
			return "login";
		}
		else{
			model.addAttribute("msg","注册失败！");
		}
		return "regist";
		
	}
		
}
