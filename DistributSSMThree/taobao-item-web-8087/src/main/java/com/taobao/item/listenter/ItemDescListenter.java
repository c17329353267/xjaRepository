package com.taobao.item.listenter;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.item.bo.BOItem;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;
import com.taobao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ItemDescListenter implements MessageListener{

	@Reference
	private ItemService itemService;
	
	//注入freemarker模板
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigure;
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			TextMessage textMessage = (TextMessage)message;
			String value = textMessage.getText();
			
			if(value != null){
	
				String[] str = value.split("-");
				System.out.println("-----------------"+str[1]);
				long id = Long.parseLong(str[1]);
		
				TbItem tbItem = itemService.QueryItemById(id);
				TbItemDesc itemDesc = itemService.queryTbItemdescById(tbItem.getId());
				Configuration configuration = freeMarkerConfigure.getConfiguration();
				Template template = configuration.getTemplate("item.ftl");
		
				Map map = new HashMap();
				map.put("item", new BOItem(tbItem));
				map.put("itemDesc", itemDesc);
				
				Writer writer = new FileWriter(new File("D:/freemarkertestFile/item/"+id+".html"));
				template.process(map, writer);
				writer.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
