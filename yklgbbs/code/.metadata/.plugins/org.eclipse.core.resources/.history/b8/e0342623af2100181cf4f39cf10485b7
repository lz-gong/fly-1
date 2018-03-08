package com.neusoft.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.bean.Comment;
import com.neusoft.bean.Userinfo;
import com.neusoft.dao.CommentDaoImp;
import com.neusoft.dao.ICommentDao;
import com.neusoft.utils.OtherUtil;

/**
 * Servlet implementation class AddCommentCommentServlet
 */
@WebServlet("/addCommentCommentServlet.do")
public class AddCommentCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		if(user!=null) {
			int userId=user.getId();
			String temp1=request.getParameter("commentid");
			String temp2=OtherUtil.ajaxPostTransformLink(request.getParameter("content"));
			if(temp1!=null && temp2!=null && temp2.trim().equals("")==false ) {
				try {
					int commentid=Integer.parseInt(temp1);
					String content=temp2.trim();
					Comment comment=new Comment();
					comment.setTopicOrCommentId(commentid); //在隐藏域中获取帖子的id
					comment.setCommentContent(content);
					comment.setCommentTime(new Date());
					comment.setUserid(userId);
					comment.setIsTopic(1);
					ICommentDao icd=new CommentDaoImp();
					int arr[]=icd.addComment(comment);
//					if(arr.length!=1 && arr[1]==1) {
					if(arr.length!=1) {
						response.getWriter().print(true);
					}else {
						response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=呀！服务器出错了");
					}
					
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else {
				response.getWriter().print(request.getContextPath()+"/jsp/tips.jsp?tips=呀！出错了");
			}
		}else {
			response.getWriter().print(request.getContextPath()+"/jsp/login.jsp");
		}
	}

}
