package com.taobao.sso.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.oracle.util.CookieUtils;
import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbUser;
import com.taobao.sso.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Reference
	private UserService userService;
	
	@Value("${cookiekey}")
	private String cookieKey;
	//检查数据是否存在
	@RequestMapping("check/{param}/{type}")
	public TaoBaoResult checkData(@PathVariable("param")String param,@PathVariable("type")int type){
		return userService.checkUserData(param, type);
	}
	//用户添加
	@RequestMapping(value="register",method=RequestMethod.POST)
	public TaoBaoResult addUser(TbUser tbUser){
		
		return userService.addUser(tbUser);
		
	}
	//用户登录
	@RequestMapping(value="login",method=RequestMethod.POST)
	public TaoBaoResult login(String username,String password,HttpServletRequest request,HttpServletResponse response){
		//System.out.println("cookiekey:"+cookieKey);
		TaoBaoResult result = userService.login(username, password);
		String data = (String)result.getData();
		CookieUtils.setCookie(request, response, cookieKey, data);
		return TaoBaoResult.build(200, "OK", data);
	}
	//根据redis中的token是否存在查询用户登录信息
	@RequestMapping(value="token")
	public TaoBaoResult getUserByToken(String token){//a70fcd45-3f6f-4589-a749-1da63869f4f6
		return userService.getUserByToken(token);
	}
	//按全退出 即删除token
	@RequestMapping(value="delToken")
	public TaoBaoResult delUserByToken(String token){
		return userService.delToken(token);
	}
	
}
