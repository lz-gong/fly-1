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
 * Servlet implementation class DeleteCommentServlet
 */
@WebServlet("/deleteCommentServlet.do")
public class DeleteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo) request.getSession().getAttribute("userInfo");
		if(user!=null) {
			int userid=user.getId();
			String temp1=request.getParameter("commentid");
			String temp2=request.getParameter("topicid");
			try {
				int commentid=Integer.parseInt(temp1);
				int topicid=Integer.parseInt(temp2);
				ITopicService its=new TopicServiceImp();
				if(its.checkTopicIsend(topicid)==false) {
					ICommentService ics=new CommentServiceImp();
					if(ics.checkCommentBelongAuthor(commentid, userid)) {
							boolean temp=ics.deleteComment(commentid);
							if(temp==true) {
								//�����Ӽ�����������
								its.decTopicAnswerNum(topicid);
							}
							response.getWriter().print(temp);

					}else {
						response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=����ɾ�����˵����۹���");
					}
				}else {
					response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=ѽ���Ѿ������ˣ����ܽ���ɾ��������");
				}
			} catch (NumberFormatException e) {
				response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=ѽ����ȡ������Ϣʧ���ˣ�������������԰�");
			}
		}else {
			response.getWriter().print(request.getContextPath()+"/jsp/login.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
