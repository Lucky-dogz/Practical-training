package easymall.controller.admin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import easymall.po.Products;

import easymall.service.ProductsService;


@Controller("chartController")
@RequestMapping("/chart")
public class ChartController extends HttpServlet{
	
	@Autowired
	private ProductsService productsService;
	

	
	@RequestMapping("/barPie")
	public String barPie() {
		return "admin/barAndpie";
	}
	
	@RequestMapping("/getPie")
	public void getPie(HttpServletResponse response) {
		
		int num1=productsService.getNumViaCategory(1);
		
		int num2=productsService.getNumViaCategory(2);
		
		int num3=productsService.getNumViaCategory(3);
		
		int num4=productsService.getNumViaCategory(4);
		
		int num5=productsService.getNumViaCategory(5);
		
		int num6=productsService.getNumViaCategory(6);
	
		int num7=productsService.getNumViaCategory(7);
		List<Integer> list=new ArrayList<Integer>();
		list.add(num1);
		list.add(num2);
		list.add(num3);
		list.add(num4);
		list.add(num5);
		list.add(num6);
		list.add(num7);
		 
		try {
			response.getWriter().print(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
