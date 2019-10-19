package com.taobao.sso.service;

import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbUser;

//该接口用于接收用户前台传递过来的登录信息
public interface UserService {
	
	TaoBaoResult checkUserData(String param,int type);
	//用户添加
	TaoBaoResult addUser(TbUser tbUser);

}
