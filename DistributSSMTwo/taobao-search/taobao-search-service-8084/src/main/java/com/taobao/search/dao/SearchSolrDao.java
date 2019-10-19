package com.taobao.search.dao;

import org.apache.solr.client.solrj.SolrQuery;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.taobao.pojo.SearchSolrResults;

public interface SearchSolrDao {

	SearchSolrResults querySearch(SolrQuery search) throws Exception;
}
