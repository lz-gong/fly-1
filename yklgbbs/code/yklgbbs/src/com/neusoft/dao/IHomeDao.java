package com.neusoft.dao;

import com.neusoft.bean.Userinfo;

public interface IHomeDao {
	Userinfo getHomeUserinfo(int userId);
}
