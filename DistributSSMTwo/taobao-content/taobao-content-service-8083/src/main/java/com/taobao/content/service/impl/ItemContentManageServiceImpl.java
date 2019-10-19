package com.taobao.content.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.util.JsonUtils;
import com.taobao.content.service.ItemContentManagService;
import com.taobao.mapper.TbContentMapper;
import com.taobao.pojo.AdNode;
import com.taobao.pojo.EasyUIDataGridResult;
import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbContent;
import com.taobao.pojo.TbContentExample;
import com.taobao.pojo.TbContentExample.Criteria;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
/**
 * @author 陈成
 *
 * 2019年10月17日
 * 分类管理
 */
@Service
public class ItemContentManageServiceImpl implements ItemContentManagService {
	//轮播图属性注入
	@Value("${HEIGHT}")
	private String height;
	@Value("${WIDTH}")
	private String width;
	@Value("${HEIGHB}")
	private String heightb;
	@Value("${WIDTHB}")
	private String widthb;
	
	//广告类别标识
	@Value("${constant.category}")
	private String contentCategory;
	
	//注入jedis
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private TbContentMapper TbContentMapper;
	
	//注入jedisCluser
	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public EasyUIDataGridResult queryByPageCategoryId(int page, int rows, long cateGoryId) {
		//分页
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		if(cateGoryId!=0){
			createCriteria.andCategoryIdEqualTo(cateGoryId);
		}
		
		List<TbContent> listContents = TbContentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<>(listContents);
		return new EasyUIDataGridResult(pageInfo.getTotal(), pageInfo.getList());
	}
	//根据分类id查询轮播图
		@Override
		public List<AdNode> queryByCategoryId(long categoryid) {
			//由于查询操作比较频繁，应当放入redis缓存，确定选取的数据结构，散列hash
			
			//从缓存池中获取jedis
			//Jedis jedis = jedisPool.getResource();
			String value = jedisCluster.hget(contentCategory,categoryid+"");
			//判断是否为空
			if(!StringUtils.isEmpty(value)){
				//如果redis中缓存有值则调用该redis中的值
				List<AdNode> jsonAdnodeList = JsonUtils.jsonToList(value, AdNode.class);
				System.out.println("使用redis缓存池中数据");
				return jsonAdnodeList;
			}
			
			TbContentExample example = new TbContentExample();
			Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryid);
			List<TbContent> categorys = TbContentMapper.selectByExample(example);
			List<AdNode> adNodes = new ArrayList<>();
			for (TbContent tbContent : categorys) {
				AdNode adNode = new AdNode();
				adNode.setAlt(tbContent.getTitle());
				adNode.setHref(tbContent.getUrl());
				adNode.setSrc(tbContent.getPic());
				adNode.setSrcB(tbContent.getPic2());
				adNode.setHeight(height);
				adNode.setHeightB(heightb);
				adNode.setWidth(width);
				adNode.setWidthB(widthb);
				adNodes.add(adNode);
			}
			try {
				jedisCluster.hset(contentCategory, categoryid+"",JsonUtils.objectToJson(adNodes));
				
				
			} catch (Exception e) {
			
			}finally {
				
				//redis.clients.jedis.exceptions.JedisNoReachableClusterNodeException: No reachable node in cluster
				//jedisCluster.close();
			}
			
			return adNodes;
		}
		
		//轮播图的添加即大广告的添加
		@Override
		public TaoBaoResult insertTbContent(TbContent tbcontent) {
			//数据同步，解决在添加图片后更新redis缓存中的内容
			jedisCluster.hdel(contentCategory,tbcontent.getCategoryId()+"");
			tbcontent.setCreated(new Date());
			tbcontent.setUpdated(new Date());
			TbContentMapper.insert(tbcontent);
			return TaoBaoResult.ok();
		}
}
