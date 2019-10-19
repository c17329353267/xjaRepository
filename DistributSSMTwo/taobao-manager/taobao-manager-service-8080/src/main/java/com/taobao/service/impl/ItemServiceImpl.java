package com.taobao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.util.IDUtils;
import com.oracle.util.JsonUtils;
import com.taobao.mapper.TbItemDescMapper;
import com.taobao.mapper.TbItemMapper;
import com.taobao.pojo.EasyUIDataGridResult;
import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;
import com.taobao.pojo.TbItemExample;
import com.taobao.pojo.TbItemExample.Criteria;
import com.taobao.service.ItemService;

import redis.clients.jedis.JedisCluster;
@Service
public class ItemServiceImpl implements ItemService{

	
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	//注入jmstemplate
	@Autowired
	private JmsTemplate jmsTemplate;
	//注入目的地 由于spring-mvc.xml中有两种目的地，所以在此根据名称注入
	@Resource(name="topicDestination")
	private Destination destination;
	//商品详情页使用redis缓存
	@Autowired
	private JedisCluster jedisCluster;
	//根据id查询商品信息
	@Override
	public TbItem QueryItemById(long id) {
		//查询商品之前先从redis缓存中进行查询
		//自定义查询主键
		String value = jedisCluster.get("item:"+id+":base");
		if(!StringUtils.isEmpty(value)){//jedis缓存中存在此key对应的value值
			//System.out.println("in redis itembase");
			return JsonUtils.jsonToPojo(value, TbItem.class);//json转实体类
		}
		
		try {
			//缓存中未存在，即往缓存中存储
			TbItem tbitem = itemMapper.selectByPrimaryKey(id);
			jedisCluster.set("item:" + id + ":base", JsonUtils.objectToJson(tbitem));
			//设置失效时间
			jedisCluster.expire("item:" + id + ":base", 259200);
			return tbitem;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	//根据商品id查询商品详情
	public TbItemDesc queryTbItemdescById(long id){
		//return tbItemDescMapper.selectByPrimaryKey(id);
		//查询商品之前先从redis缓存中进行查询
				//自定义查询主键
				String value = jedisCluster.get("item:"+id+":desc");
				if(!StringUtils.isEmpty(value)){//jedis缓存中存在此key对应的value值
					System.out.println("in redis item  desc");
					return JsonUtils.jsonToPojo(value, TbItemDesc.class);
				}
				try {
					//缓存中未存在，即往缓存中存储
					TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
					jedisCluster.set("item:" + id + ":desc", JsonUtils.objectToJson(tbItemDesc));
					//设置失效时间
					jedisCluster.expire("item:" + id + ":desc", 259200);
					return tbItemDesc;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					return null;
				}
	}
	//用来查询所有商品
	@Override
	public EasyUIDataGridResult queryPage(int page, int rows) {
		//开启分页
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		Criteria createCriteria = example.createCriteria();
		//查询应到查询未删除的，即删除的数据状态为3不再查询出此数据
		createCriteria.andStatusNotEqualTo((byte)3);
		List<TbItem> tbItems = itemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);
		return new EasyUIDataGridResult(pageInfo.getTotal(),pageInfo.getList());
	}
	//根据id进行删除
	@Override
	public TaoBaoResult deleteByIds(long[] ids) {
		// TODO Auto-generated method stub
		if(ids.length != 0){
			
			for(int i=0;i<ids.length;i++){
				TbItem item = itemMapper.selectByPrimaryKey(ids[i]);
				//查询后更新
				item.setStatus((byte)3);//假删除，只是把状态修改
				itemMapper.updateByPrimaryKey(item);//未判断是否为空
				
			}
		}
		return TaoBaoResult.ok();
	}
	//商品添加
	@Override
	public TaoBaoResult insertItem(TbItem tbItem, String desc) {
	
		
		//使用自定义id生成策略生成主键
		final long itemId = IDUtils.genItemId();
		tbItem.setId(itemId);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		tbItem.setStatus((byte)1);
		//数据库商品的添加
		itemMapper.insert(tbItem);
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		tbItemDesc.setItemId(tbItem.getId());
		tbItemDesc.setItemDesc(desc);
		//商品描述表的添加  --两表同步
		tbItemDescMapper.insert(tbItemDesc);
		//使用消息中间件来做消息的同步 当数据库数据改变时发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session sesion) throws JMSException {
				TextMessage createTextMessage = sesion.createTextMessage("ADD-"+itemId);
				return createTextMessage;
			}
		});
		return TaoBaoResult.ok();
	}
	//根据商品id查询商品的描述信息
	@Override
	public TaoBaoResult loadItemDesc(long id) {
		// TODO Auto-generated method stub
		TaoBaoResult taoBaoResult = new TaoBaoResult();
		taoBaoResult.setData(tbItemDescMapper.selectByPrimaryKey(id));
		taoBaoResult.setStatus(200);
		taoBaoResult.setMsg("OK");
		return taoBaoResult;
	}
	@Override
	public TaoBaoResult query(long id) {
		// TODO Auto-generated method stub
		TaoBaoResult taoBaoResult = new TaoBaoResult();
		taoBaoResult.setData(itemMapper.selectByPrimaryKey(id));
		taoBaoResult.setStatus(200);
		taoBaoResult.setMsg("OK");
		return taoBaoResult;
	}
	//商品下架
	@Override
	public TaoBaoResult lowerShelf(long[] ids) {

		if(ids.length != 0){
			for(int i=0;i<ids.length;i++){
				TbItem item = itemMapper.selectByPrimaryKey(ids[i]);
				//查询后更新
				item.setStatus((byte)2);
				itemMapper.updateByPrimaryKey(item);//未判断是否为空			
			}
		}
		return TaoBaoResult.ok();
	}
	//商品上架
	@Override
	public TaoBaoResult onShelf(long[] ids) {
		// TODO Auto-generated method stub
		if(ids.length != 0){
			for(int i=0;i<ids.length;i++){
				TbItem item = itemMapper.selectByPrimaryKey(ids[i]);
				//查询后更新
				item.setStatus((byte)1);
				itemMapper.updateByPrimaryKey(item);//未判断是否为空			
			}
		}
		return TaoBaoResult.ok();
	}
	//商品信息修改
	@Override
	public TaoBaoResult updateItem(TbItem tbItem) {
		// TODO Auto-generated method stub
		/*TbItemExample example = new TbItemExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andIdEqualTo(tbItem.getId());*/
		tbItem.setStatus((byte)1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		itemMapper.updateByPrimaryKey(tbItem);
		return TaoBaoResult.ok();
	}


}
