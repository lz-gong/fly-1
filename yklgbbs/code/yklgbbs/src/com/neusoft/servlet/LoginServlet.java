package com.neusoft.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.bean.Userinfo;
import com.neusoft.dao.UserDaoImp;
import com.neusoft.service.IUserService;
import com.neusoft.service.UserServiceImp;
import com.neusoft.utils.EmailUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
		
		String email=request.getParameter("email");
		String password=request.getParameter("pass");
		String checkCode=request.getParameter("checkCode");
//		System.out.println(checkCode);
		
		if(email!=null && password!=null && checkCode!=null) {  //这里为了防止不经过表单直接提交
			String checkCodeFromSession=(String)request.getSession().getAttribute("checkCode");
			email=email.trim();
			Cookie cookie1=new Cookie("username", email);
			cookie1.setMaxAge(604800);
			response.addCookie(cookie1);
			if(checkCodeFromSession!=null && checkCode.equalsIgnoreCase(checkCodeFromSession)) {
				//用完验证码后立即删除，防止表单重复提交
				request.getSession().removeAttribute("checkCode");
				
				
				password=password.trim();
				if(EmailUtil.isEmail(email) && password.length()>=6) {
					IUserService ius=new UserServiceImp();
					Userinfo user=ius.findUser(email, password);
					if(user!=null) {
						request.getSession().setAttribute("userInfo", user);  //这里存储用户的登陆信息，很重要
						response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
						return;
					}else {
//						request.getSession().setAttribute("error_login", "用户不存在或密码错误！");
						Cookie cookie=new Cookie("error_login","any");
						cookie.setMaxAge(1);
						response.addCookie(cookie);

						response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
					}
				}else {
					if(EmailUtil.isEmail(email)==false) {
//						request.getSession().setAttribute("error_email", "邮箱格式不正确！");
						Cookie cookie=new Cookie("error_email","any");
						cookie.setMaxAge(1);
						response.addCookie(cookie);
						response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
					}
					if(password.length()<6) {
//						request.getSession().setAttribute("error_password", "密码长度小于6位！"); 
						Cookie cookie=new Cookie("error_password","any");
						cookie.setMaxAge(1);
						response.addCookie(cookie);
						response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
					}
				}
			}else {
				Cookie cookie=new Cookie("error_checkCode","any");
				cookie.setMaxAge(1);
				response.addCookie(cookie);
				response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
			}
		}else {
			response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
		}
	}
}
