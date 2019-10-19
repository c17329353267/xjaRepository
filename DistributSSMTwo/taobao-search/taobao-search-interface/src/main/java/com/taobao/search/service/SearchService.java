package com.taobao.search.service;

import com.taobao.pojo.SearchSolrResults;

public interface SearchService {

	SearchSolrResults search(String search,int page) throws Exception;
}
