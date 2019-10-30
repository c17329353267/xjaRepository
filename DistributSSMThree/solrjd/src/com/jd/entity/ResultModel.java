package com.jd.entity;

import java.util.List;

public class ResultModel {
	private List<Products> productList;
	private Long recordCount;//������
	private Long curPage; //��ǰҳ
	private Long pageCount; //ÿҳ����
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