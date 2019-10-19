package com.jd.entity;

import java.util.List;

public class ResultModel {
	private List<Products> productList;
	private Long recordCount;//总条数
	private Long curPage; //当前页
	private Long pageCount; //每页条数
	public List<Products> getProductList() {
		return productList;
	}
	public void setProductList(List<Products> productList) {
		this.productList = productList;
	}
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	public Long getCurPage() {
		return curPage;
	}
	public void setCurPage(Long curPage) {
		this.curPage = curPage;
	}
	public Long getPageCount() {
		return pageCount;
	}
	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}
	@Override
	public String toString() {
		return "ResultModel [productList=" + productList + ", recordCount=" + recordCount + ", curPage=" + curPage
				+ ", pageCount=" + pageCount + "]";
	}
	
}