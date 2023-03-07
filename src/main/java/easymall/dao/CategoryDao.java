package easymall.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import easymall.po.Category;

@Repository("categoryDao")
@Mapper
public interface CategoryDao {

//	public List<String> allcategories();
	
	public List<Category> catelist();

	public void catesave(Category category);

	public Category cateInfo(String name);

	public void change(Category category);

	public void delcate(String name);

}
