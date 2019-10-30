package com.taobao.wxpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taobao.wxpay.service.WXPayService;
//页面跳转控制器
@Controller
public class BaseController {

	
	@RequestMapping("/{page}")
	public String pay(@PathVariable("page") String page){
		
		return page;
	}
	
}
