package com.neusoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.dao.IUserDao;
import com.neusoft.dao.UserDaoImp;

/**
 * Servlet implementation class AjaxRegServlet
 */
@WebServlet("/ajaxReg.do")
public class AjaxRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//功能：检查注册邮箱是否存在
		String email=request.getParameter("email");
		UserDaoImp udi=new UserDaoImp();
		int temp=udi.checkEmailRepeat(email);  //为0存在重复
		PrintWriter out=response.getWriter();
		out.print(temp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
