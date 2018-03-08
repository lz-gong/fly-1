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
 * Servlet implementation class DeleteTopicServlet
 */
@WebServlet("/deleteTopicServlet.do")
public class DeleteTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		
		if(user!=null && user.getIsManager()==1) {
			String temp=request.getParameter("topicid");
			try {
				int topicid=Integer.parseInt(temp);
				ITopicService its=new TopicServiceImp();
				boolean check=its.deleteTopic(topicid);
				response.setContentType("text/html;charset=utf-8"); //设置这个是为了防止中文乱码（在此处是多余的），英文是不会乱码的
				if(check==true) {
					response.getWriter().print("success!");
					return;
				}
				if(check==false) {
					response.getWriter().print("delete failed!");
					return;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.out.println("帖子id不可转换！");
				response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
				return;
			}
		}else {
			response.setContentType("text/html;charset=utf-8");
			if(user==null) {
				response.getWriter().print("请登录后进行操作！");
				return;
			}
			if(user.getIsManager()!=1) {
				response.getWriter().print("删除操作请联系管理员！");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
