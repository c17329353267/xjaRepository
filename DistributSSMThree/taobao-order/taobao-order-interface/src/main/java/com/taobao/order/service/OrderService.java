package com.taobao.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbOrder;
import com.taobao.pojo.TbOrderItem;

public interface OrderService {

	//创建订单
	TbOrder createOrder(String totalmaney,String token,List<TbItem> tbItems);
	
	//创建订单项
	void createOrderItem(TbItem tbItem);
	
	//修改订单状态
	void updateOrderStatus(String orderId);
	
	//展示订单详情

	TbOrder showOrder(String orderId);
}
