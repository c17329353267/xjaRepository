package com.taobao.wxpay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.wxpay.sdk.WXPayUtil;
import com.taobao.wxpay.service.WXPayService;
import com.taobao.wxpay.util.HttpClient;

/**
 * 微信支付接口实现类
 * @author 陈成
 *
 * 2019年10月22日
 */
@Service
public class WXPayServiceImpl implements WXPayService{
	/**
	 *  appid： 微信公众账号或开放平台APP的唯一标识
		partner：财付通平台的商户账号
		partnerkey：财付通平台的商户密钥
		notifyurl:  回调地址
	 */
	@Value("${appid}")
	private String appid;
	
	@Value("${partner}")
	private String partner;
	
	@Value("${partnerkey}")
	private String partnerkey;
	
	@Value("${notifyurl}")
	private String notifyurl;
	
	//钱，订单id,商品描述
	@Override
	public Map createNative(String money, String orderId, String desc) {
		//
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", appid);//公众账号ID
		map.put("mch_id", partner);//商户id
		map.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
		map.put("body", desc);//商品简单描述
		map.put("out_trade_no", orderId);//商品系统内部订单号
		map.put("total_fee", money);//订单总金额
		map.put("spbill_create_ip", "127.0.0.1");//支持ipv4和ipv6两种格式的ip地址
		map.put("notify_url", "www.baidu.com");//异步接收微信支付结果通知的的回调地址
		map.put("trade_type", "NATIVE");//交易类型
		
		
		try {
			//生成签名要发送的xml
			String xmlParam = WXPayUtil.generateSignedXml(map, partnerkey);
			HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
			client.setHttps(true);//设置为true表示采用http协议
			client.setXmlParam(xmlParam);
			client.post();//采用post方式进行发送
			//获取结果
			String result = client.getContent();
			Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
			
			Map<String, String> map2 = new HashMap<>();
			map.put("code_url", resultMap.get("code_url"));
			map.put("total_fee", money);
			map.put("out_trade_no", orderId);
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	//用户监控订单支付状态
	@Override
	public Map queryPayStatus(String orderId) {
		//判断传入的订单id是否存在
		if(orderId != null && !"".equals(orderId)){
			Map<String, String> map = new HashMap<>();
			map.put("appid", appid);//公众账号ID
			map.put("mch_id", partner);//商户id
			map.put("out_trade_no", orderId);//商品系统内部订单号
			map.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
			String url = "https://api.mch.weixin.qq.com/pay/orderquery";
			try {
				String xmlParam = WXPayUtil.generateSignedXml(map, partnerkey);
				HttpClient client = new HttpClient(url);
				client.setHttps(true);//设置为true表示采用http协议
				client.setXmlParam(xmlParam);
				client.post();//采用post方式进行发送
				//获取结果
				String result = client.getContent();
				Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
				return resultMap;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
		return null;
	}

}
