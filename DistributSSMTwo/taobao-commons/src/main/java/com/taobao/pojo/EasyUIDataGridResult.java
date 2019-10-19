package com.taobao.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体
 * @author mdlge
 *
 */
public class EasyUIDataGridResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private long total;	//总数据条数
	private List<?> rows;//数据详细信息  把所有信息封装到list集合
	
	
	public EasyUIDataGridResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EasyUIDataGridResult(long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "EasyUIDataGridResult [total=" + total + ", rows=" + rows + "]";
	}
	
}
