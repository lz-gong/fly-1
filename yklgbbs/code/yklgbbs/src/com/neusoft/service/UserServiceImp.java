package com.neusoft.service;

import java.util.Date;

import com.neusoft.bean.Userinfo;
import com.neusoft.dao.IUserDao;
import com.neusoft.dao.UserDaoImp;

public class UserServiceImp implements IUserService{

	@Override
	public int addUser(Userinfo user) {
		//调用dao层注册用户
		IUserDao ius=new UserDaoImp();
		int rel=ius.addUser(user);
		return rel;
	}

	@Override
	public Userinfo findUser(String email, String password) {
		IUserDao iud=new UserDaoImp();
		Userinfo user=iud.findUser(email, password);
		return user;
	}

	@Override
	public int setUserInfo(Userinfo userInfo) {
		IUserDao iud=new UserDaoImp();
		int num=iud.setUserInfo(userInfo);
		return num;
	}

	@Override
	public boolean changePass(int userid,String newpass) {
		IUserDao iud=new UserDaoImp();
		return iud.changePass(userid, newpass);
	}

	@Override
	public boolean setHeadImg(int userid, String newHeadImg,String oldHeadImg, boolean isDefaultHeadimg) {
		IUserDao ius=new UserDaoImp();
		boolean temp=ius.setHeadImg(userid, newHeadImg);
		if(isDefaultHeadimg==false) {
			if(ius.deleteoldHeadimg(oldHeadImg)) {
				System.out.println("用户之前设置过头像，已经删除！");
			}
		}
		return temp;
	}

	@Override
	public boolean decreaseKissNum(int userid, int deKissnum) {
		IUserDao ius=new UserDaoImp();
		return ius.decreaseKissNum(userid, deKissnum);
	}

	@Override
	public boolean increaseKissNum(int userid, int inKissnum) {
		IUserDao ius=new UserDaoImp();
		return ius.increaseKissNum(userid, inKissnum);
	}

	@Override
	public boolean updateSignDate(int userid) {
		IUserDao ius=new UserDaoImp();
		return ius.updateSignDate(userid);
	}

	@Override
	public boolean increaseExperience(int userid, int incExperience) {
		IUserDao ius=new UserDaoImp();
		return ius.increaseExperience(userid, incExperience);
	}

}
