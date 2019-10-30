package com.taobao.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.pojo.EasyUITreeNode;
import com.taobao.service.ItemCatService;
/**
 * 返回json数据
 * @author mdlge
 *
 */
@RestController
@RequestMapping("/cat/")
public class ItemCatController {

	@Reference
	private ItemCatService itemCatService;
	
	@RequestMapping("queryByParentId")
	public List<EasyUITreeNode> queryByParentId(@RequestParam(value="id",defaultValue="1") long id){
		//System.out.println("in queryByParentId");
		return itemCatService.queryIsParent(id);
	}
}
