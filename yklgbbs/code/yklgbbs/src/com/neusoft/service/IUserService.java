package com.neusoft.service;

import java.util.Date;

import com.neusoft.bean.Userinfo;

public interface IUserService {
	//�����û�
	int addUser(Userinfo user);
	
	//�û���¼
	Userinfo findUser(String email,String passowrd);
	
	//�����û���Ϣ   //����0˵������ʧ�ܣ�����1�ɹ�
	int setUserInfo(Userinfo userInfo);
	
	//�û��޸����룺
	boolean changePass(int userid,String newpass);
	
	//����ͷ��
	boolean setHeadImg(int userid,String newHeadImg,String oldHeadImg,boolean isDefaultHeadimg);  //ע���Ժ���֤�Ƿ�ɹ�����boolean��ʡ��0��1�㲻���
	
	//�������Ӽ��ٶ�Ӧ�˻��ķ�����
	boolean decreaseKissNum(int userid,int deKissnum);
	
	//�����������Ӷ�Ӧ�˻��ķ�����
	boolean increaseKissNum(int userid,int inKissnum);

	//�����û���ǩ��ʱ��
	boolean updateSignDate(int userid);
	
	//���û����Ӿ���
	boolean increaseExperience(int userid,int incExperience);
}