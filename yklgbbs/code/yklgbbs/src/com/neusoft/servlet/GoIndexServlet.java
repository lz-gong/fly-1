package com.neusoft.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.neusoft.bean.TopicInfoEx;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.ITopicService;
import com.neusoft.service.TopicServiceImp;
import com.sun.glass.ui.Application;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

/**
 * Servlet implementation class GoIndexServlet
 */
@WebServlet("/goIndex/*")
public class GoIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String temp=request.getRequestURI();
		String jumpTarget="";
		jumpTarget = temp.substring(16);
		
		ITopicService its=new TopicServiceImp();
		request.setAttribute("weekAnswerList", JSON.toJSONString(its.getWeekAnswerList()));
		request.setAttribute("hotViewList", JSON.toJSONString(its.getHotViewList()));
		request.setAttribute("hotReplyList", JSON.toJSONString(its.getHotReplyList()));
		request.setAttribute("kissNumList", JSON.toJSONString(its.getKissNumList()));
		
/*		Cookie cookies[]=request.getCookies();
		if(cookies!=null && cookies.length>0) {
			for(int i=0;i<cookies.length;i++) {
				if(cookies[i].getName().equals("weekAnswerList")) {
					System.out.println("if-if1");
					break;
				}
				if(i==cookies.length-1) {
					System.out.println("if-if2");
					Cookie cookie1=new Cookie("weekAnswerList", JSON.toJSONString(its.getWeekAnswerList()));
					cookie1.setPath(request.getContextPath());
					response.addCookie(cookie1);
					Cookie cookie4=new Cookie("kissNumList", JSON.toJSONString(its.getKissNumList()));
					cookie4.setPath(request.getContextPath());
					response.addCookie(cookie4);
					Cookie cookie2=new Cookie("hotViewList", JSON.toJSONString(its.getHotViewList()));
					cookie2.setPath(request.getContextPath());
					response.addCookie(cookie2);
					Cookie cookie3=new Cookie("hotReplyList", JSON.toJSONString(its.getHotReplyList()));
					cookie3.setPath(request.getContextPath());
					response.addCookie(cookie3);
				}
			}
		}else {
			System.out.println("else-if2");
			Cookie cookie1=new Cookie("weekAnswerList",JSON.toJSONString(its.getWeekAnswerList()));
			cookie1.setPath(request.getContextPath());
			response.addCookie(cookie1);
			Cookie cookie4=new Cookie("kissNumList", JSON.toJSONString(its.getKissNumList()));
			cookie4.setPath(request.getContextPath());
			response.addCookie(cookie4);
			Cookie cookie2=new Cookie("hotViewList", JSON.toJSONString(its.getHotViewList()));
			cookie2.setPath(request.getContextPath());
			response.addCookie(cookie2);
			Cookie cookie3=new Cookie("hotReplyList", JSON.toJSONString(its.getHotReplyList()));
			cookie3.setPath(request.getContextPath());
			response.addCookie(cookie3);
		}*/
		
		if(jumpTarget.equals("/all") || jumpTarget.equals("")) {
//			ITopicService its=new TopicServiceImp();
			List<TopicInfoEx> list1=its.getTopicInfoExList(0, 20);
			List<TopicInfoEx> list2=its.getTopTopicInfoExList();
			if(list1!=null && list2!=null) {
				request.setAttribute("jumpTarget", "all");
				request.setAttribute("topicList", list1);
				request.setAttribute("topTopicList", list2);
				request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("/jsp/tips.jsp?tips=呀！数据库出问题了，正在努力解决中...").forward(request, response);
			}
		}else if(jumpTarget.equals("/naccept")){ //未采纳
//			ITopicService its=new TopicServiceImp();
			List<TopicInfoEx> list1=its.getNotAcceptTopicInfoExList(0, 20);
			if(list1!=null) {
				request.setAttribute("jumpTarget", "naccept");
				request.setAttribute("topicList", list1);
				request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("/jsp/tips.jsp?tips=呀！数据库出问题了，正在努力解决中...").forward(request, response);
			}
		}else if(jumpTarget.equals("/accept")){  //已采纳
//			ITopicService its=new TopicServiceImp();
			List<TopicInfoEx> list1=its.getAcceptTopicInfoExList(0, 20);
			if(list1!=null) {
				request.setAttribute("jumpTarget", "accept");
				request.setAttribute("topicList", list1);
				request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("/jsp/tips.jsp?tips=呀！数据库出问题了，正在努力解决中...").forward(request, response);
			}
		}else if(jumpTarget.equals("/nice")) {   //精帖
//			ITopicService its=new TopicServiceImp();
			List<TopicInfoEx> list1=its.getNiceTopicInfoExList(0, 20);
			if(list1!=null) {
				request.setAttribute("jumpTarget", "nice");
				request.setAttribute("topicList", list1);
				request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("/jsp/tips.jsp?tips=呀！数据库出问题了，正在努力解决中...").forward(request, response);
			}
		}else if(jumpTarget.equals("/mytopic")) { //我的帖子
			Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
			if(user!=null) {
//				ITopicService its=new TopicServiceImp();
				List<TopicInfoEx> list1=its.getMyTopicTopicInfoExList(0, 20 , user.getId());
				if(list1!=null) {
					request.setAttribute("jumpTarget", "mytopic");
					request.setAttribute("topicList", list1);
					request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
				}else {
					request.getRequestDispatcher("/jsp/tips.jsp?tips=呀！数据库出问题了，正在努力解决中...").forward(request, response);
				}
			}else {
				response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
			}
		}else if(jumpTarget.startsWith("/search")) {
			String temp1=request.getParameter("searchStr");
			if(temp1!=null && temp1.trim().equals("")==false) {
				String searchStr=temp1.trim();
//				ITopicService its=new TopicServiceImp();
				List<TopicInfoEx> list1=its.getSearchTopicInfoExList(0, 20,searchStr);
				if(list1!=null) {
					request.setAttribute("jumpTarget", "search");
					request.setAttribute("searchStr", temp1);
					request.setAttribute("topicList", list1);
					request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
					
				}else {
					request.getRequestDispatcher("/jsp/tips.jsp?tips=呀！数据库出问题了，正在努力解决中...").forward(request, response);
				}
			}else {
				request.getRequestDispatcher("/jsp/tips.jsp?tips=输入点内容再搜索吧！").forward(request, response);
			}
			
		}else {
			response.sendRedirect(request.getContextPath()+"/goIndex/all");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
