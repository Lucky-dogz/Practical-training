package easymall.controller.admin;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import easymall.po.Category;
import easymall.pojo.MyCategory;
import easymall.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class CategoryControllerAdmin {
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/catelist")
	public String catelist(Model model){
		List<Category> categories = categoryService.catelist();
		model.addAttribute("categories", categories);
		return "/admin/cate_list";
	}
	
	@RequestMapping("/addcate")
	public String addcate(Model model){
		model.addAttribute("category",new MyCategory());
		return "/admin/add_cate";
	}
	
	@RequestMapping("/catesave")
	public String catesave(@Valid @ModelAttribute MyCategory mycategory,
			HttpServletRequest request,Model model){
		String msg = categoryService.catesave(mycategory,request);
		model.addAttribute("msg", msg);
		return "redirect:/admin/addcate";
	}
	
	// 进入修改界面
	@RequestMapping("/changecate")
	public String changecate(Model model, HttpServletRequest request, String name){
		Category category = categoryService.cateInfo(name);
		// 获取之前的category值
		model.addAttribute("category",category);
		// 用新的值
		model.addAttribute("categories",new Category());
		return "/admin/upd_cate";
	}

		// 修改中
		@RequestMapping("/change")
		public String change(@Valid @ModelAttribute Category category, 
				HttpServletRequest request, Model model) throws Exception{
			String msg = categoryService.change(category, request);
			model.addAttribute("msg",msg);
			return "redirect:/admin/catelist";
		}
		
		// 删除
		@RequestMapping("/delcate")
		public String delcate(String name){
			categoryService.delcate(name);
			return "redirect:/admin/catelist";
		}
	
}
