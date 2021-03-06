package com.neusoft.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.bean.Userinfo;
import com.neusoft.service.IUserService;
import com.neusoft.service.UserServiceImp;
import com.neusoft.utils.TimeToStringUtil;

/**
 * Servlet implementation class SignServlet
 */
@WebServlet("/signServlet.do")
public class SignServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		if(user!=null) {
			int userid=user.getId();
			Date userLastSignTime=user.getSignTime();
			if(TimeToStringUtil.checkStringTimeIsEqulas(userLastSignTime, new Date())==false) {
				IUserService ius=new UserServiceImp();
				ius.updateSignDate(userid);
				//将session中的用户时间也更新一下，但是不能保证这个时间和数据库的一毫秒都不差
				user.setSignTime(new Date());
				response.getWriter().print(ius.increaseKissNum(userid, 5));
			}else {
				response.getWriter().print(false);
			}
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
