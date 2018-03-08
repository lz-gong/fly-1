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
			String nowUserpssword=user.getPassword();  //��ǰ�û�����
			String nowpass=request.getParameter("nowpass");
			String newpass=request.getParameter("pass");
			String repeatNewpass=request.getParameter("repass");
			if(nowpass!=null && newpass!=null && repeatNewpass!=null) {
				nowpass=nowpass.trim();
				newpass=newpass.trim();
				repeatNewpass=repeatNewpass.trim();
				if(nowpass.equals(nowUserpssword)) {
					if(newpass.equals(repeatNewpass) && newpass.length()>=6) {
						//��ʼ�����޸ģ�
						IUserService ius=new UserServiceImp();
						if(ius.changePass(user.getId(), newpass)) {
							request.getSession().removeAttribute("userInfo");
							response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
						}else { //����޸�ʧ��
							System.out.println("�������޸����������ʾ���޸���������ˣ�");
						}
					}
				}else {
					//�����������
					System.out.println("�������ԭ���벻��ȷ��");
					response.sendRedirect(request.getContextPath()+"/jsp/user/set.jsp#pass");
				}
			}
		}else {
			response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
		}
	}

}
