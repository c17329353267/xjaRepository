package com.taobao.pojo;

import java.io.Serializable;

/**
 * 
 * @author 陈成
 *
 * 2019年10月11日
 * 此实体类用作封装查询图片的基本信息
 */
public class SearchSolrItem implements Serializable{

	private String id;
	private String image;
	private long price;
	private String title;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "SearchSolrItem [id=" + id + ", image=" + image + ", price=" + price + ", title=" + title + "]";
	}
	
}
