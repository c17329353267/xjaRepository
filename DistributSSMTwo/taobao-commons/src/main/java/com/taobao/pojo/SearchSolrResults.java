package com.taobao.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author 陈成
 *
 * 2019年10月11日
 * 从solr中查询出数据进行分页封装
 */
public class SearchSolrResults implements Serializable{

	//封装分页总数据
	private List<SearchSolrItem> listSearchItems;
	//总记录数
	private long recordCount;
	//数据总条数
	private long pageCount;
	//页面容量大小即一个页面显示多少条记录
	private long pageSize=60;
	public List<SearchSolrItem> getListSearchItems() {
		return listSearchItems;
	}
	public void setListSearchItems(List<SearchSolrItem> listSearchItems) {
		this.listSearchItems = listSearchItems;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public long getPageCount() {
		if(recordCount%pageSize==0){
			return recordCount/pageSize;
		}else {
			return recordCount/pageSize+1;
		}
		
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	@Override
	public String toString() {
		return "SearchSolrResults [listSearchItems=" + listSearchItems + ", recordCount=" + recordCount + ", pageCount="
				+ pageCount + "]";
	}
	
	
}
