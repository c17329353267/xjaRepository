package com.taobao.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.pojo.EasyUIDataGridResult;
import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;
import com.taobao.pojo.TbItemParamItem;
import com.taobao.service.ItemParamService;
import com.taobao.service.ItemService;

@RestController
@RequestMapping("/item/")
public class ItemController {
	//远程调用Service
	@Reference
	private ItemService itemService;
	@Reference
	private ItemParamService ItemParamService;
	@RequestMapping("queryByid/{id}")
	public TbItem queryById(@PathVariable("id") long id){
		//System.out.println("ItemController queryById id:"+id);
		
		return itemService.QueryItemById(id);
	}
	
	@RequestMapping("query/{id}")
	public TaoBaoResult query(@PathVariable("id") long id){
		System.out.println("ItemController query id:"+id);
		
		return itemService.query(id);
	}
	@RequestMapping("list")
	public EasyUIDataGridResult queryPage(@RequestParam("page") int page,@RequestParam("rows") int rows){
		//System.out.println("queryPage");
		return itemService.queryPage(page, rows);
	}
	//删除
	@RequestMapping("delete")
	public TaoBaoResult deleteItems(long[] ids){
		itemService.deleteByIds(ids);
		return TaoBaoResult.ok();
	}
	
	//商品添加
	@RequestMapping("save")
	public TaoBaoResult saveItems(TbItem tbItem,@RequestParam("desc")String desc){
		return itemService.insertItem(tbItem, desc);
	}
	//修改商品信息
	@RequestMapping("update")
	public TaoBaoResult updateItems(TbItem tbItem){
		//System.out.println("update-tbItem="+tbItem);
		itemService.updateItem(tbItem);
		return TaoBaoResult.ok();
	}
	
	//加载商品描述信息
	@RequestMapping("desc/{id}")
	public TaoBaoResult loadDesc(@PathVariable("id") long id){
		//System.out.println("loadDesc:"+id);
		return itemService.loadItemDesc(id);
	}
	//下架
	@RequestMapping("instock")
	public TaoBaoResult lowershelf(long[] ids){
		itemService.lowerShelf(ids);
		return TaoBaoResult.ok();
	}
	//上架
	@RequestMapping("reshelf")
	public TaoBaoResult onShelf(long[] ids){
		itemService.onShelf(ids);
		return TaoBaoResult.ok();
	}
	
}
