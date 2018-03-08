package com.neusoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.ITopicService;
import com.neusoft.service.TopicServiceImp;

/**
 * Servlet implementation class CollectServlet
 */
@WebServlet("/collectServlet.do")
public class CollectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		response.setContentType("text/html;charset=utf-8"); //设置这个是为了防止中文乱码（在此处是多余的），英文是不会乱码的
		PrintWriter out=response.getWriter();
		if(user!=null) {
			String temp1=request.getParameter("topicid");
			String temp2=request.getParameter("collect_onOff");
			if(temp2.equals("0") || temp2.equals("1")) {
				try {
					int topicid=Integer.parseInt(temp1); //即使这里转换成功了，但是这个帖子可能可不存在！因为只要是数字就行
					ITopicService its=new TopicServiceImp();
					if(its.findTopic(topicid)) {
						if(temp2.equals("0")) {  //开始收藏帖子
							if(!its.checkRepeatCollect(topicid, user.getId())) {
								if(its.collectTopic(topicid, user.getId())) {
									response.getWriter().print("true");
								}else {
									out.print(request.getContextPath()+"/jsp/tips.jsp?tips=呀！收藏帖子失败了");
								}
							}else {
								out.print(request.getContextPath()+"/jsp/tips.jsp?tips=不可重复收藏哦！");
							}
						}
						if(temp2.equals("1")) {  //开始取消收藏帖子
							if(its.cancleCollectTopic(topicid, user.getId())) {
								response.getWriter().print("true");
							}else {
								out.print(request.getContextPath()+"/jsp/tips.jsp?tips=呀！取消收藏帖子失败了");
							}
						}
					}else {
						out.print(request.getContextPath()+"/jsp/404.jsp");
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
					out.print(request.getContextPath()+"/jsp/404.jsp");
				}
			}else {
				out.print(request.getContextPath()+"/jsp/404.jsp");
			}
		}else {
			out.print(request.getContextPath()+"/jsp/login.jsp");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
