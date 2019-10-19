package com.taobao.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.taobao.mapper.TbItemCatMapper;
import com.taobao.mapper.TbItemMapper;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemCat;

public class MyTopicListenter implements MessageListener{

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;//注入分类的mapper
	
	@Autowired
	private CloudSolrServer SolrServer;
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		TextMessage textMessage = (TextMessage)message;
		 try {
			String value = textMessage.getText();
			if(!StringUtils.isEmpty(value)){
				String[] idStr = value.split("-"); 
				long id = Long.parseLong(idStr[1]);
				if("ADD".equals(idStr[0])){
					this.add(id);
				}
				
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		//System.out.println(textMessage);
	}
	
	public void add(long id){
		System.out.println("listenter id:"+id);
		try {
			//根据查询商品的id信息得到商品的信息和商品的分类名称,然后存入solr中方便搜索
			TbItem item = itemMapper.selectByPrimaryKey(id);
			TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(item.getCid());
			SolrInputDocument inputDocument = new SolrInputDocument();
			inputDocument.addField("id", item.getId()+"");
			inputDocument.addField("item_title", item.getTitle());
			inputDocument.addField("item_image", item.getImage());
			inputDocument.addField("item_price", item.getPrice());
			inputDocument.addField("item_sell_point", item.getSellPoint());
			inputDocument.addField("item_category_name", itemCat.getName());
			SolrServer.add(inputDocument);//将域添加到solrServer中
			SolrServer.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void delete(){
		
		
	}

}
