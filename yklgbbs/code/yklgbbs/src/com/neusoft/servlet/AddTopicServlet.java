package com.neusoft.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.bean.Topicinfo;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.ITopicService;
import com.neusoft.service.IUserService;
import com.neusoft.service.TopicServiceImp;
import com.neusoft.service.UserServiceImp;

/**
 * Servlet implementation class AddTopicServlet
 */
@WebServlet("/addTopic.do")
public class AddTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String checkCode=(String)request.getSession().getAttribute("checkCode");
		if(checkCode==null) {
			String message = "�����Ѿ������ˣ������ĵȴ�";
			message = URLEncoder.encode(message,"utf-8");
			response.sendRedirect(request.getContextPath()+"/jsp/tips.jsp?tips="+message);
		}else {
			request.getSession().removeAttribute("checkCode");
			Userinfo user=(Userinfo) request.getSession().getAttribute("userInfo");
			if(user!=null) {
				String title=request.getParameter("title");
				String content=request.getParameter("content");
				String category_id=request.getParameter("class");  //���ûѡ�Ļ������ȡ���ǿ�ֵ
				String rewardKiss=request.getParameter("kiss");  //����û����� ������˵Ӧ�����е�
				Date creatTime=new Date();  //���ʱ�䵼���ݿ�Ҫת��Ϊsql Date���͵�
				int userid=user.getId();
				
				if(title!=null && content!=null && category_id!=null && rewardKiss!=null && category_id!=null) { //����Ϊ�˷�ֹ��½�󲻾�������ֱ�ӷ������ҳ��
					if(rewardKiss.equals("5") || rewardKiss.equals("10")  || rewardKiss.equals("20")  || rewardKiss.equals("50")  || rewardKiss.equals("100")) {
						if(user.getKissNum()>=Integer.parseInt(rewardKiss)) {
							title=title.trim();
							content=content.trim();
							if(title.equals("")==false && content.equals("")==false) {
								Topicinfo topicinfo=new Topicinfo();
								topicinfo.setTitle(title);
								topicinfo.setContent(content);
								topicinfo.setCreatetime(creatTime);
								topicinfo.setCategoryId(Integer.parseInt(category_id));
								topicinfo.setViewCount(0);
								topicinfo.setUserid(userid);
								topicinfo.setIsGood(0);
								topicinfo.setIsEnd(0);
								topicinfo.setRewardKiss(Integer.parseInt(rewardKiss));

								ITopicService its=new TopicServiceImp();
								int num=its.addTopic(topicinfo);

								if(num==1) {
									//������ҳ
									//�����get������post����Ҫ������addTopic.do���ύ��ʽʱpost����get
									//���������ķ�����
									IUserService ius=new UserServiceImp();
									ius.decreaseKissNum(userid, Integer.parseInt(rewardKiss));
									//���������ľ���
									String incExperience=request.getServletContext().getInitParameter("incExperience");
									String experience[]=incExperience.split(",");
									ius.increaseExperience(userid, Integer.parseInt(experience[1])); 
									
									response.sendRedirect(request.getContextPath()+"/goIndex/all");
								}
								if(num==0) {
									//��ʾ���ݿ�����ˣ����·���һ����
								}
							}else {
								System.out.println("�������������ݺ��ٷ����ɣ�����û��ѡ�����");
							}
						}else {
							request.getRequestDispatcher("/jsp/tips.jsp?tips=ѽ�������������ˣ�ǩ���򱻲��ɶ����Ի�÷���Ŷ").forward(request, response);
						}
					}else {
						request.getRequestDispatcher("/jsp/tips.jsp?tips=ѽ�����͵ķ���������").forward(request, response);
					}
					
				}else {
					response.sendRedirect(request.getContextPath()+"/loadOption.do");
				}
				// is_goog is_end  �ڷ���ʱĬ��Ϊ0  �����Ǿ�Ʒ��û�н���
			}else {
				response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
			}
		}
	}

}