package easymall.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import easymall.dao.CategoryDao;
import easymall.po.Category;
import easymall.pojo.MyCategory;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;

//	@Override
//	public List<String> alcategoryDaolcategories() {
//		return categoryDao.allcategories();
//	}

	@Override
	public String catesave(MyCategory mycategory, HttpServletRequest request) {
		Category category = new Category();
		category.setName(mycategory.getName());
		category.setDescription(mycategory.getDescription());
		categoryDao.catesave(category);
		return "分类添加成功";
	}

	@Override
	public List<Category> catelist() {
		
		return categoryDao.catelist();
	}

	@Override
	public Category cateInfo(String name) {
		return categoryDao.cateInfo(name);
	}

	@Override
	public String change(Category category, HttpServletRequest request) {
		categoryDao.change(category);
		return "分类修改成功";
	}

	@Override
	public void delcate(String name) {
		categoryDao.delcate(name);
	}

}
