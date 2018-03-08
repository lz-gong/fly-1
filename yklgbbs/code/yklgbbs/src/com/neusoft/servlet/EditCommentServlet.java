package com.neusoft.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.service.CommentServiceImp;
import com.neusoft.service.ICommentService;

/**
 * Servlet implementation class EditCommentServlet
 */
@WebServlet("/editCommentServlet.do")
public class EditCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String temp=request.getParameter("commentid");
		try {
			int commentid=Integer.parseInt(temp);
			ICommentService ics=new CommentServiceImp();
			String content=ics.getCommentContent(commentid);
			response.getWriter().print(content);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.getWriter().print("id不可转换！获取评论内容失败。");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
