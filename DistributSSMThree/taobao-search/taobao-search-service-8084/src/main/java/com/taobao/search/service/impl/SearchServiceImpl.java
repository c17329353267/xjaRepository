package com.taobao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.taobao.pojo.SearchSolrResults;
import com.taobao.search.dao.SearchSolrDao;
import com.taobao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{
	
	private static final Integer PAGE_SIZE=60;
	
	@Autowired
	private SearchSolrDao SearchSolrDao;
	@Override
	public SearchSolrResults search(String search, int page) throws Exception {
		// 创建query查询对象
		SolrQuery solrQuery = new SolrQuery();
		//设置查询域名
		solrQuery.set("df", "item_keywords");
		//设置查询参数
		solrQuery.setQuery(search);
		//设置查询分页
		solrQuery.setStart((page-1)*PAGE_SIZE);
		solrQuery.setRows(PAGE_SIZE);
		//设置高亮显示
		solrQuery.setHighlight(true);
		//设置高亮显示域
		solrQuery.addHighlightField("item_title");
		//设置显示规则
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		solrQuery.setHighlightSimplePost("</span>");
		//System.out.println("SearchSolrDao="+SearchSolrDao);
		//SearchSolrResults searchSolrResults = 
		return SearchSolrDao.querySearch(solrQuery);
	}

}
