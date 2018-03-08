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
		//���ܣ����ע�������Ƿ����
		String email=request.getParameter("email");
		UserDaoImp udi=new UserDaoImp();
		int temp=udi.checkEmailRepeat(email);  //Ϊ0�����ظ�
		PrintWriter out=response.getWriter();
		out.print(temp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
