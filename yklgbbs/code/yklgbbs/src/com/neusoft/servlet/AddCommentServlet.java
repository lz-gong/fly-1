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
			int isTopic=0;  //0代表帖子的评论
			String temp=request.getParameter("topicId");
			System.out.println(temp);
			if(temp!=null) {  //如果为空说明用户直接手写的/addComment.do，后面没有参数
				try {
					int topicid=Integer.parseInt(temp);
					Comment comment=new Comment();
					comment.setTopicOrCommentId(topicid); //在隐藏域中获取帖子的id
					comment.setCommentContent(content);
					comment.setCommentTime(createTime);
					comment.setUserid(userId);
					comment.setIsTopic(isTopic); 
					
					ICommentService ics=new CommentServiceImp();
					int arr[]=ics.addComment(comment);
					if(arr.length==2 && arr[0]==1) { //注：本if块的代码和getCommentListServlet中的代码基本是相同的
						//给帖子增加评论数量
						ITopicService its=new TopicServiceImp();
						its.addTopicAnswerNum(topicid);
						//增加发帖用户新增一个message
						ics.addMessage(arr[1], its.getUseridByTopicid(topicid));
						//给发评论的用户增加1经验
						IUserService ius=new UserServiceImp();
						String incExperience=request.getServletContext().getInitParameter("incExperience");
						String experience[]=incExperience.split(",");
						ius.increaseExperience(userId, Integer.parseInt(experience[7])); 
						
						response.getWriter().print("true");
					}else if(arr[0]==0) {
						//说明提交失败，可能是数据库错误
						response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=呀！提交失败了");
					}else if(arr.length==1) {
						//说明用户正在进行非法回帖
						response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=不要非法回帖哦！");
					}
				} catch (NumberFormatException e) {
					//这里说明登陆了，但是Servlet后面的参数是乱拼的，如中文，导致数字转换异常，这里跳到404
					response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=地址不能乱拼哦！");
					e.printStackTrace();
				}
				
			}else {
				//这里说明登陆了，但是直接访问的这个Servlet，弹出404界面
				response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=年轻人！总想走捷径可不行");
			}
			
		}else {
			//如果用没有登陆就想评论，那么就直接跳转到登陆界面
			response.getWriter().print(request.getContextPath()+"/jsp/login.jsp");
		}
	}
}
