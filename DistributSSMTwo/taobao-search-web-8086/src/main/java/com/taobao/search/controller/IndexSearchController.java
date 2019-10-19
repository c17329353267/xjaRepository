package com.taobao.search.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.pojo.SearchSolrResults;
import com.taobao.search.service.SearchService;

@Controller
public class IndexSearchController {

	@Reference
	private SearchService searchService;
	@RequestMapping("search")
	public String indexSearch(@RequestParam(value="q")String q,
			@RequestParam(value="page",defaultValue="1") int page,Model model) throws Exception{
		SearchSolrResults search = searchService.search(q, page);
		model.addAttribute("itemList", search.getListSearchItems());
		return "search";
	}
}
