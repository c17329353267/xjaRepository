package com.taobao.wxpay.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taobao.wxpay.service.WXPayService;

@RestController
@RequestMapping("scan")
public class WXPayController {

	@Autowired
	private WXPayService wxPayService;
	
	@RequestMapping("pay")
	public Object createNative(){
		//每次测试支付完,需要修改下订单号,下次才可用
	    String orderId = "931716402249805835";

		return wxPayService.createNative("1", orderId, "java全栈视频");
				
	}
	//监控订单状态
	@RequestMapping("queryPayStatus")
	public Object queryPayStatus(String out_trade_no){
		System.out.println("queryPayStatus out_trade_no:"+out_trade_no);
		
		int i =0;//设置超时时间问题
		//用于封装返回结果
		Map<String,Object> resultMap = new HashMap<>();
		while(true){
			Map map = wxPayService.queryPayStatus(out_trade_no);
			if(map == null ){//出错
				resultMap.put("msg", "支付出错");
				resultMap.put("success", false);
				break;
			}
			if("SUCCESS".equals(resultMap.get("trade_state "))){
				resultMap.put("msg", "支付成功");
				resultMap.put("success", true);
				break;
			}
			
			try {
				Thread.sleep(2000);//间隔两秒
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(i >= 10){
				resultMap.put("msg", "二维码超时");
				resultMap.put("success", false);
				break;
			}
		}
		
		return resultMap;
	}
}
