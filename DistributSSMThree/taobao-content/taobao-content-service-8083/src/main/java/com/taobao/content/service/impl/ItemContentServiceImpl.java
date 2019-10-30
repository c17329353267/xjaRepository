package com.taobao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.alibaba.dubbo.config.annotation.Service;
import com.taobao.content.service.ItemCaontentService;
import com.taobao.mapper.TbContentCategoryMapper;
import com.taobao.mapper.TbContentMapper;
import com.taobao.pojo.AdNode;
import com.taobao.pojo.EasyUITreeNode;
import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbContent;
import com.taobao.pojo.TbContentCategory;
import com.taobao.pojo.TbContentCategoryExample;
import com.taobao.pojo.TbContentCategoryExample.Criteria;

import redis.clients.jedis.JedisCluster;

import com.taobao.pojo.TbContentExample;
//import com.taobao.service.ItemService;

@Service
public class ItemContentServiceImpl implements ItemCaontentService {
	@Autowired
	private TbContentCategoryMapper cateGorymapper;

	
	@Override
	public List<EasyUITreeNode> queryByParentId(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);// 条件是parentid
		createCriteria.andStatusEqualTo(1);// 此类商品存在
		List<TbContentCategory> tbContentCategories = cateGorymapper.selectByExample(example);
		List<EasyUITreeNode> trees = new ArrayList<>();
		for (TbContentCategory tbContent : tbContentCategories) {
			// 为每一个tree赋值
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(tbContent.getId());
			treeNode.setText(tbContent.getName());
			treeNode.setState(tbContent.getIsParent() ? "closed" : "open");
			trees.add(treeNode);
		}
		return trees;
	}

	// 传入的父节点的id和当前节点的名称
	@Override
	public TaoBaoResult insertContentCategory(long parentId, String nodeName) {
		// TODO Auto-generated method stub
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setIsParent(false);// 新插入的节点都是叶子节点
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setName(nodeName);
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setUpdated(new Date());
		tbContentCategory.setStatus(1);
		tbContentCategory.setSortOrder(1);

		// 由于执行 插入操作后，如果是对叶子节点进行插入，则需要对叶子节点进行更改，为父节点状态
		// 根据传入的父id进行更改
		TbContentCategory contentCategory = cateGorymapper.selectByPrimaryKey(parentId);
		if (!contentCategory.getIsParent()) {// 原来为叶子节点，修改后为父节点
			// 修改状态
			contentCategory.setIsParent(true);
			cateGorymapper.updateByPrimaryKey(contentCategory);
		}
		cateGorymapper.insert(tbContentCategory);
		return TaoBaoResult.ok();
	}

	// 执行编辑操作
	@Override
	public void updateNodeName(long nodeId, String nodeName) {
		// TODO Auto-generated method stub

		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria createCriteria = example.createCriteria();
		
		// createCriteria.andCreated
		TbContentCategory category = cateGorymapper.selectByPrimaryKey(nodeId);// 根据子节点的id进行查询实体类
		category.setName(nodeName);
		cateGorymapper.updateByPrimaryKey(category);

	}

	// 执行删除操作
	@Override
	public void deleteByContentCategoryId(long nodeId) {
		// TODO Auto-generated method stub
		/*
		 * 情况 问题1 传入的删除节点就是叶子节点直接进行删除 传入待删除节点是父节点，且其子节点含有父节点
		 * 
		 * 共同考虑删除当前节点后其父节点是否需要改变状态(根据当前删除节点的父节点进行数据查询，是否就一个子节点，若一个子节点就改变其状态，
		 * 否则不变) 问题2 怎么判断当前节点是否是父节点
		 */
		// 根据传入的节点id查询当期节点的信息
		TbContentCategory delObject = cateGorymapper.selectByPrimaryKey(nodeId);
		// 判断当前节点是否是父节点
		if (delObject.getIsParent()) {// 如果是父节点
			List<TbContentCategory> nodes = new ArrayList<TbContentCategory>();
			// 得到所有的子节点
			List<TbContentCategory> childNodes = this.childNodes(delObject, nodes);
			// 判断所有节点父节点是否只有一个子节点
			for (TbContentCategory tbContentCategory : childNodes) {
				tbContentCategory.setStatus(0);
				cateGorymapper.updateByPrimaryKey(tbContentCategory);
			}
			// 判断当前delNode节点父节点是否只有一个子节点，即当前节点是否有兄弟节点
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria createCriteria = example.createCriteria();
			createCriteria.andParentIdEqualTo(delObject.getParentId());
			createCriteria.andStatusEqualTo(1);
			// 根据当前节点的父节点查询当前节点是否存在兄弟节点
			List<TbContentCategory> listBrother = cateGorymapper.selectByExample(example);
			if (listBrother.size() == 0) {
				TbContentCategory parentNode = cateGorymapper.selectByPrimaryKey(delObject.getParentId());
				parentNode.setIsParent(false);
				cateGorymapper.updateByPrimaryKey(parentNode);
			}
		} else {// 如果是叶子节点
				// 判断当前delNode节点父节点是否只有一个子节点，即当前节点是否有兄弟节点
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria createCriteria = example.createCriteria();
			createCriteria.andParentIdEqualTo(delObject.getParentId());
			createCriteria.andStatusEqualTo(1);
			// 根据当前节点的父节点查询当前节点是否存在兄弟节点
			List<TbContentCategory> listBrother = cateGorymapper.selectByExample(example);
			if (listBrother.size() == 1) {
				TbContentCategory parentNode = cateGorymapper.selectByPrimaryKey(delObject.getParentId());
				parentNode.setIsParent(false);
				cateGorymapper.updateByPrimaryKey(parentNode);
			}
			delObject.setStatus(0);
			cateGorymapper.updateByPrimaryKey(delObject);
		}
	}

	// 把所有的子节点获取到集合中
	public List<TbContentCategory> childNodes(TbContentCategory contentCategory, List<TbContentCategory> nodes) {
		// 传入的是父节点
		TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
		Criteria createCriteria = tbContentCategoryExample.createCriteria();
		createCriteria.andParentIdEqualTo(contentCategory.getId());// 传入的父节点的id作为查询子节点的id
		createCriteria.andStatusEqualTo(1);
		List<TbContentCategory> findNodes = cateGorymapper.selectByExample(tbContentCategoryExample);// 查询出该传入的节点的所有父节点
		// 如果查询的子节点仍然是父节点
		for (TbContentCategory Category : findNodes) {
			if (Category.getIsParent()) {
				this.childNodes(Category, nodes);
			} // 叶子节点
					// 子节点添加到集合中
				nodes.add(contentCategory);
			
		}
		return nodes;
	}

	
}
