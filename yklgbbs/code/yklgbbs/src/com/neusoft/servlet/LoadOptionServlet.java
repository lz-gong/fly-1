package com.neusoft.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.bean.Category;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.ITopicService;
import com.neusoft.service.TopicServiceImp;

/**
 * Servlet implementation class LoadOptionServlet
 */
@WebServlet("/loadOption.do")
public class LoadOptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo) request.getSession().getAttribute("userInfo");
		if(user!=null) {
			if(user.getKissNum()>=5) {
				//��Ҫ�жϵ�½����������������������½����
				ITopicService its=new TopicServiceImp();
				List<Category> list=its.getCategoryInfo();
				if(list!=null && list.size()>0) {
					request.setAttribute("option", list);
					request.setAttribute("kissNum", user.getKissNum());
					request.getRequestDispatcher("/jsp/user/add_topic.jsp").forward(request, response);
				}else {
					//�ߵ�����˵�����ݿ���û�����ݣ������ݿ������Ҫ��������ҳ�棨ѽ�����ݿ�����ˣ����Ժ����ԣ���
				}
			}else {
				request.getRequestDispatcher("/jsp/tips.jsp?tips=ѽ�������������ˣ�ǩ���򱻲��ɶ����Ի�÷���Ŷ").forward(request, response);
			}	
		}else {
			response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
		}
	}

}
