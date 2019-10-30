package com.taobao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.taobao.mapper.TbItemCatMapper;
import com.taobao.pojo.EasyUITreeNode;
import com.taobao.pojo.TbItemCat;
import com.taobao.pojo.TbItemCatExample;
import com.taobao.pojo.TbItemCatExample.Criteria;
import com.taobao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	@Autowired
	private TbItemCatMapper itemCatMapper;
	//查看当前id是否是父节点
	@Override
	public List<EasyUITreeNode> queryIsParent(long id) {
		// TODO Auto-generated method stub
		TbItemCatExample example = new TbItemCatExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(id);//条件是parentid
		createCriteria.andStatusEqualTo(1);//此类商品存在
		List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
		List<EasyUITreeNode> trees =  new ArrayList<>();
		for (TbItemCat tbItemCat : tbItemCats) {
			//为每一个tree赋值
			EasyUITreeNode treeNode= new EasyUITreeNode();
			treeNode.setId(tbItemCat.getId());
			treeNode.setText(tbItemCat.getName());
			treeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			trees.add(treeNode);
		}		
		return trees;  
	}
}
