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
			String message = "帖子已经发表了！请耐心等待";
			message = URLEncoder.encode(message,"utf-8");
			response.sendRedirect(request.getContextPath()+"/jsp/tips.jsp?tips="+message);
		}else {
			request.getSession().removeAttribute("checkCode");
			Userinfo user=(Userinfo) request.getSession().getAttribute("userInfo");
			if(user!=null) {
				String title=request.getParameter("title");
				String content=request.getParameter("content");
				String category_id=request.getParameter("class");  //如果没选的换这里获取的是空值
				String rewardKiss=request.getParameter("kiss");  //表中没有这个 正常来说应该是有的
				Date creatTime=new Date();  //这个时间导数据库要转换为sql Date类型的
				int userid=user.getId();
				
				if(title!=null && content!=null && category_id!=null && rewardKiss!=null && category_id!=null) { //这里为了防止登陆后不经过表单直接访问这个页面
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
									//跳到主页
									//这个是get请求还是post请求？要看访问addTopic.do的提交方式时post还是get
									//减少贴主的飞吻数
									IUserService ius=new UserServiceImp();
									ius.decreaseKissNum(userid, Integer.parseInt(rewardKiss));
									//增加贴主的经验
									String incExperience=request.getServletContext().getInitParameter("incExperience");
									String experience[]=incExperience.split(",");
									ius.increaseExperience(userid, Integer.parseInt(experience[1])); 
									
									response.sendRedirect(request.getContextPath()+"/goIndex/all");
								}
								if(num==0) {
									//提示数据库出错了，重新发布一条吧
								}
							}else {
								System.out.println("请输入标题和内容后再发布吧！或者没有选择”类别“");
							}
						}else {
							request.getRequestDispatcher("/jsp/tips.jsp?tips=呀！飞吻数不够了，签到或被采纳都可以获得飞吻哦").forward(request, response);
						}
					}else {
						request.getRequestDispatcher("/jsp/tips.jsp?tips=呀！悬赏的飞吻数有误").forward(request, response);
					}
					
				}else {
					response.sendRedirect(request.getContextPath()+"/loadOption.do");
				}
				// is_goog is_end  在发帖时默认为0  即不是精品，没有结帖
			}else {
				response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
			}
		}
	}

}
