package com.neusoft.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.neusoft.bean.CommentEx;
import com.neusoft.bean.PageDataModel;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.CommentServiceImp;
import com.neusoft.service.ICommentService;
import com.neusoft.service.ITopicService;
import com.neusoft.service.TopicServiceImp;

/**
 * Servlet implementation class GetCommentListServlet
 */
@WebServlet("/getCommentListServlet.do")
public class GetCommentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int topicid=Integer.parseInt(request.getParameter("topicId"));
		
		//获取帖子评论list
		ICommentService ics=new CommentServiceImp();
		List<CommentEx> list=ics.getAllComment(topicid);
		
		ITopicService its=new TopicServiceImp();
		if(list!=null && list.size()>0) { //如果没有评论下面的判断就没有必要了
			//检查当前帖子是否已经结贴
			if(its.checkTopicIsend(topicid)==false) {
				//查看当前帖子是否已经采纳最佳评论 和 查看帖子是否属于当前用户
				if(its.checkTopicIsaccepted(topicid)==false) { //如果当前帖子没有采纳最佳评论就进行如下操作
					Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
					if(user!=null) {
						int userid=user.getId();
						if(its.checkTopicBelongAuthor(topicid, userid)) {
							for(CommentEx c:list) {
								if(c.getUserId()!=userid) {
									c.setShowAccept(true);  //走到这里说明这条评论需要显示 采纳 按钮
								}else {
									c.setShowDelete(true);
									c.setShowEdit(true);
								}
							}
						}else{  //如果帖子不属于作者，也要给作者的评论显示编辑按钮
							for(CommentEx c:list) {
								if(c.getUserId()==userid) {
									c.setShowDelete(true);
									c.setShowEdit(true);
								}
							}
						}
					}
				}else{ //如果已经采纳就进行如下操作
					Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
					if(user!=null) {
						int userid=user.getId();
						for(CommentEx c:list) {
							if(c.getUserId()==userid && c.getIsAccepted()==0) {
								c.setShowDelete(true);
								c.setShowEdit(true);
							}
						}
					}
				}
			}else {
				System.out.println("本帖子已经结贴，这里是getCommentListServlet");
			}
		}
		
		//这里给list排序，将最佳评论顶置
		if(list!=null && list.size()>0) {
			for(CommentEx c:list) {
				if(c.getIsAccepted()==1) {
					list.remove(c);
					list.add(0, c);
					break;
				}
			}
		}
		//如果帖子已经结贴只显示被采纳即可，即使没有被采纳也是可以结贴的
		PageDataModel<CommentEx> pdm=new PageDataModel<CommentEx>();
		pdm.setList(list);
		response.getWriter().print(JSON.toJSONString(pdm));
	}

}
