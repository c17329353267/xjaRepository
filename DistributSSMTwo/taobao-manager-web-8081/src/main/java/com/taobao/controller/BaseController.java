package com.taobao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BaseController {
	//返回首界面
	@RequestMapping("/")
	public String returnIndex(){
		System.out.println("in BaseController returnIndex 8081");
		return "index";
	}
	//返回page界面
	@RequestMapping("/{page}")
	public String page(@PathVariable("page") String page){
		
		return page;
	}
}
