package com.neusoft.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.bean.Userinfo;
import com.neusoft.service.HomeServiceImp;
import com.neusoft.service.IHomeService;
import com.neusoft.service.ITopicService;
import com.neusoft.service.TopicServiceImp;

/**
 * Servlet implementation class GoHomeServlet
 */
@WebServlet("/home/*")
public class GoHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath=request.getRequestURI();
		String temp=servletPath.substring(14);
		try {
			int userId=Integer.parseInt(temp);
			IHomeService ihs=new HomeServiceImp();
			Userinfo userinfo=ihs.getHomeUserinfo(userId);	
			if(userinfo!=null) {
				//获取用户的发帖与回答信息
				ITopicService its=new TopicServiceImp();
				request.setAttribute("userNearQuestionList", its.getUserNearQuestionList(userId));
				request.setAttribute("userNearAnswerList", its.getUserNearAnswerList(userId));
				//获取本页面用户的信息
				request.setAttribute("homeUserinfo", userinfo);
				request.getRequestDispatcher("/jsp/home_page.jsp").forward(request, response);;
			}else {
				response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
			}
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
