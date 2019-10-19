package com.taobao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbItemParamItem;
import com.taobao.service.ItemParamService;

@Controller
@RequestMapping("/item/")
public class ItemParamController {

	@Reference
	private ItemParamService ItemParamService;
	
	//编辑  加载商品信息
		//编辑  加载商品信息
	@RequestMapping("edit")
	public String editShoppingNews(){
		
		
		return "item-edit";
	}
}
