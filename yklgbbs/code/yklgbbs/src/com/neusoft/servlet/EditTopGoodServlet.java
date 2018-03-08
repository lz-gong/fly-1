package com.neusoft.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.StandardSocketFactory;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.ITopicService;
import com.neusoft.service.TopicServiceImp;

/**
 * Servlet implementation class EditTopGoodServlet
 */
@WebServlet("/editTopGoodServlet.do")
public class EditTopGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		if(user!=null && user.getIsManager()==1) {
			String arr[]=request.getParameterValues("topAndGood");
			String temp=request.getParameter("topicid");
			if(temp!=null ) {
				int topicid=Integer.parseInt(temp);
				int isTop=0;
				int isGood=0;
				if(arr!=null) {
					for(int i=0;i<arr.length;i++) {  
						if(arr[i].equals("top")) {
							isTop=1;
							
						}
						if(arr[i].equals("good")) {
							isGood=1;
						}
					}
				}
//				System.out.println("isTop="+isTop+"  "+"isGood="+isGood);
				ITopicService its=new TopicServiceImp();
				response.setContentType("text/html;charset=utf-8");
				if(its.setTopicTopGood(topicid, isTop, isGood)) {
					response.getWriter().print(topicid+"�����ӱ�����Ϊ��"+(isGood==1?"�Ӿ�  ":"���Ӿ�  ")+(isTop==1?"����":"������"));
					response.getWriter().print("&nbsp;&nbsp;&nbsp;�뷵�غ�ˢ�²鿴");
				}else {
					response.getWriter().print("ѽ�����ù����г���δ֪����");
				}
			}
		}else {
			response.setContentType("text/html;charset=utf-8");
			if(user==null) {
				response.getWriter().print("���¼����в�����");
				return;
			}
			if(user.getIsManager()!=1) {
				response.getWriter().print("���úͼӾ���������ϵ����Ա��");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
