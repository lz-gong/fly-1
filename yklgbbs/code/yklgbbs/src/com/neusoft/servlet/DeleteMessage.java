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

/**
 * Servlet implementation class DeleteMessage
 */
@WebServlet("/deleteMessage.do")
public class DeleteMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		if(user!=null) {
			String temp=request.getParameter("req");
			if(temp!=null) {
				int userid=user.getId();
				ICommentService ics=new CommentServiceImp();
				if(temp.equals("all")) {
					response.getWriter().print(ics.deleteAllMessage(userid));
				}else {
					try {
						int messageid=Integer.parseInt(temp);
						if(ics.checkMessageIsBelongNowUser(messageid, userid)) {
							response.getWriter().print(ics.deleteOneMessage(messageid));
						}else {
							response.getWriter().print(false);
							System.out.println("将要删除的消息不属于当前用户，无法删除！");
						}
					} catch (NumberFormatException e) {
						response.getWriter().print(false);
						e.printStackTrace();
					}
				}
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
