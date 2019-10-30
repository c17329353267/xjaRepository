package com.taobao.item.bo;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import com.alibaba.dubbo.rpc.protocol.thrift.ThriftUtils;
import com.taobao.pojo.TbItem;

/**
 * @author 陈成
 *
 * 2019年10月15日
 * 此实体类用于封装Tbitem中未存在的images数组
 */
public class BOItem extends TbItem{

	public BOItem(TbItem tbItem) {
		this.setId(tbItem.getId());
		this.setCid(tbItem.getCid());
		this.setTitle(tbItem.getTitle());
		this.setBarcode(tbItem.getBarcode());
		this.setCreated(tbItem.getCreated());
		this.setImage(tbItem.getImage());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(tbItem.getStatus());
		this.setUpdated(tbItem.getUpdated());
		
	}
	public BOItem(){}
	public String[] getImages(){
		String image = this.getImage();
		if(image != null && !"".equals(image)){
			return image.split(",");
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
