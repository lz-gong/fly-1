package com.neusoft.bean;

import java.util.List;

public class PageDataModel<T> {
	private int total;
	private List<T> list;
	//����
	private String check; //������������и����жϵģ������û�δ��¼���������ó�һ������
	
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
