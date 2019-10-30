package com.taobao.wxpay.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.stat.TableStat.Mode;
import com.alibaba.dubbo.config.annotation.Reference;
import com.oracle.util.CookieUtils;
import com.oracle.util.IDUtils;
import com.oracle.util.JsonUtils;
import com.taobao.order.service.OrderService;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbOrder;
import com.taobao.wxpay.service.PayService;
@RestController
@RequestMapping("wx")
public class PayController {
	
	@Autowired
	private PayService payService;
	@Value("${USERTOKEN}")
	private String usertoken;
	@Value("${cookiekey}")
	private String cookieKey;
	@Reference
	private OrderService orderService;
	//支付接口
	@RequestMapping("pay")
	public Object pay(List<TbItem> tbItems) {
		//测试数据
		//每次测试支付完,需要修改下订单号,下次才可用
		//String outid = "931716402249805615";
		//这里订单一旦支付过不能再用,否则微信支付失败,这里参数 1 是1分钱,不是元
		//return payService.createNative(outid, "1");
		//==================================
		System.out.println("PayController pay tbitems:"+tbItems);
		return payService.createNative(IDUtils.genItemId()+"", "1");
	}

	@RequestMapping("orderCart")
	public Object showOrder(HttpServletRequest request,HttpServletResponse response,Model model){
		//生成订单
		//orderService.createOrder(totalPrice,request,response);
		String json = CookieUtils.getCookieValue(request, "Item_Cart",true);//从cookie中获取购物车信息
		List<TbItem> cartlists = JsonUtils.jsonToList(json, TbItem.class);
		String token = CookieUtils.getCookieValue(request, cookieKey);
		System.out.println("token="+token+",cartList:"+cartlists);
		//计算订单总金额
		TbOrder tbOrder = orderService.createOrder("1",token,cartlists);//
		model.addAttribute("orderId", tbOrder.getOrderId());
		System.out.println("PayController totalprice:"+tbOrder.getPayment());
		return payService.createNative(tbOrder.getOrderId(), "1");
	}

	/*
	 * 检测支付状态(否则支付成功)
	 */
	@RequestMapping("queryPayStatus")
	public Object queryPayStatus(String out_trade_no) {

		System.out.println("检测订单号:" + out_trade_no);

		// 设置超时问题
		int i = 0;

		Map<Object, Object> result = new HashMap<Object, Object>();
		// 这里循环检测
		while (true) {
			Map<String, String> map = payService.queryPayStatus(out_trade_no);
			if (map == null) {// 出错
				result.put("success", false);
				result.put("msg", "支付出错");
				break;
			}
			if (map.get("trade_state").equals("SUCCESS")) {// 如果成功
				result.put("success", true);
				result.put("msg", "支付成功");
				//修改订单状态
				orderService.updateOrderStatus(out_trade_no);
				break;
			}
			try {
				Thread.sleep(2000);// 间隔三秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 二维码超时问题
			i++;
			if (i >= 10) {
				result.put("success", false);
				result.put("msg", "二维码超时");
				break;
			}
		}
		return result;
	}

}
