package com.neusoft.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.CommentServiceImp;
import com.neusoft.service.ICommentService;

/**
 * Servlet implementation class IncreaseSupportServlet
 */
@WebServlet("/increaseSupportServlet.do")
public class IncreaseSupportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo) request.getSession().getAttribute("userInfo");
		if(user!=null) {
			String temp=request.getParameter("commentid");
			ICommentService ics=new CommentServiceImp();
			if(ics.increaseSupportNum(Integer.parseInt(temp))) {
				response.getWriter().print(true);
			}else{
				response.getWriter().print(false);
			}
		}else {
			response.getWriter().print(request.getContextPath()+"/jsp/login.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
