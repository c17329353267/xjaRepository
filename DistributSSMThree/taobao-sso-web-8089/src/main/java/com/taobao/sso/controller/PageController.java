package com.taobao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转控制器
 * @author 陈成
 *
 * 2019年10月21日
 */
@Controller
public class PageController {

	@RequestMapping("page/{page}")
	public String page(@PathVariable("page") String page){
		return page;
	}
}
