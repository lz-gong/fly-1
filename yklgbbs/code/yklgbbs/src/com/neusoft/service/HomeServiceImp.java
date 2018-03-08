package com.neusoft.service;

import com.neusoft.bean.Userinfo;
import com.neusoft.dao.HomeDaoImp;
import com.neusoft.dao.IHomeDao;

public class HomeServiceImp implements IHomeService{

	@Override
	public Userinfo getHomeUserinfo(int userId) {
		IHomeDao ihd=new HomeDaoImp();
		Userinfo userinfo=ihd.getHomeUserinfo(userId);
		return userinfo;
	}
}
