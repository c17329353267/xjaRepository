package com.taobao.sso.service;

import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbUser;

//该接口用于接收用户前台传递过来的登录信息
public interface UserService {
	
	TaoBaoResult checkUserData(String param,int type);
	//用户添加 注册
	TaoBaoResult addUser(TbUser tbUser);
	
	//用户登录
	TaoBaoResult login(String username,String password);
	
	//通过token查询用户信息
	TaoBaoResult getUserByToken(String token);
	
	//安全退出 删除token
	TaoBaoResult delToken(String token);

}
