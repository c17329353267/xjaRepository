package com.jd.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.dao.ProductsDao;
import com.jd.entity.ResultModel;
import com.jd.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {

	private static final Integer PAGE_SIZE = 60;

	@Autowired
	private ProductsDao dao;

	public ResultModel query(String queryString, String catalog_name, String price, Integer page, String sort) throws Exception {

	 //创建query对象
	 SolrQuery solrQuery = new SolrQuery();
	 //默认查询域名
	 solrQuery.set("df", "product_keywords");
	
	 //判断是否根据名称查询
	 if (queryString !=null && !"".equals(queryString)) {
	
	 solrQuery.setQuery(queryString);
	 }else {
	 solrQuery.setQuery("*:*");
	 }
	 //判断是否根据类型查询
	 if (catalog_name !=null && !"".equals(catalog_name)) {
	 solrQuery.addFilterQuery("products_catalog_name:"+catalog_name);
	 }
	 //判断是否根据价格查询
	 if (price !=null && !"".equals(price)) {
	 String[] split = price.split("-");
	 if (split !=null && split.length>0) {
	
	 solrQuery.addFilterQuery("products_price:["+split[0]+" TO "+split[1]+ "]");
	}

}

	//判断是否排序
	if ("1".equals(sort)) {
	solrQuery.addSort("products_price", ORDER.asc);
	}else {
	solrQuery.addSort("products_price", ORDER.desc);
	}
	
	//分页
	if (page ==null) {
	page=1;
	}
	Integer start = (page-1)*PAGE_SIZE;
	solrQuery.setStart(start);
	solrQuery.setRows(PAGE_SIZE);
	
	solrQuery.setHighlight(true);
	solrQuery.addHighlightField("products_name");
	solrQuery.setHighlightSimplePre("<span style='color:red'>");
	solrQuery.setHighlightSimplePost("</span>");
	//调用dao数据
	ResultModel resultModel = dao.queryProducts(solrQuery);
	//拿到当前页
	resultModel.setCurPage(Long.parseLong(page.toString()));
	//计算总页数
	Long pageCount = resultModel.getRecordCount() / PAGE_SIZE;
	if (resultModel.getRecordCount() % PAGE_SIZE >0) {
	pageCount++;
	}
	resultModel.setPageCount(pageCount);
	return resultModel;
	}
}
