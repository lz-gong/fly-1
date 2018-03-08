package com.neusoft.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.bean.Userinfo;
import com.neusoft.dao.IUserDao;
import com.neusoft.service.IUserService;
import com.neusoft.service.UserServiceImp;

/**
 * Servlet implementation class ChangePassServlet
 */
@WebServlet("/changePass.do")
public class ChangePassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		if(user!=null) {
			String nowUserpssword=user.getPassword();  //当前用户密码
			String nowpass=request.getParameter("nowpass");
			String newpass=request.getParameter("pass");
			String repeatNewpass=request.getParameter("repass");
			if(nowpass!=null && newpass!=null && repeatNewpass!=null) {
				nowpass=nowpass.trim();
				newpass=newpass.trim();
				repeatNewpass=repeatNewpass.trim();
				if(nowpass.equals(nowUserpssword)) {
					if(newpass.equals(repeatNewpass) && newpass.length()>=6) {
						//开始进行修改！
						IUserService ius=new UserServiceImp();
						if(ius.changePass(user.getId(), newpass)) {
							request.getSession().removeAttribute("userInfo");
							response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
						}else { //如果修改失败
							System.out.println("这里在修改密码界面提示！修改密码出错了！");
						}
					}
				}else {
					//返回密码错误
					System.out.println("您输入的原密码不正确！");
					response.sendRedirect(request.getContextPath()+"/jsp/user/set.jsp#pass");
				}
			}
		}else {
			response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
		}
	}

}
