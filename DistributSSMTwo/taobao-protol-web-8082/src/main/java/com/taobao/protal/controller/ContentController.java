package com.taobao.protal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.oracle.util.JsonUtils;
import com.taobao.content.service.ItemContentManagService;
import com.taobao.pojo.AdNode;

@Controller
public class ContentController {

	//把配置文件的分类id注入进来
	@Value("${big_advertisement}")
	private long contentCategoryid; 
	@Reference
	private ItemContentManagService contentManagService;
	//根据分类进行查询
	@RequestMapping("index")
	public String queryByCategory(Model model){
		List<AdNode> adNodes = contentManagService.queryByCategoryId(contentCategoryid);
		String objectToJson = JsonUtils.objectToJson(adNodes);
		//将查询的数据回显到前台界面
		model.addAttribute("ad1", objectToJson);
		return "index";
	}
}
