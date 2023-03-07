package easymall.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import easymall.po.Products;
import easymall.pojo.MyProducts;

public interface ProductsService {
	//查找商品类别
	public List<String> allcategories();
	//查找商品
	public List<Products> prodlist(Map<String,Object> map);
	
	//查找商品详情
	public Products prodInfo(String pid);
	
	//分类查找商品
	public List<Products> prodclass(String proclass);
	
	public Products oneProduct(String product_id);
	public String save(MyProducts myproducts, HttpServletRequest request);
	public List<Products> allprods();
	public void updateSaleStatus(Map<String, Object> map);
	
//	echart
	public Integer getNumViaCategory(Integer category);
	
	public void delprod(String id);
	public String update(Products products, HttpServletRequest request);
	
}
