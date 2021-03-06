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
				int topicid=Integer.parseInt(temp); //这里是为了防止用户手动拼写id，并且加入了中文				
				
				//返回要访问的帖子，并且增加此帖子的阅读量
				ITopicService its=new TopicServiceImp();

				TopicInfoEx topicInfoEx=its.topicShowView(topicid);
				if(topicInfoEx==null) {
					//数据库查不到相关（数据）贴子。（呀！出错了，访问其他帖子试试吧！）
					response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
					return;
				}
				
				//下面的事正常来说是要用一个单独的Servlet和ajax做的
				
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
				//直接访问进行非法操作！ 这里跳到error界面，或404.jsp() 如下：
				response.sendRedirect(request.getContextPath()+"/jsp/404.jsp");
			}
		}else {
			response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
		}
	}

}
