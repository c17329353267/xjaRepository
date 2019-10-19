package com.taobao.sso.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbUser;
import com.taobao.sso.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Reference
	private UserService userService;
	
	//检查数据是否存在
	@RequestMapping("check/{param}/{type}")
	public TaoBaoResult checkData(@PathVariable("param")String param,@PathVariable("type")int type){
		return userService.checkUserData(param, type);
	}
	//用户添加
	@RequestMapping(value="register",method=RequestMethod.POST)
	public TaoBaoResult addUser(TbUser tbUser){
		
		System.out.println("in addUser"+tbUser);
		return userService.addUser(tbUser);
		
	}
	
}
