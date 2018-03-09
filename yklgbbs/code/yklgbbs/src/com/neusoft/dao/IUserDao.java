package com.neusoft.dao;

import java.util.Date;

import com.neusoft.bean.Userinfo;

public interface IUserDao {
	int addUser(Userinfo user);
	
	Userinfo findUser(String email,String password);
	
	int setUserInfo(Userinfo userInfo);
	
	boolean changePass(int userid,String newpass);
	
	//�����û�ͷ��
	boolean setHeadImg(int userid,String newHeadImg);
	
	//ɾ���ɵ�ͷ��
	boolean deleteoldHeadimg(String oldHeadImg);
	
	//�������Ӽ��ٶ�Ӧ�˻��ķ�����
	boolean decreaseKissNum(int userid,int deKissnum);
	
	//�����������Ӷ�Ӧ�˻��ķ�����
	boolean increaseKissNum(int userid,int inKissnum);
	
	//�����û���ǩ��ʱ��
	boolean updateSignDate(int userid);
	
	//���û����Ӿ���
	boolean increaseExperience(int userid,int incExperience);
}