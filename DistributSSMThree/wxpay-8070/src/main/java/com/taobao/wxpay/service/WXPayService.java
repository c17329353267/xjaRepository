package com.taobao.wxpay.service;

import java.util.Map;

public interface WXPayService {
	//创建支付接口
	Map createNative(String money,String orderId,String desc); 
	
	//监控订单状态接口
	 Map queryPayStatus(String orderId);
}
