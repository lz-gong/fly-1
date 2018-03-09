package com.neusoft.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.bean.Userinfo;
import com.neusoft.service.CommentServiceImp;
import com.neusoft.service.ICommentService;
import com.neusoft.service.ITopicService;
import com.neusoft.service.IUserService;
import com.neusoft.service.TopicServiceImp;
import com.neusoft.service.UserServiceImp;

/**
 * Servlet implementation class AcceptServlet
 */
@WebServlet("/acceptServlet.do")
public class AcceptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		if(user!=null) {
			int userid=user.getId();
			String temp1=request.getParameter("commentid");
			String temp2=request.getParameter("topicid");
			try {
				int commentid=Integer.parseInt(temp1);
				int topicid=Integer.parseInt(temp2);
				ITopicService its=new TopicServiceImp();
				if(its.checkTopicIsend(topicid)==false) {
					if(its.checkTopicBelongAuthor(topicid, userid)) {
						if(its.checkTopicIsaccepted(topicid)==false) {
							ICommentService ics=new CommentServiceImp();
							if(ics.checkCommentBelongAuthor(commentid, userid)==false) {
								//����������Ϊ�Ѳ���
								boolean check=ics.acceptComment(commentid);
								if(check) {
									IUserService ius=new UserServiceImp();
									//����������Ϊ�Ѳ���
									its.acceptTopic(topicid);
									ius.increaseKissNum(ics.getCommentUserid(commentid), its.geTopictRewardKiss(topicid));	
									
									//��������������5����
									String incExperience=request.getServletContext().getInitParameter("incExperience");
									String experience[]=incExperience.split(",");
									ius.increaseExperience(userid, Integer.parseInt(experience[3])); 
									
									//��������������10����
									//���ȸ���commentid��ȡ�����ߵ�id
									ius.increaseExperience(ics.getCommentUserid(commentid), Integer.parseInt(experience[5]));
									
									response.getWriter().print(check);
								}else {
									response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=ѽ�����ݿ�����˻����������ݿ�����ʧ��");
								}
								
							}else {
								response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=���ܲ����Լ������۹���");
							}
						}else {
							response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=�����ظ�����");
						}
					}else {
						response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=���ܲ��ɱ��˵����ӹ���");
					}
				}else {
					response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=ѽ���Ѿ������ˣ������ٽ��в�����");
				}
			} catch (NumberFormatException e) {
				response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=ѽ����ȡ������Ϣʧ���ˣ�������������԰�");
			}
		}else{
			response.getWriter().print(request.getContextPath()+"/jsp/login.jsp");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}