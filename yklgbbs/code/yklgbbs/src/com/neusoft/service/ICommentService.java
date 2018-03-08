package com.neusoft.service;

import java.util.List;

import com.neusoft.bean.Comment;
import com.neusoft.bean.CommentEx;
import com.neusoft.bean.MessageEx;
import com.neusoft.bean.TopicInfoEx;

public interface ICommentService {
	//�������
	int[] addComment(Comment comment);
	
	//��ȡĳ�����ӵ�ȫ������
	List<CommentEx> getAllComment(int topicId);
	
	//��ȡĳ�����ӵ����۵�����(��ȡ�������ӵ����۵��������������۵����۵�����)
	int getCommentNum(int topicId);
	
	//����ĳ�����۵�����
	boolean increaseSupportNum(int commentid);
	
	//��ѯ�����Ƿ����ڱ�����
	boolean checkCommentBelongAuthor(int commentid,int userid);
	
	//��������
	boolean acceptComment(int commentid);
	
	//��ȡ�����ߵ�id
	int getCommentUserid(int commentid);
	
	//ɾ��ĳ������
	boolean deleteComment(int commentid);
	
	//��ȡ��������
	String getCommentContent(int commentid);
	
	//�޸����۵�����
	boolean editComment(int commentid,String newContent);
	
	//���һ��message
	boolean addMessage(int commentid,int topicUserid);
	
	//��ȡ�ҵ�ȫ����Ϣ������ȡ��ǰ�û���ȫ����Ϣ��
	List<MessageEx> getMyMessageList(int userid , int pageIndex , int pageSize);
	
	//��ȡ��ǰ�û�����Ϣ����
	int getMyMessageNum(int userid);
	
	//��ѯ����Ϣ�Ƿ����ڵ�ǰ�û�
	boolean checkMessageIsBelongNowUser(int messageid,int userid);
	
	//ɾ��һ��message
	boolean deleteOneMessage(int messageid);
	
	//ɾ��ȫ����Ϣ
	boolean deleteAllMessage(int userid);
}
