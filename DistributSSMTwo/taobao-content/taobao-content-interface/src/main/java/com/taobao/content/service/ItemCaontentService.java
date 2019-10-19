package com.taobao.content.service;

import java.util.List;

import com.taobao.pojo.AdNode;
import com.taobao.pojo.EasyUITreeNode;
import com.taobao.pojo.TaoBaoResult;

public interface ItemCaontentService {

	//查询内容
	List<EasyUITreeNode> queryByParentId(long parent);
	//插入分类 传入插入节点父节点id,插入节点的名称
	TaoBaoResult insertContentCategory(long parentId,String nodeName);
	//编辑 传入当前节点的id,当前节点的修改后的名称
	void updateNodeName(long nodeId,String nodeName);
	//删除
	void deleteByContentCategoryId(long id);
}
