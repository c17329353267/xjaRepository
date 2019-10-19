package com.jd.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jd.dao.ProductsDao;
import com.jd.entity.Products;
import com.jd.entity.ResultModel;

@Repository
public class ProductDaoImpl implements ProductsDao {

	@Autowired
	private HttpSolrServer httpsolrServer;

	@Override
	public ResultModel queryProducts(SolrQuery solrQuery) throws Exception {

		// 创建查询响应对象
		QueryResponse response = httpsolrServer.query(solrQuery);
		// 获取查询document文档对象
		SolrDocumentList documentList = response.getResults();

		// 创建接受商品集合
		List<Products> lists = new ArrayList<Products>();

		// 创建分页pojo对象
		ResultModel resultModel = new ResultModel();

		if (documentList != null) {

			// 获取总记录数给分页赋值
			resultModel.setRecordCount(documentList.getNumFound());
			// 循环遍历每一个document对象
			for (SolrDocument doc : documentList) {
				// 创建商品对象
				Products products = new Products();
				// 给商品pojo的id赋值
				products.setPid(String.valueOf(doc.get("id")));

				// 处理搜索名称为高亮状态
				Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
				if (highlighting != null) {

					// 获取高良数据的name数据
					List<String> list = highlighting.get(doc.get("id")).get("products_name");
					if (list != null && list.size() > 0) {
						// 有高亮的名字
						products.setName(list.get(0));
					} else {
						// 没有高亮的名字products.setName(String.valueOf(doc.get("products_name")));
					}
				} else {
					// 没有高亮数据名字products.setName(String.valueOf(doc.get("products_name")));
				}
				// 判断价格，防止转换异常
				if (doc.get("products_price") != null && !"".equals(doc.get("products_price"))) {
					products.setPrice(Float.valueOf(doc.get("products_price").toString()));
				}
				// 类别查询
				products.setCatalog_name(String.valueOf(doc.get("products_catalog_name")));
				// 图片查询products.setPicture(String.valueOf(doc.get("products_picture")));
				// 添加给商品集合
				lists.add(products);
			}
			// 分页数据赋值
			resultModel.setProductList(lists);
		}
		return resultModel;
	}

}
