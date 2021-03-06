package com.neusoft.service;

import java.util.Date;

import com.neusoft.bean.Userinfo;

public interface IUserService {
	//添加用户
	int addUser(Userinfo user);
	
	//用户登录
	Userinfo findUser(String email,String passowrd);
	
	//设置用户信息   //返回0说明添加失败，返回1成功
	int setUserInfo(Userinfo userInfo);
	
	//用户修改密码：
	boolean changePass(int userid,String newpass);
	
	//设置头像
	boolean setHeadImg(int userid,String newHeadImg,String oldHeadImg,boolean isDefaultHeadimg);  //注：以后验证是否成功都用boolean，省的0和1搞不清楚
	
	//发表帖子减少对应账户的飞吻数
	boolean decreaseKissNum(int userid,int deKissnum);
	
	//采纳帖子增加对应账户的飞吻数
	boolean increaseKissNum(int userid,int inKissnum);

	//更新用户的签到时间
	boolean updateSignDate(int userid);
	
	//给用户增加经验
	boolean increaseExperience(int userid,int incExperience);
}
