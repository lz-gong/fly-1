package com.neusoft.bean;

import java.util.List;

public class PageDataModel<T> {
	private int total;
	private List<T> list;
	//新增
	private String check; //这个是用来进行各种判断的，例如用户未登录，我们设置成一个数字
	
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}
