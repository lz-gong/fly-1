package com.neusoft.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.neusoft.bean.MessageEx;
import com.neusoft.bean.PageDataModel;
import com.neusoft.bean.TopicInfoEx;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.CommentServiceImp;
import com.neusoft.service.ICommentService;

/**
 * Servlet implementation class AjaxMessage
 */
@WebServlet("/ajaxMessage.do")
public class AjaxMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		if(user!=null) {
			int userid=user.getId();
			String strPageIndex=request.getParameter("pageIndex");
			String strPageSize=request.getParameter("pageSize");
			int pageIndex=Integer.parseInt(strPageIndex);
			int pageSize=Integer.parseInt(strPageSize);
			ICommentService ics=new CommentServiceImp();
			List<MessageEx> list=ics.getMyMessageList(userid, pageIndex, pageSize);
			PageDataModel<MessageEx> pdm=new PageDataModel<MessageEx>();
			pdm.setTotal(ics.getMyMessageNum(userid));
			pdm.setList(list);
			response.getWriter().print(JSON.toJSONString(pdm));
		}else {
			//这种情况不处理了，太麻烦了，意义还不大
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
