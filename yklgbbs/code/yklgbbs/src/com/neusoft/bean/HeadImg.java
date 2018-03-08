package com.neusoft.bean;

public class HeadImg {
	private int status;  // 是否上传成功（在user.js中用框架写的 ajax中要用到，0代表成功），这个基本没什么用
	private String src;	 // 头像的路径
	private String msg;  // 提示错误信息，这个基本没什么用
	
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
