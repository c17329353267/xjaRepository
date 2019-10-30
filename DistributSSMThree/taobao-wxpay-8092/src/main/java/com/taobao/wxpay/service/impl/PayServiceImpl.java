package com.taobao.wxpay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.wxpay.sdk.WXPayUtil;
import com.taobao.wxpay.service.PayService;
import com.taobao.wxpay.util.HttpClient;

/**
 * 微信支付接口实现类
 * @author 陈成
 *
 * 2019年10月22日
 */
@Service
public class PayServiceImpl implements PayService {

	@Value("${appid}")
	private String appid;

	@Value("${partner}")
	private String partner;

	@Value("${partnerkey}")
	private String partnerkey;

	//支付实现
	@Override
	public Map createNative(String out_trade_no, String total_fee) {

		/*
		 * 测试微信支付
		 * // 1.创建参数
		Map<String, String> param = new HashMap();// 创建参数
		param.put("appid", appid);// 公众号
		param.put("mch_id", partner);// 商户号
		param.put("nonce_str", WXPayUtil.generateNonceStr());// 随机字符串
		param.put("body", "皇家帝王享用");// 商品描述
		param.put("out_trade_no", out_trade_no);// 商户订单号
		param.put("total_fee", total_fee);// 总金额（分）
		param.put("spbill_create_ip", "127.0.0.1");// IP
		param.put("notify_url", "http://test.itcast.cn");// 回调地址(随便写)
		param.put("trade_type", "NATIVE");// 交易类型
		try {
			// 2.生成签名，要发送的xml
			String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
			//System.out.println("请求参数  :" + xmlParam);
			HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
			client.setHttps(true);
			client.setXmlParam(xmlParam);
			client.post();
			// 3.获得结果
			String result = client.getContent();
			System.out.println(result);
			Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
			Map<String, String> map = new HashMap<>();
			map.put("code_url", resultMap.get("code_url"));// 支付地址
			map.put("total_fee", total_fee);// 总金额
			map.put("out_trade_no", out_trade_no);// 订单号
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<>();
		}*/
		Map<String, String> param = new HashMap();// 创建参数
		param.put("appid", appid);// 公众号
		param.put("mch_id", partner);// 商户号
		param.put("nonce_str", WXPayUtil.generateNonceStr());// 随机字符串
		param.put("body", "皇家帝王享用");// 商品描述
		param.put("out_trade_no", out_trade_no);// 商户订单号
		param.put("total_fee", total_fee);// 总金额（分）
		param.put("spbill_create_ip", "127.0.0.1");// IP
		param.put("notify_url", "http://test.itcast.cn");// 回调地址(随便写)
		param.put("trade_type", "NATIVE");// 交易类型
		try {
			// 2.生成签名，要发送的xml
			String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
			//System.out.println("请求参数  :" + xmlParam);
			HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
			client.setHttps(true);
			client.setXmlParam(xmlParam);
			client.post();
			// 3.获得结果
			String result = client.getContent();
			System.out.println(result);
			Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
			Map<String, String> map = new HashMap<>();
			map.put("code_url", resultMap.get("code_url"));// 支付地址
			map.put("total_fee", total_fee);// 总金额
			map.put("out_trade_no", out_trade_no);// 订单号
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<>();
		}
	}
	//支付状态实现
		@Override
		public Map<String, String> queryPayStatus(String out_trade_no) {
			if (out_trade_no != null && !"".equals(out_trade_no)) {

				Map<String, String> param = new HashMap<String, String>();
				param.put("appid", appid);// 公众账号ID
				param.put("mch_id", partner);// 商户号
				param.put("out_trade_no", out_trade_no);// 订单号
				param.put("nonce_str", WXPayUtil.generateNonceStr());// 随机字符串
				String url = "https://api.mch.weixin.qq.com/pay/orderquery";
				try {
					String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
					HttpClient client = new HttpClient(url);
					client.setHttps(true);
					client.setXmlParam(xmlParam);
					client.post();
					String result = client.getContent();
					Map<String, String> map = WXPayUtil.xmlToMap(result);
					System.out.println(map);
					return map;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}

			}
			return null;
		}

}

