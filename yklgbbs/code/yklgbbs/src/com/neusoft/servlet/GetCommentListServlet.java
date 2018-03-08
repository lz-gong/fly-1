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
		
		//��ȡ��������list
		ICommentService ics=new CommentServiceImp();
		List<CommentEx> list=ics.getAllComment(topicid);
		
		ITopicService its=new TopicServiceImp();
		if(list!=null && list.size()>0) { //���û������������жϾ�û�б�Ҫ��
			//��鵱ǰ�����Ƿ��Ѿ�����
			if(its.checkTopicIsend(topicid)==false) {
				//�鿴��ǰ�����Ƿ��Ѿ������������ �� �鿴�����Ƿ����ڵ�ǰ�û�
				if(its.checkTopicIsaccepted(topicid)==false) { //�����ǰ����û�в���������۾ͽ������²���
					Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
					if(user!=null) {
						int userid=user.getId();
						if(its.checkTopicBelongAuthor(topicid, userid)) {
							for(CommentEx c:list) {
								if(c.getUserId()!=userid) {
									c.setShowAccept(true);  //�ߵ�����˵������������Ҫ��ʾ ���� ��ť
								}else {
									c.setShowDelete(true);
									c.setShowEdit(true);
								}
							}
						}else{  //������Ӳ��������ߣ�ҲҪ�����ߵ�������ʾ�༭��ť
							for(CommentEx c:list) {
								if(c.getUserId()==userid) {
									c.setShowDelete(true);
									c.setShowEdit(true);
								}
							}
						}
					}
				}else{ //����Ѿ����ɾͽ������²���
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
				System.out.println("�������Ѿ�������������getCommentListServlet");
			}
		}
		
		//�����list���򣬽�������۶���
		if(list!=null && list.size()>0) {
			for(CommentEx c:list) {
				if(c.getIsAccepted()==1) {
					list.remove(c);
					list.add(0, c);
					break;
				}
			}
		}
		//��������Ѿ�����ֻ��ʾ�����ɼ��ɣ���ʹû�б�����Ҳ�ǿ��Խ�����
		PageDataModel<CommentEx> pdm=new PageDataModel<CommentEx>();
		pdm.setList(list);
		response.getWriter().print(JSON.toJSONString(pdm));
	}

}
