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

		// ������ѯ��Ӧ����
		QueryResponse response = httpsolrServer.query(solrQuery);
		// ��ȡ��ѯdocument�ĵ�����
		SolrDocumentList documentList = response.getResults();

		// ����������Ʒ����
		List<Products> lists = new ArrayList<Products>();

		// ������ҳpojo����
		ResultModel resultModel = new ResultModel();

		if (documentList != null) {

			// ��ȡ�ܼ�¼������ҳ��ֵ
			resultModel.setRecordCount(documentList.getNumFound());
			// ѭ������ÿһ��document����
			for (SolrDocument doc : documentList) {
				// ������Ʒ����
				Products products = new Products();
				// ����Ʒpojo��id��ֵ
				products.setPid(String.valueOf(doc.get("id")));

				// ������������Ϊ����״̬
				Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
				if (highlighting != null) {

					// ��ȡ�������ݵ�name����
					List<String> list = highlighting.get(doc.get("id")).get("products_name");
					if (list != null && list.size() > 0) {
						// �и���������
						products.setName(list.get(0));
					} else {
						// û�и���������products.setName(String.valueOf(doc.get("products_name")));
					}
				} else {
					// û�и�����������products.setName(String.valueOf(doc.get("products_name")));
				}
				// �жϼ۸񣬷�ֹת���쳣
				if (doc.get("products_price") != null && !"".equals(doc.get("products_price"))) {
					products.setPrice(Float.valueOf(doc.get("products_price").toString()));
				}
				// ����ѯ
				products.setCatalog_name(String.valueOf(doc.get("products_catalog_name")));
				// ͼƬ��ѯproducts.setPicture(String.valueOf(doc.get("products_picture")));
				// ��Ӹ���Ʒ����
				lists.add(products);
			}
			// ��ҳ���ݸ�ֵ
			resultModel.setProductList(lists);
		}
		return resultModel;
	}

}
