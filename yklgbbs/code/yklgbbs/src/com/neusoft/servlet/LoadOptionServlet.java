package com.neusoft.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.bean.Category;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.ITopicService;
import com.neusoft.service.TopicServiceImp;

/**
 * Servlet implementation class LoadOptionServlet
 */
@WebServlet("/loadOption.do")
public class LoadOptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo) request.getSession().getAttribute("userInfo");
		if(user!=null) {
			if(user.getKissNum()>=5) {
				//需要判断登陆后才能跳到这里，否则跳到登陆界面
				ITopicService its=new TopicServiceImp();
				List<Category> list=its.getCategoryInfo();
				if(list!=null && list.size()>0) {
					request.setAttribute("option", list);
					request.setAttribute("kissNum", user.getKissNum());
					request.getRequestDispatcher("/jsp/user/add_topic.jsp").forward(request, response);
				}else {
					//走到这里说明数据库中没有内容，或数据库出错，需要跳到错误页面（呀！数据库出错了，请稍后再试！）
				}
			}else {
				request.getRequestDispatcher("/jsp/tips.jsp?tips=呀！飞吻数不够了，签到或被采纳都可以获得飞吻哦").forward(request, response);
			}	
		}else {
			response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
		}
	}

}
