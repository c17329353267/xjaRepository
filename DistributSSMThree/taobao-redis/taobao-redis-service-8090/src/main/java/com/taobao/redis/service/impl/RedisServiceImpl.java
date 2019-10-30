package com.taobao.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.mapper.TbItemDescMapper;
import com.taobao.mapper.TbItemMapper;
import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;
import com.taobao.redis.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService{

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Override
	public TbItem queryItemById(long id) {
		// TODO Auto-generated method stub
		return itemMapper.selectByPrimaryKey(id);
	}

	@Override
	public TbItemDesc queryItemDescById(long id) {
		// TODO Auto-generated method stub
		return itemDescMapper.selectByPrimaryKey(id);
	}

}
