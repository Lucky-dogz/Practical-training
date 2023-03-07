package easymall.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import easymall.po.Products;

@Repository
@Mapper
public interface ProductsDao {
	//查找商品类别
	public List<String> allcategories();
	//查找商品
	public List<Products> prodlist(Map<String,Object> map);
	
	//查找商品详情	
	public Products prodInfo(String pid);
	
	public List<Products> prodclass(String category);
	
	public Products oneProduct(String product_id);
	public Object findByImgurl(String imgurl);
	public void save(Products products);
	public List<Products> allprods();
	public void updateSaleStauts(Map<String, Object> map);
	public void updateSoldNum(Map<String, Object> map);
	
	public Integer getNumViaCategory(Integer category);
	public void delprod(String id);
	public void update(Products products);
}
