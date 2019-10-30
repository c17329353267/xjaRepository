package com.taobao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.taobao.mapper.TbItemParamItemMapper;
import com.taobao.pojo.TbItemParamItem;
import com.taobao.service.ItemParamService;

@Service
public class TbItemParamItemImpl implements ItemParamService{

	@Autowired
	private TbItemParamItemMapper itemParamMapper;
	@Override
	public TbItemParamItem findTbItemParmItemById(long id) {
		// TODO Auto-generated method stub
		return itemParamMapper.selectByPrimaryKey(id);
	}

}
