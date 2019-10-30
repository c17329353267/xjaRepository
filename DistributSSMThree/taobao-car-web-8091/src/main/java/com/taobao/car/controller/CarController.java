package com.taobao.car.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr.Item;
import com.alibaba.druid.stat.TableStat.Mode;
import com.alibaba.dubbo.config.annotation.Reference;
import com.oracle.util.CookieUtils;
import com.oracle.util.JsonUtils;
import com.taobao.pojo.TbItem;
import com.taobao.service.ItemService;

@Controller
@RequestMapping("car")
public class CarController {
	
	@Reference
	private ItemService ItemService;

	@RequestMapping("/{page}")
	public String page(@PathVariable("page")String page){
		return page;
	}
	//商品添加人购物车
	@RequestMapping("add/{id}")
	public String addCar(@PathVariable("id") String strid,@RequestParam(value="num",defaultValue="1")int num,
			HttpServletRequest request,HttpServletResponse response){
		Long id = Long.parseLong(strid.replace(",", ""));
		List<TbItem> itemCartList =getItemCartList(request);
		boolean flag = false;
		try {
			for (TbItem tbItem : itemCartList) {
				if(tbItem.getId().longValue() == id.longValue()){//如果当前商品在购物车中存在则数量增加
					tbItem.setNum(tbItem.getNum()+num);
					
					//itemCartList.add(tbItem);//把修改后的商品信息重新放入list集合
					flag = true;
				}
			}
			System.out.println(itemCartList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(!flag){//如果当前商品在购物车中不存在
			//根据商品id从数据库中查询当前商品信息
			TbItem item = ItemService.QueryItemById(id);
			//取出一张图片
			String img = item.getImage();
			if(!StringUtils.isEmpty(img)){
				//分割字符串取出第一张图片
				String[] imgs = img.split(",");
				item.setImage(imgs[0]);
			}
			//设置购买商品的数量
			item.setNum(num);
			//将商品添加到购物车列表
			itemCartList.add(item);
			//将商品信息加入到cookie中
			
			
		}
		CookieUtils.setCookie(request, response, "Item_Cart", JsonUtils.objectToJson(itemCartList),604800,true);
		return "cartSuccess";
	}
	//将购物车中的商品信息进行展示到界面
	@RequestMapping("car")
	public String showCartNews(Model model,HttpServletRequest request){
		List<TbItem> carItemList = this.getItemCartList(request);
		model.addAttribute("cartList", carItemList);
		return "cart";
	}
	//获取cookie中的购车
	public List<TbItem> getItemCartList(HttpServletRequest request){
		
		String json = CookieUtils.getCookieValue(request, "Item_Cart",true);
		if(!StringUtils.isEmpty(json)){
			
			List<TbItem> itemslist = JsonUtils.jsonToList(json, TbItem.class);
			return itemslist;
		}
		return new ArrayList<TbItem>();
	}
	
	//修改商品数量
	@RequestMapping("update/num/{id}/{num}")
	public String updateItemNum(@PathVariable("id") Long id,@PathVariable("num") int num,
			HttpServletRequest request,HttpServletResponse response,Model model){
		//从cookie中获取id为传入的商品并改变其数量重新存放到cookie中并返回购物车界面
		String json = CookieUtils.getCookieValue(request, "Item_Cart",true);
		
		List<TbItem> itemlist = JsonUtils.jsonToList(json, TbItem.class);
		try {
			for (TbItem tbItem:itemlist) {
				if(tbItem.getId().longValue() == id.longValue()){
					//修改商品在购物车中的数量
					tbItem.setNum(num);
					//itemlist.add(tbItem);
				}}
		} catch (Exception e) {
			// TODO: handle exception
		}
			
			
		CookieUtils.setCookie(request, response, "Item_Cart", JsonUtils.objectToJson(itemlist),604800,true);
		model.addAttribute("cartList", itemlist);
		return "cart";
	}
	//删除操作
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable("id") Long id,HttpServletRequest request,
			HttpServletResponse response,Model model){
		String json = CookieUtils.getCookieValue(request, "Item_Cart",true);
		List<TbItem> itemlist = JsonUtils.jsonToList(json, TbItem.class);
			try {
				
				Iterator<TbItem> it =itemlist.iterator();
		        while (it.hasNext())
		        {
		        	TbItem tbItem = it.next();
		            if (tbItem.getId().longValue() == id.longValue())
		            {
		                it.remove();  
		            }
		        }

			} catch (Exception e) {
				e.printStackTrace();
			}
		model.addAttribute("cartList", itemlist);	
		CookieUtils.setCookie(request, response, "Item_Cart", JsonUtils.objectToJson(itemlist),604800,true);
		
		return "cart";
	}
	
}
