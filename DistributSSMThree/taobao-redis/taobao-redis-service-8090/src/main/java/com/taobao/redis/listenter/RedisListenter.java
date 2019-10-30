package com.taobao.redis.listenter;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.oracle.util.JsonUtils;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;
import com.taobao.redis.service.RedisService;
import redis.clients.jedis.JedisCluster;

public class RedisListenter implements MessageListener {

	@Autowired
	private RedisService redisService;
	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			TextMessage textMessage = (TextMessage) message;
			String value = textMessage.getText();
			if (value != null) {
				String[] str = value.split("-");
				long id = Long.parseLong(str[1]);
				TbItem tbItem = redisService.queryItemById(id);
				System.out.println("tbITem=========="+tbItem);
				if (tbItem != null) {
					System.out.println("############## tbItem"+tbItem);
					jedisCluster.set("item:" + id + ":base", JsonUtils.objectToJson(tbItem));
					// 设置失效时间
					jedisCluster.expire("item:" + id + ":base", 259200);
				
					TbItemDesc itemDesc = redisService.queryItemDescById(tbItem.getId());
					if (itemDesc != null) {
					// 缓存中未存在，即往缓存中存储

					jedisCluster.set("item:" + id + ":desc", JsonUtils.objectToJson(itemDesc));
					// 设置失效时间
					jedisCluster.expire("item:" + id + ":desc", 259200);
					//Thread.sleep(3000);
				 }
				}
			}
			}catch (Exception e) {
		  
			e.printStackTrace();
		
		}finally {
			
		}
	}
		
	
}
