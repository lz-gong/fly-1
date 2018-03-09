package com.neusoft.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.neusoft.bean.Comment;
import com.neusoft.bean.CommentEx;
import com.neusoft.bean.PageDataModel;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.CommentServiceImp;
import com.neusoft.service.ICommentService;
import com.neusoft.service.ITopicService;
import com.neusoft.service.IUserService;
import com.neusoft.service.TopicServiceImp;
import com.neusoft.service.UserServiceImp;
import com.neusoft.utils.OtherUtil;

/**
 * Servlet implementation class AddCommentServlet
 */
@WebServlet("/addComment.do")
public class AddCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo userInfo=(Userinfo)request.getSession().getAttribute("userInfo");
		if(userInfo!=null) {
			int userId=userInfo.getId();
			Date createTime=new Date();
			String content = OtherUtil.ajaxPostTransformLink(request.getParameter("content"));
			int isTopic=0;  //0�������ӵ�����
			String temp=request.getParameter("topicId");
			System.out.println(temp);
			if(temp!=null) {  //���Ϊ��˵���û�ֱ����д��/addComment.do������û�в���
				try {
					int topicid=Integer.parseInt(temp);
					Comment comment=new Comment();
					comment.setTopicOrCommentId(topicid); //���������л�ȡ���ӵ�id
					comment.setCommentContent(content);
					comment.setCommentTime(createTime);
					comment.setUserid(userId);
					comment.setIsTopic(isTopic); 
					
					ICommentService ics=new CommentServiceImp();
					int arr[]=ics.addComment(comment);
					if(arr.length==2 && arr[0]==1) { //ע����if��Ĵ����getCommentListServlet�еĴ����������ͬ��
						//������������������
						ITopicService its=new TopicServiceImp();
						its.addTopicAnswerNum(topicid);
						//���ӷ����û�����һ��message
						ics.addMessage(arr[1], its.getUseridByTopicid(topicid));
						//�������۵��û�����1����
						IUserService ius=new UserServiceImp();
						String incExperience=request.getServletContext().getInitParameter("incExperience");
						String experience[]=incExperience.split(",");
						ius.increaseExperience(userId, Integer.parseInt(experience[7])); 
						
						response.getWriter().print("true");
					}else if(arr[0]==0) {
						//˵���ύʧ�ܣ����������ݿ����
						response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=ѽ���ύʧ����");
					}else if(arr.length==1) {
						//˵���û����ڽ��зǷ�����
						response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=��Ҫ�Ƿ�����Ŷ��");
					}
				} catch (NumberFormatException e) {
					//����˵����½�ˣ�����Servlet����Ĳ�������ƴ�ģ������ģ���������ת���쳣����������404
					response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=��ַ������ƴŶ��");
					e.printStackTrace();
				}
				
			}else {
				//����˵����½�ˣ�����ֱ�ӷ��ʵ����Servlet������404����
				response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=�����ˣ������߽ݾ��ɲ���");
			}
			
		}else {
			//�����û�е�½�������ۣ���ô��ֱ����ת����½����
			response.getWriter().print(request.getContextPath()+"/jsp/login.jsp");
		}
	}
}