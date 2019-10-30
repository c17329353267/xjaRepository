package com.jd.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.jd.entity.ResultModel;

public interface ProductsDao {

	public ResultModel queryProducts(SolrQuery solrQuery) throws Exception;

}