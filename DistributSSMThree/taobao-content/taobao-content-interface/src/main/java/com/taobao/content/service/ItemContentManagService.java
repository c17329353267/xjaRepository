package com.taobao.content.service;

import java.util.List;

import com.taobao.pojo.AdNode;
import com.taobao.pojo.EasyUIDataGridResult;
import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbContent;

public interface ItemContentManagService {
	//内容管理查询
	EasyUIDataGridResult queryByPageCategoryId(int page,int rows,long cateGoryId);
	
	//轮播图的查询
	List<AdNode> queryByCategoryId(long categoryId);
	
	//新增轮播图
	TaoBaoResult insertTbContent(TbContent tbcontent);
}
