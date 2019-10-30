package com.taobao.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController{
	
	@RequestMapping("/{page}")
	public String index(@PathVariable("page") String page){
		
		return page;
	}

}
