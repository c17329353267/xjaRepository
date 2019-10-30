package com.taobao.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.config.annotation.Reference;
import com.oracle.util.CookieUtils;
import com.oracle.util.JsonUtils;
import com.taobao.order.service.OrderService;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbOrder;
@Controller
@RequestMapping("order")
public class OrderController{

	@Value("${USERTOKEN}")
	private String usertoken;
	@Value("${cookiekey}")
	private String cookieKey;
	@Reference
	private OrderService orderService;
	@RequestMapping("orderCart")
	public String showOrder(HttpServletRequest request,HttpServletResponse response){
		//生成订单
		//orderService.createOrder(totalPrice,request,response);
		String json = CookieUtils.getCookieValue(request, "Item_Cart",true);//从cookie中获取购物车信息
		List<TbItem> cartlists = JsonUtils.jsonToList(json, TbItem.class);
		String token = CookieUtils.getCookieValue(request, cookieKey);
		System.out.println("token="+token+",cartList:"+cartlists);
		//计算订单总金额
		
		orderService.createOrder("1",token,cartlists);
		return "null";
	}
	
	//展示客户的订单
	@RequestMapping("showOrder")
	private String showOrder(String orderId,Model model) {
		// TODO Auto-generated method stub
		TbOrder tbOrder = orderService.showOrder(orderId);
		model.addAttribute("tbOrder", tbOrder);
		return "success";
	}
}
