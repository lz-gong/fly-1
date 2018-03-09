package com.neusoft.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.neusoft.bean.CommentEx;
import com.neusoft.bean.TopicInfoEx;
import com.neusoft.bean.Userinfo;
import com.neusoft.dao.ITopicDao;
import com.neusoft.dao.TopicDaoImp;
import com.neusoft.service.CommentServiceImp;
import com.neusoft.service.ICommentService;
import com.neusoft.service.ITopicService;
import com.neusoft.service.TopicServiceImp;

/**
 * Servlet implementation class TopicDetailServlet
 */
@WebServlet("/topicDetail.do")
public class TopicDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String temp=request.getParameter("topicid");
		if(temp!=null) {
			
			try {
				int topicid=Integer.parseInt(temp); //������Ϊ�˷�ֹ�û��ֶ�ƴдid�����Ҽ���������				
				
				//����Ҫ���ʵ����ӣ��������Ӵ����ӵ��Ķ���
				ITopicService its=new TopicServiceImp();

				TopicInfoEx topicInfoEx=its.topicShowView(topicid);
				if(topicInfoEx==null) {
					//���ݿ�鲻����أ����ݣ����ӡ���ѽ�������ˣ����������������԰ɣ���
					response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
					return;
				}
				
				//�������������˵��Ҫ��һ��������Servlet��ajax����
				
//				ICommentService ics=new CommentServiceImp();
//				List<CommentEx> commentEx=ics.getAllComment(topicid);
				

				if(topicInfoEx!=null) {
					Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
					if(user!=null) {
						request.setAttribute("ifCollect", its.checkRepeatCollect(topicid, user.getId()));
					}
					request.setAttribute("isAccepted", its.checkTopicIsaccepted(topicid));
					request.setAttribute("topicDetail", topicInfoEx);
//					request.setAttribute("commentEx", commentEx);
					request.getRequestDispatcher("/jsp/detail.jsp").forward(request, response);
				}
			} catch (NumberFormatException e) {
				//ֱ�ӷ��ʽ��зǷ������� ��������error���棬��404.jsp() ���£�
				response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
			}
		}else {
			response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
		}
	}

}