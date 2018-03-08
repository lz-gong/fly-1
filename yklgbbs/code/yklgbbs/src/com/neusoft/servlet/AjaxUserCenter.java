package com.neusoft.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.neusoft.bean.PageDataModel;
import com.neusoft.bean.TopicInfoEx;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.ITopicService;
import com.neusoft.service.TopicServiceImp;

/**
 * Servlet implementation class AjaxUserCenter
 */
@WebServlet("/ajaxUserCenter.do")
public class AjaxUserCenter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		if(user!=null) {
			int userid=user.getId();
			String req=request.getParameter("req");
			String strPageIndex=request.getParameter("pageIndex");
			String strPageSize=request.getParameter("pageSize");
			int pageIndex=Integer.parseInt(strPageIndex);
			int pageSize=Integer.parseInt(strPageSize);
			ITopicService its=new TopicServiceImp();
			if(req.equals("myTopic")) {
				List<TopicInfoEx> list=its.getMyTopicList(userid, pageIndex, pageSize);
				PageDataModel<TopicInfoEx> pdm=new PageDataModel<TopicInfoEx>();
				pdm.setTotal(its.getMyTopicTopicNum(userid));
				pdm.setList(list);
				response.getWriter().print(JSON.toJSONString(pdm));
			}else if(req.equals("myCollect")) {
				List<TopicInfoEx> list=its.getMyCollectTopicList(userid, pageIndex, pageSize);
				PageDataModel<TopicInfoEx> pdm=new PageDataModel<TopicInfoEx>();
				pdm.setTotal(its.getMyCollectNum(userid));
				System.out.println(its.getMyCollectNum(userid));
				pdm.setList(list);
				response.getWriter().print(JSON.toJSONString(pdm));
			}else {
				//这种情况不处理了，太麻烦了
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
