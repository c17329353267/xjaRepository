package com.taobao.service;

import java.util.List;


import com.taobao.pojo.EasyUITreeNode;

public interface ItemCatService {
		List<EasyUITreeNode> queryIsParent(long id);
}
