package com.neusoft.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import com.neusoft.bean.Userinfo;
import com.neusoft.service.IUserService;
import com.neusoft.service.UserServiceImp;
import com.neusoft.utils.EmailUtil;

/**
 * Servlet implementation class RegServlet
 */
@WebServlet("/reg.do")  //这个是java工作人员的习惯
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
		String checkCode=request.getParameter("checkCode");
		String checkCodeFromSession=(String)request.getSession().getAttribute("checkCode");
		if(checkCodeFromSession!=null && checkCode.equalsIgnoreCase(checkCodeFromSession)) {
			//用完验证码后立即删除，防止表单重复提交
			request.getSession().removeAttribute("checkCode");
			
			//1.获取前台参数
			String email=request.getParameter("email");
			String nickName = request.getParameter("nickName");
			String password =request.getParameter("pass");
			String repassword=request.getParameter("repass");
			
			
			if(email!=null && nickName!=null && password!=null && repassword!=null) {  //这里为了防止不经过表单直接提交
				email=email.trim();
				nickName = nickName.trim();
				password =password.trim();
				repassword=repassword.trim();
				
				if(nickName!="" && password.length()>=6 && repassword.length()>=6 && password.equals(repassword) && EmailUtil.isEmail(email)) {
					Userinfo ui=new Userinfo();
					ui.setEmail(email);
					ui.setNickname(nickName);
					ui.setPassword(password);
					//这里开始给用户添加默认头像
					String headImgs=request.getServletContext().getInitParameter("defaultHeadImg");
					System.out.println(headImgs);
					String arr[]=headImgs.split(",");
					String temp=request.getContextPath()+"/res/images/avatar/"+arr[(int) Math.round(Math.random()*arr.length)];
					ui.setHeadUrl(temp);
					IUserService ius=new UserServiceImp();
					int rel = ius.addUser(ui);  //这个temp暂时没用上
					if(rel==1) { //返回1说明注册成功
//						System.out.println("返回1，注册成功！");
						response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
					}
					if(rel==0){
//						request.getSession().setAttribute("error_emailRepeat", "邮箱已被注册！");
//						System.out.println("邮箱已被注册！");
						response.sendRedirect(request.getContextPath()+"/jsp/reg.jsp");
					}
				}else { //如果else就列出所有的错误
					if(nickName.equals("")) {
//						request.getSession().setAttribute("error_nickName", "昵称不能为空！");
						response.sendRedirect(request.getContextPath()+"/jsp/reg.jsp");
					}
					if(password.equals(repassword)==false) {
//						request.getSession().setAttribute("error_password", "两次密码不同！");
						Cookie cookie=new Cookie("diff_pass_error","any");
						cookie.setMaxAge(1);
						response.addCookie(cookie);
						response.sendRedirect(request.getContextPath()+"/jsp/reg.jsp");
					}
					if(password.length()<6 || repassword.length()<6) {
//						request.getSession().setAttribute("error_password", "密码长度应大于6位！");  //相对密码不相同来说这个是重要的
						Cookie cookie=new Cookie("length_pass_error","any");
						cookie.setMaxAge(1);
						response.addCookie(cookie);
						response.sendRedirect(request.getContextPath()+"/jsp/reg.jsp");
					}
					if(EmailUtil.isEmail(email)==false) {
//						request.getSession().setAttribute("error_email", "邮箱格式不正确！");
						response.sendRedirect(request.getContextPath()+"/jsp/reg.jsp");
					}
				}
				
			}else {
				//这里应该提示一个非法操作页面，因为不经过表单直接提交，或者直接跳转到注册Servlet
				response.sendRedirect(request.getContextPath()+"/jsp/reg.jsp");
			}
		}else {
			Cookie cookie=new Cookie("error_checkCode","any");
			cookie.setMaxAge(1);
			response.addCookie(cookie);
			response.sendRedirect(request.getContextPath()+"/jsp/reg.jsp");
		}
	}
}
