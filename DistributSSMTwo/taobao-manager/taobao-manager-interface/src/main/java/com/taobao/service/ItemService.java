package com.taobao.service;

import java.util.List;

import com.taobao.pojo.EasyUIDataGridResult;
import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;

public interface ItemService {

	TbItem QueryItemById(long id);
	//查询所有
	EasyUIDataGridResult queryPage(int page,int rows);
	//删除
	TaoBaoResult deleteByIds(long[] ids);
	//添加
	TaoBaoResult insertItem(TbItem tbItem,String desc);
	//加载商品描述信息
	TaoBaoResult loadItemDesc(long id);
	
	TaoBaoResult query(long id);
	//商品下架
	TaoBaoResult lowerShelf(long[] ids);
	//商品上架
	TaoBaoResult onShelf(long[] ids);
	//商品修改
	TaoBaoResult updateItem(TbItem tbItem);
	//查询商品详情
	TbItemDesc queryTbItemdescById(long id);
}
