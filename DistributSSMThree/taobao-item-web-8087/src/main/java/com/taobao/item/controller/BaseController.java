package com.taobao.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.item.bo.BOItem;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;
import com.taobao.service.ItemService;
/**
 * @author 陈成
 *
 * 2019年10月15日
 * 商品详情
 */
@Controller
public class BaseController {

	//远程调用managerService
	@Reference
	private ItemService itemService;
	
	@RequestMapping("item/{id}")
	public String baseItem(@PathVariable("id")long id,Model model){
		//System.out.println("baseItem id:"+id+"===itemService:"+itemService);
		//由于前台需要tbitem中的属性值和商品的描述信息
		TbItem tbItem = itemService.QueryItemById(id);
		TbItemDesc itemDesc = itemService.queryTbItemdescById(tbItem.getId());
		/*
			问题：由于前台的数据显示封装的一个属性类中，所以需要在此将两个实体类的数据转换成前台需要的BO对象
			前台界面的images数组
		 */
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("/WEB-INF/ftl");
		configurer.setDefaultEncoding("utf-8");
		model.addAttribute("item", new BOItem(tbItem));
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
}
