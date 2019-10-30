package com.taobao.redis.service;

import com.taobao.pojo.TbItem;
import com.taobao.pojo.TbItemDesc;

public interface RedisService {
	TbItem queryItemById(long id);
	TbItemDesc queryItemDescById(long id);
	
}
