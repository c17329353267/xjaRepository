package com.taobao.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Service;
import com.oracle.util.CookieUtils;
import com.oracle.util.IDUtils;
import com.oracle.util.JsonUtils;
import com.taobao.mapper.TbOrderItemMapper;
import com.taobao.mapper.TbOrderMapper;
import com.taobao.order.service.OrderService;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbOrder;
import com.taobao.pojo.TbOrderItem;
import com.taobao.pojo.TbUser;

import redis.clients.jedis.JedisCluster;

@Service
public class OrderServiceImpl implements OrderService{

	@Value("${USERTOKEN}")
	private String usertoken;
	@Value("${cookiekey}")
	private String cookieKey;
	@Autowired
	private JedisCluster jedisCluser; //注入JedisCluster 
	@Autowired
	private TbOrderMapper ordermapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Override
	public TbOrder createOrder(String totalmaney,String token,List<TbItem> tbItems) {
		// TODO Auto-generated method stub
		String json = jedisCluser.get(usertoken+":"+token);
		
		TbUser tbUser  = JsonUtils.jsonToPojo(json, TbUser.class);//得到用户信息
		System.out.println("OrderServiceImpl createOrder json:"+json+"==========tbUser:"+tbUser);
		TbOrder tbOrder = new TbOrder();
		tbOrder.setOrderId(IDUtils.genItemId()+"");
		tbOrder.setBuyerNick(tbUser.getUsername());
		tbOrder.setUserId(tbUser.getId());
		tbOrder.setCreateTime(new Date());
		tbOrder.setPayment(totalPrice(tbItems));//计算总价格
		tbOrder.setStatus(1);//付款状态
		tbOrder.setCloseTime(new Date());
		tbOrder.setEndTime(new Date());
		tbOrder.setPaymentTime(new Date());
		tbOrder.setShippingName(getShippingName());
		tbOrder.setShippingCode(IDUtils.genItemId()+"");
		tbOrder.setStatus(0);
		ordermapper.insert(tbOrder);//创建订单
		//创建订单项
		for (TbItem tbItem : tbItems) {
			TbOrderItem orderItem = new TbOrderItem();
			orderItem.setId(IDUtils.genItemId()+"");//每一个订单项对应一个订单id
			orderItem.setItemId(tbItem.getId()+"");
			orderItem.setNum(tbItem.getNum());
			orderItem.setPrice(tbItem.getPrice());//商品单价
			orderItem.setTotalFee(tbItem.getPrice()*tbItem.getNum());//商品总价
			orderItem.setTitle(tbItem.getTitle());
			orderItem.setOrderId(tbOrder.getOrderId());
			orderItem.setPicPath(tbItem.getImage());
			tbOrderItemMapper.insert(orderItem);
		}
		
		System.out.println("=======tbOrder========="+tbOrder);
		return tbOrder;//将订单总金额返回
			
	}
	@Override
	public void createOrderItem(TbItem tbItem) {
		// TODO Auto-generated method stub
		
	}
	
	public  String  getShippingName() {
		List<String> shoppingNames = new ArrayList<String>();
		shoppingNames.add("申通");//
		shoppingNames.add("韵达");
		shoppingNames.add("中通");
		shoppingNames.add("顺丰");
		shoppingNames.add("邮政");
		shoppingNames.add("菜鸟驿站");
		shoppingNames.add("圆通");
		Random random = new Random();
		//采用随机数获取快递名称
		int randNumber = random.nextInt(shoppingNames.size() - 1 + 0) + 1;
		System.out.println("shoping name :"+shoppingNames.get(randNumber));
		return shoppingNames.get(randNumber);
		
	}	
	//计算商品总价格
	public String totalPrice(List<TbItem> cartlists){
		long totalprice = 0;
		for (TbItem tbItem : cartlists) {
			System.out.println(tbItem.getNum());
			Integer num = tbItem.getNum();
			Long price = tbItem.getPrice();
			totalprice = num * price +totalprice;
		}
		System.out.println("totalPrice====:"+totalprice);
		return totalprice+"";
	}
	@Override
	public void updateOrderStatus(String orderId) {
		// TODO Auto-generated method stub
		//修改订单状态
		TbOrder tbOrder = ordermapper.selectByPrimaryKey(orderId);
		tbOrder.setStatus(2);
		ordermapper.updateByPrimaryKey(tbOrder);
	}
	//展示用户付钱后的订单详情
	@Override
	public TbOrder showOrder(String orderId) {
		// TODO Auto-generated method stub
		return ordermapper.selectByPrimaryKey(orderId);
	}
	
}
