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
@WebServlet("/reg.do")  //�����java������Ա��ϰ��
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
			//������֤�������ɾ������ֹ�����ظ��ύ
			request.getSession().removeAttribute("checkCode");
			
			//1.��ȡǰ̨����
			String email=request.getParameter("email");
			String nickName = request.getParameter("nickName");
			String password =request.getParameter("pass");
			String repassword=request.getParameter("repass");
			
			
			if(email!=null && nickName!=null && password!=null && repassword!=null) {  //����Ϊ�˷�ֹ����������ֱ���ύ
				email=email.trim();
				nickName = nickName.trim();
				password =password.trim();
				repassword=repassword.trim();
				
				if(nickName!="" && password.length()>=6 && repassword.length()>=6 && password.equals(repassword) && EmailUtil.isEmail(email)) {
					Userinfo ui=new Userinfo();
					ui.setEmail(email);
					ui.setNickname(nickName);
					ui.setPassword(password);
					//���￪ʼ���û�����Ĭ��ͷ��
					String headImgs=request.getServletContext().getInitParameter("defaultHeadImg");
					System.out.println(headImgs);
					String arr[]=headImgs.split(",");
					String temp=request.getContextPath()+"/res/images/avatar/"+arr[(int) Math.round(Math.random()*arr.length)];
					ui.setHeadUrl(temp);
					IUserService ius=new UserServiceImp();
					int rel = ius.addUser(ui);  //���temp��ʱû����
					if(rel==1) { //����1˵��ע��ɹ�
//						System.out.println("����1��ע��ɹ���");
						response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
					}
					if(rel==0){
//						request.getSession().setAttribute("error_emailRepeat", "�����ѱ�ע�ᣡ");
//						System.out.println("�����ѱ�ע�ᣡ");
						response.sendRedirect(request.getContextPath()+"/jsp/reg.jsp");
					}
				}else { //���else���г����еĴ���
					if(nickName.equals("")) {
//						request.getSession().setAttribute("error_nickName", "�ǳƲ���Ϊ�գ�");
						response.sendRedirect(request.getContextPath()+"/jsp/reg.jsp");
					}
					if(password.equals(repassword)==false) {
//						request.getSession().setAttribute("error_password", "�������벻ͬ��");
						Cookie cookie=new Cookie("diff_pass_error","any");
						cookie.setMaxAge(1);
						response.addCookie(cookie);
						response.sendRedirect(request.getContextPath()+"/jsp/reg.jsp");
					}
					if(password.length()<6 || repassword.length()<6) {
//						request.getSession().setAttribute("error_password", "���볤��Ӧ����6λ��");  //������벻��ͬ��˵�������Ҫ��
						Cookie cookie=new Cookie("length_pass_error","any");
						cookie.setMaxAge(1);
						response.addCookie(cookie);
						response.sendRedirect(request.getContextPath()+"/jsp/reg.jsp");
					}
					if(EmailUtil.isEmail(email)==false) {
//						request.getSession().setAttribute("error_email", "�����ʽ����ȷ��");
						response.sendRedirect(request.getContextPath()+"/jsp/reg.jsp");
					}
				}
				
			}else {
				//����Ӧ����ʾһ���Ƿ�����ҳ�棬��Ϊ����������ֱ���ύ������ֱ����ת��ע��Servlet
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