package com.taobao.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.content.service.ItemContentManagService;
import com.taobao.pojo.EasyUIDataGridResult;
import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbContent;

@RestController
@RequestMapping("content")
public class ItemContentManagerController {

	@Reference
	private ItemContentManagService contentManagService;
	
	@RequestMapping("list")
	public EasyUIDataGridResult quertContentByCategory(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="20")int rows,@RequestParam("categoryId")long categoryId){
		return contentManagService.queryByPageCategoryId(page, rows, categoryId);
	}
	
	@RequestMapping("save")
	public TaoBaoResult insertTbContent(TbContent tbContent){
		
		return contentManagService.insertTbContent(tbContent);
	}
}
