package easymall.controller.admin;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import easymall.controller.BaseController;
import easymall.po.Products;
import easymall.pojo.MyProducts;
import easymall.service.ProductsService;

@Controller
@RequestMapping("/admin")
public class ProductsControllerAdmin {
	@Autowired
	private ProductsService productsService;
	public  List<Products> products=new ArrayList<>();
	@RequestMapping("/prodlist")
	public String prodlist(Model model){				
		List<Products> products = productsService.allprods();
		//查询结果暴露给前端
		model.addAttribute("products",products);
		return "admin/prod_list";
	}
	@RequestMapping("/addprod")
	public String addprod(Model model){
		List<String> categories = productsService.allcategories();
		model.addAttribute("categories",categories);
		model.addAttribute("myproducts",new MyProducts());
		return "/admin/add_prod";
	}
	
	@RequestMapping("/save")
	public String save(@Valid @ModelAttribute MyProducts myproducts, 
			HttpServletRequest request, Model model)
			throws Exception {
		String msg=productsService.save(myproducts,request);
		model.addAttribute("msg",msg);
		return "redirect:/admin/prodlist";
	}
	@RequestMapping("/OnSale")
	public String OnSale(String id){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("status", 1);
		productsService.updateSaleStatus(map);
		return "redirect:/admin/prodlist";
	}
	@RequestMapping("/OffSale")
	public String OffSale(String id){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("status", 0);
		productsService.updateSaleStatus(map);
		return "redirect:/admin/prodlist";
	}
	
//	删除商品
	@RequestMapping("/delprod")
	public String delprod(String id) {
		productsService.delprod(id);
		return "redirect:/admin/prodlist";
	}

//	更改商品
	@RequestMapping("/updprod")
	public String updprod(Model model, HttpServletRequest request, String id) {
		// String id= request.getParameter("id");
		// System.out.println(id);
		// Map<String, Object> map=new HashMap<String,Object>();
		List<String> categories = productsService.allcategories();
		model.addAttribute("categories", categories);
		// model.addAttribute("product",map);
		Products product = productsService.prodInfo(id);
		model.addAttribute("product", product);
		model.addAttribute("products", new Products());
		// String id1=request.getParameter("id");
		// System.out.println(id1);
		return "/admin/upd_prod";
	}

//	更新
	@RequestMapping("/update")
	public String update(@Valid @ModelAttribute Products products, HttpServletRequest request, Model model)
			throws Exception {
		String msg = productsService.update(products, request);
		model.addAttribute("msg", msg);
		return "redirect:/admin/prodlist";
	}
	
	
//	销售榜单
	@RequestMapping("/prodlist2") 
	public String prodlist2(Model model) {
		//为搜索条件设置默认值，并检索条件是否合法
//		double _minPrice=0;
//		double _maxPrice=Double.MAX_VALUE;
//		//创建map,用于存放查询条件
//		Map<String, Object> map=new HashMap<>();
//		map.put("name","");
//		map.put("category","");
//		map.put("minPrice",_minPrice);
//		map.put("maxPrice", _maxPrice);
		//根据条件查询符合条件的商品信息
//		List<Products> products=productsService.allprods();
		products=productsService.allprods();
		
		//System.out.println(products.size());
		//查询结果暴露给前端
		Collections.sort(products);
		model.addAttribute("products",products);
		return "/admin/prod_list2";
	}
	
	
	
	/**
     * 导出数据
     * @return
     */
    @RequestMapping("/export")
    public String export(HttpServletResponse response){
 
        try {
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            StringBuffer fileNameBuffer = new StringBuffer("");
            fileNameBuffer.append("销售榜单").append(".xls");
            String filename = URLEncoder.encode(fileNameBuffer.toString(), "UTF-8");
            //定义导出信息
            //表名定义
            String shellNameObjects[] = {
                    "销售榜单"
            };
            //列名定义
            String shellHeaderObjects[][] = new String[][]{
                    {"商品名","商品单价", "销量"},
 
            };
            //创建工作表
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
            //创建表格名称
            HSSFSheet sheet0 = wb.createSheet(shellNameObjects[0]);
            //设置每行每列单元格大小
            sheet0.autoSizeColumn(1, true);
            //单独设置每列的宽度：这个非常有用，假设有些列内容过长时，就需要设置一定的长度显示内容了。不设置则使用默认宽度。
            sheet0.setColumnWidth(0 , 25 * 256);
            //sheet0.setColumnWidth(2 , 80 * 256);
            //创建（填充）表格标题（表格第一行）
            HSSFRow sheet0HeaderRow = sheet0.createRow(0);
            sheet0HeaderRow.setHeightInPoints(20);//目的是想把行高设置成20px
            for (int i = 0; i < shellHeaderObjects[0].length; i++) {
                HSSFCell cell = sheet0HeaderRow.createCell((short) i);
                cell.setCellValue(shellHeaderObjects[0][i]);
                cell.setCellStyle(style);
            }
   
            Collections.sort(products);
            
            //填充表格（单元格）内容
            if (null != products && products.size() > 0)
            {
                //从表格第二行开始填充，因为第一行已经填充标题了
                int index = 1;
                for (Products bean: products){
                    HSSFRow sheetRow = sheet0.createRow(index);
                    sheetRow.setHeightInPoints(30);//目的是想把行高设置成20px
                    sheetRow.createCell(0).setCellValue(bean.getName());
                    sheetRow.createCell(1).setCellValue(bean.getPrice());
                    sheetRow.createCell(2).setCellValue(bean.getSoldnum());
                    index++;
                }
            }
 
            // 回去输出流
            OutputStream out = response.getOutputStream();
            response.setHeader("Cache-Control", "private");
            response.setHeader("Pragma", "private");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Type", "application/force-download");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            wb.write(out);
            out.flush();
            out.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return null;
    }
}
