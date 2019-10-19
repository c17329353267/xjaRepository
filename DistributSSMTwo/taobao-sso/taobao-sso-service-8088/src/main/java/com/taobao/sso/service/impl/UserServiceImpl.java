package com.taobao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.taobao.mapper.TbUserMapper;
import com.taobao.pojo.TaoBaoResult;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.TbUserExample;
import com.taobao.pojo.TbUserExample.Criteria;
import com.taobao.sso.service.UserService;
/**
 * @author 陈成
 *
 * 2019年10月17日
 * 该实现类用于判断用户登录从前台传递过来的参数进行判断
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private TbUserMapper tbUserMapper;
	@Override
	public TaoBaoResult checkUserData(String param, int type) {
		// TODO Auto-generated method stub
		TbUserExample example = new TbUserExample();
		Criteria createCriteria = example.createCriteria();
		if(type ==1){
			createCriteria.andUsernameEqualTo(param);
		}else if (type == 2) {
			createCriteria.andPhoneEqualTo(param);
		}else if (type==3) {
			createCriteria.andEmailEqualTo(param);
		}else {
			return TaoBaoResult.build(200, "参数类型不符合要求", null);
		}
		List<TbUser> userResult = tbUserMapper.selectByExample(example);
		if(userResult != null && userResult.size() !=0){
			//根据返回值确认该字段在数据库中是否已经注册过
			return TaoBaoResult.ok(false);
		}
		//返回true标识可以注册
		return TaoBaoResult.ok(true);
	}
	//添加用户
	@Override
	public TaoBaoResult addUser(TbUser tbUser) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(tbUser.getUsername())){
			return TaoBaoResult.build(400, "用户名不能为空");
		}
		if(StringUtils.isEmpty(tbUser.getPassword())){
			return TaoBaoResult.build(400, "密码不能为空");
		}
		if(StringUtils.isEmpty(tbUser.getEmail())){
			return TaoBaoResult.build(400, "邮箱不能为空");
		}if(StringUtils.isEmpty(tbUser.getPhone())){
			return TaoBaoResult.build(400, "手机号不能为空");
		}
		
		if(!(boolean)checkUserData(tbUser.getUsername(),1).getData()){
			return TaoBaoResult.build(400, "用户名已经存在");
		}
		if(!(boolean)checkUserData(tbUser.getPhone(),2).getData()){
			return TaoBaoResult.build(400, "手机号已经存在");
		}
		if(!(boolean)checkUserData(tbUser.getEmail(),3).getData()){
			return TaoBaoResult.build(400, "邮箱已经存在");
		}
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());
		tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
		tbUserMapper.insert(tbUser);
		return TaoBaoResult.build(200, "注册成功", true) ;
	}

}
