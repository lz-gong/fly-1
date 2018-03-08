package com.neusoft.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import com.neusoft.bean.TopicInfoEx;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.ITopicService;
import com.neusoft.service.TopicServiceImp;
import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;

/**
 * Servlet implementation class EditTopicServlet
 */
@WebServlet("/editTopicServlet.do")
public class EditTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		if(user!=null) {
			String temp=request.getParameter("topicid");
			if(temp!=null) {
				try {
					int topicid=Integer.parseInt(temp);
					ITopicService its=new TopicServiceImp();
					if(its.checkTopicBelongAuthor(topicid, user.getId())) {
						TopicInfoEx topicInfoEx=its.getEditTopic(topicid);
						topicInfoEx.setId(topicid);
						request.setAttribute("topicInfoEx", topicInfoEx);
						request.getRequestDispatcher("/jsp/user/edit_topic.jsp").forward(request, response);;
					}else {
						response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}else {
				response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
			}
		}else {
			response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
