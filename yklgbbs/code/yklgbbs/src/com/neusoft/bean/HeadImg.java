package com.neusoft.bean;

public class HeadImg {
	private int status;  // �Ƿ��ϴ��ɹ�����user.js���ÿ��д�� ajax��Ҫ�õ���0����ɹ������������ûʲô��
	private String src;	 // ͷ���·��
	private String msg;  // ��ʾ������Ϣ���������ûʲô��
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
