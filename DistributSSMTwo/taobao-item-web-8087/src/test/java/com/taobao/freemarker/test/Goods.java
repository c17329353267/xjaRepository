package com.taobao.freemarker.test;

public class Goods {

	private Integer goodId;
	private String goodName;
	private String goodPrice;
	public Integer getGoodId() {
		return goodId;
	}
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}
	public Goods(Integer goodId, String goodName, String goodPrice) {
		super();
		this.goodId = goodId;
		this.goodName = goodName;
		this.goodPrice = goodPrice;
	}
	public Goods() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Goods [goodId=" + goodId + ", goodName=" + goodName + ", goodPrice=" + goodPrice + "]";
	}
	
}
