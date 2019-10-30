package com.taobao.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.content.service.ItemCaontentService;
import com.taobao.pojo.EasyUITreeNode;
import com.taobao.pojo.TaoBaoResult;

@RestController
@RequestMapping("content/category/")
public class ItemContentCategoryController {
	@Reference
	private ItemCaontentService contenService;

	@RequestMapping("list")
	public List<EasyUITreeNode> queryByParentId(@RequestParam(value = "id", defaultValue = "0") long id) {
		// System.out.println("in ItemContentCategoryController
		// queryByParentId");
		return contenService.queryByParentId(id);
	}

	// 创建分类
	@RequestMapping("create")
	public TaoBaoResult createNode(@RequestParam("parentId") long parentId, @RequestParam("name") String nodeName) {
		return contenService.insertContentCategory(parentId, nodeName);

	}

	// 编辑
	@RequestMapping("update")
	public void updateContentCategoryNodeName(@RequestParam("id") long id, @RequestParam("name") String name) {
		System.out.println("in updateContentCategoryNodeName");
		contenService.updateNodeName(id, name);
	}

	// 删除
	@RequestMapping("delete")
	public TaoBaoResult deleteContentCategoryNode(@RequestParam("id") long nodeId) {
		System.out.println("in deleteContentCategoryNode  ===nodeId==:" + nodeId);
		contenService.deleteByContentCategoryId(nodeId);
		return TaoBaoResult.ok();

	}
}
