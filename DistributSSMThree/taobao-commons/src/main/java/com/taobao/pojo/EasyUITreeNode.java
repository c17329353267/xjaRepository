package com.taobao.pojo;

import java.io.Serializable;

/**
 * 
 * @author 陈成
 *
 * 2019年10月8日
 * 返回后台管理端树状结构实体类
 */
public class EasyUITreeNode implements Serializable{

	private long  id;		//id
	private String text;	//显示的节点文本
	private String state;	//节点的状态 open : closed
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public EasyUITreeNode(long id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}
	public EasyUITreeNode() {
		super();
		// TODO Auto-generated constructor stub
	}
}
