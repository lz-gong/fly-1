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
 * Servlet implementation class CutPageServlet
 */
@WebServlet("/fenyeServlet.do")
public class FenyeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String target=request.getParameter("target");
		if(target!=null && target.equals("")==false) {
			if(target.equals("all")) {
				String strPageIndex=request.getParameter("pageIndex");
				String strPageSize=request.getParameter("pageSize");
				if(strPageIndex!=null && strPageSize!=null) {
					int pageIndex=Integer.parseInt(strPageIndex);
					int pageSize=Integer.parseInt(strPageSize);
					ITopicService its=new TopicServiceImp();
					int topicNum=its.getTopicNum();
					List<TopicInfoEx> list=its.getTopicInfoExList(pageIndex, pageSize);
					PageDataModel<TopicInfoEx> pdm=new PageDataModel<TopicInfoEx>();
					pdm.setTotal(topicNum);
					pdm.setList(list);
					response.getWriter().print(JSON.toJSONString(pdm));
				}else {
					response.sendRedirect(request.getContextPath()+"/jsp/index_fenye.jsp");
				}
			}else if(target.equals("naccept")) {
				String strPageIndex=request.getParameter("pageIndex");
				String strPageSize=request.getParameter("pageSize");
				int pageIndex=Integer.parseInt(strPageIndex);
				int pageSize=Integer.parseInt(strPageSize);
				ITopicService its=new TopicServiceImp();
				int topicNum=its.getNotAcceptTopicNum();
				List<TopicInfoEx> list=its.getNotAcceptTopicInfoExList(pageIndex, pageSize);
				PageDataModel<TopicInfoEx> pdm=new PageDataModel<TopicInfoEx>();
				pdm.setTotal(topicNum);
				pdm.setList(list);
				response.getWriter().print(JSON.toJSONString(pdm));
			}else if(target.equals("accept")) {
				String strPageIndex=request.getParameter("pageIndex");
				String strPageSize=request.getParameter("pageSize");
				int pageIndex=Integer.parseInt(strPageIndex);
				int pageSize=Integer.parseInt(strPageSize);
				ITopicService its=new TopicServiceImp();
				int topicNum=its.getAcceptTopicNum();
				List<TopicInfoEx> list=its.getAcceptTopicInfoExList(pageIndex, pageSize);
				PageDataModel<TopicInfoEx> pdm=new PageDataModel<TopicInfoEx>();
				pdm.setTotal(topicNum);
				pdm.setList(list);
				response.getWriter().print(JSON.toJSONString(pdm));
			}else if(target.equals("nice")) {
				String strPageIndex=request.getParameter("pageIndex");
				String strPageSize=request.getParameter("pageSize");
				int pageIndex=Integer.parseInt(strPageIndex);
				int pageSize=Integer.parseInt(strPageSize);
				ITopicService its=new TopicServiceImp();
				int topicNum=its.getNiceTopicNum();
				List<TopicInfoEx> list=its.getNiceTopicInfoExList(pageIndex, pageSize);
				PageDataModel<TopicInfoEx> pdm=new PageDataModel<TopicInfoEx>();
				pdm.setTotal(topicNum);
				pdm.setList(list);
				response.getWriter().print(JSON.toJSONString(pdm));
			}else if(target.equals("mytopic")) {
				Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
				if(user!=null) {
					String strPageIndex=request.getParameter("pageIndex");
					String strPageSize=request.getParameter("pageSize");
					int pageIndex=Integer.parseInt(strPageIndex);
					int pageSize=Integer.parseInt(strPageSize);
					ITopicService its=new TopicServiceImp();
					int topicNum=its.getMyTopicTopicNum(user.getId());
					List<TopicInfoEx> list=its.getMyTopicTopicInfoExList(pageIndex, pageSize, user.getId());
					PageDataModel<TopicInfoEx> pdm=new PageDataModel<TopicInfoEx>();
					pdm.setTotal(topicNum);
					pdm.setList(list);
					response.getWriter().print(JSON.toJSONString(pdm));
				}else {
					PageDataModel<TopicInfoEx> pdm=new PageDataModel<TopicInfoEx>();
					pdm.setCheck("notLogin");
					response.getWriter().print(JSON.toJSONString(pdm));
				}
			}else if(target.equals("search")){
				String searchStr=request.getParameter("searchStr");
				if(searchStr.equals("")==false) {
					String strPageIndex=request.getParameter("pageIndex");
					String strPageSize=request.getParameter("pageSize");
					int pageIndex=Integer.parseInt(strPageIndex);
					int pageSize=Integer.parseInt(strPageSize);
					ITopicService its=new TopicServiceImp();
					int topicNum=its.getSearchTopicNum(searchStr);
					List<TopicInfoEx> list=its.getSearchTopicInfoExList(pageIndex, pageSize, searchStr);
					PageDataModel<TopicInfoEx> pdm=new PageDataModel<TopicInfoEx>();
					pdm.setTotal(topicNum);
					pdm.setList(list);
					response.getWriter().print(JSON.toJSONString(pdm));
				}else {
					PageDataModel<TopicInfoEx> pdm=new PageDataModel<TopicInfoEx>();
					pdm.setCheck("noSearchStr");
					response.getWriter().print(JSON.toJSONString(pdm));
				}
			}else {
				PageDataModel<TopicInfoEx> pdm=new PageDataModel<TopicInfoEx>();
				pdm.setCheck("404");
				response.getWriter().print(JSON.toJSONString(pdm));
			}
		}else {//当直接访问index_fenye.jsp，直接跳转到index_fenye.jsp?target=all
			PageDataModel<TopicInfoEx> pdm=new PageDataModel<TopicInfoEx>();
			pdm.setCheck("jumpAll");
			response.getWriter().print(JSON.toJSONString(pdm));
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
