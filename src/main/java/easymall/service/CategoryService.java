package easymall.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import easymall.po.Category;
import easymall.pojo.MyCategory;

public interface CategoryService {

//	List<String> allcategories();

	public List<Category> catelist();
	
	public String catesave(MyCategory mycategory, HttpServletRequest request);

	public Category cateInfo(String name);

	public String change(Category category, HttpServletRequest request);

	public void delcate(String name);

}
