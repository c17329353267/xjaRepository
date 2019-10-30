package com.taobao.wxpay.service;

import java.util.Map;

public interface PayService {
	
	 Map createNative(String out_trade_no,String total_fee);
	 
	 Map queryPayStatus(String out_trade_no);
}
