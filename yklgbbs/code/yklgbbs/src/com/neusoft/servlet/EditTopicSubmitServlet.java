package com.neusoft.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.bean.Userinfo;
import com.neusoft.service.ITopicService;
import com.neusoft.service.TopicServiceImp;

/**
 * Servlet implementation class EditTopicSubmitServlet
 */
@WebServlet("/editTopicSubmitServlet.do")
public class EditTopicSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		if(user!=null) {
			String temp=request.getParameter("topicid");
			if(temp!=null) {
				try {
					int topicid=Integer.parseInt(temp);
					String title=request.getParameter("title");
					String content=request.getParameter("content");
					ITopicService its=new TopicServiceImp();
					if(its.editTopic(topicid, title, content)) {
						response.sendRedirect(request.getContextPath()+"/topicDetail.do?topicid="+topicid);
					}else {
						System.out.println("Ñ½£¡Ìû×ÓÐÞ¸ÄÊ§°ÜÁË");
						response.sendRedirect(request.getContextPath()+"/topicDetail.do?topicid="+topicid);
					}
				} catch (NumberFormatException e) {
					response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
					e.printStackTrace();
				}
			}else {
				response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
			}
		}else {
			response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
		}
	}

}
