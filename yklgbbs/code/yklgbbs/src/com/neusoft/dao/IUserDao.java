package com.neusoft.dao;

import java.util.Date;

import com.neusoft.bean.Userinfo;

public interface IUserDao {
	int addUser(Userinfo user);
	
	Userinfo findUser(String email,String password);
	
	int setUserInfo(Userinfo userInfo);
	
	boolean changePass(int userid,String newpass);
	
	//设置用户头像
	boolean setHeadImg(int userid,String newHeadImg);
	
	//删除旧的头像
	boolean deleteoldHeadimg(String oldHeadImg);
	
	//发表帖子减少对应账户的飞吻数
	boolean decreaseKissNum(int userid,int deKissnum);
	
	//采纳帖子增加对应账户的飞吻数
	boolean increaseKissNum(int userid,int inKissnum);
	
	//更新用户的签到时间
	boolean updateSignDate(int userid);
	
	//给用户增加经验
	boolean increaseExperience(int userid,int incExperience);
}
