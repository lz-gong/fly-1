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
								//给评论设置为已采纳
								boolean check=ics.acceptComment(commentid);
								if(check) {
									IUserService ius=new UserServiceImp();
									//给帖子设置为已采纳
									its.acceptTopic(topicid);
									ius.increaseKissNum(ics.getCommentUserid(commentid), its.geTopictRewardKiss(topicid));	
									
									//给采纳贴主增加5经验
									String incExperience=request.getServletContext().getInitParameter("incExperience");
									String experience[]=incExperience.split(",");
									ius.increaseExperience(userid, Integer.parseInt(experience[3])); 
									
									//给被采纳者增加10经验
									//首先根据commentid获取评论者的id
									ius.increaseExperience(ics.getCommentUserid(commentid), Integer.parseInt(experience[5]));
									
									response.getWriter().print(check);
								}else {
									response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=呀！数据库出错了或评论在数据库中消失了");
								}
								
							}else {
								response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=不能采纳自己的评论哈！");
							}
						}else {
							response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=不可重复采纳");
						}
					}else {
						response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=不能采纳别人的帖子哈！");
					}
				}else {
					response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=呀！已经结贴了，不能再进行采纳了");
				}
			} catch (NumberFormatException e) {
				response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=呀！获取评论信息失败了，换个浏览器试试吧");
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
