package com.neusoft.service;

import java.util.List;

import com.neusoft.bean.Category;
import com.neusoft.bean.CommentEx;
import com.neusoft.bean.TopicInfoEx;
import com.neusoft.bean.Topicinfo;

public interface ITopicService {
	List<Category> getCategoryInfo();
	
	int addTopic(Topicinfo topicinfo);
	
	//��ȡȫ�������ӣ����˷�ҳ�����Ӧ�þͲ������ˣ�����ɾ��
	List<TopicInfoEx> getAllTopic();
	
	//��ȡ���ӵ���ϸ��Ϣ
	TopicInfoEx topicShowView(int id);
	
	//��ȡ���ӵ�������
	int getTopicNum();
	
	//�õ�List(ÿһҳ��list)
	List<TopicInfoEx> getTopicInfoExList(int pageIndex,int pageSize);
	
	//�õ����õ�TopicInfoEx
	List<TopicInfoEx> getTopTopicInfoExList();
	
	//ɾ������
	boolean deleteTopic(int topicid);
	
	//���Ӷ�����Ӿ�
	boolean setTopicTopGood(int topicid,int isTop,int isGood);
	
	//��������Ƿ����������
	boolean checkTopicBelongAuthor(int topicid,int userid);
	
	//�༭��������
	boolean editTopic(int topicid,String title,String content);
	
	//��ȡ�༭����
	TopicInfoEx getEditTopic(int topicid);
	
	//���������Ƿ����
	boolean findTopic(int topicid);
	
	//�ղ�����
	boolean collectTopic(int topicid,int userid);
	
	//ȡ���ղ�����
	boolean cancleCollectTopic(int topicid,int userid);
	
	//����û��Ƿ��ղع�������
	boolean checkRepeatCollect(int topicid,int userid);
	
	//�鿴�����Ƿ��ѽ���
	boolean checkTopicIsend(int topicid);
	
	//��ѯ�����Ƿ��Ѳ���ĳ������
	boolean checkTopicIsaccepted(int topicid);
	
	//��ȡ�������͵ķ�����
	int geTopictRewardKiss(int topicid);
	
	//�õ�δ��������List(ÿһҳ��list)
	List<TopicInfoEx> getNotAcceptTopicInfoExList(int pageIndex,int pageSize);
	
	//�õ��Ѳ�������List(ÿһҳ��list)
	List<TopicInfoEx> getAcceptTopicInfoExList(int pageIndex,int pageSize);
	
	//�õ�����List(ÿһҳ��list)
	List<TopicInfoEx> getNiceTopicInfoExList(int pageIndex,int pageSize);
	
	//�õ��ҵ�����List(ÿһҳ��list)
	List<TopicInfoEx> getMyTopicTopicInfoExList(int pageIndex,int pageSize,int userid);
	
	//����������ӣ�ֻ���ݱ���List(ÿһҳ��list)
	List<TopicInfoEx> getSearchTopicInfoExList(int pageIndex,int pageSize, String SearchStr);

	
	//�õ�δ�������ӵ�����
	int getNotAcceptTopicNum();
	
	//�õ��Ѳ������ӵ�����
	int getAcceptTopicNum();
	
	//�õ�����������
	int getNiceTopicNum();
	
	//�õ��ҵ����ӵ�����
	int getMyTopicTopicNum(int userid);
	
	//����������ӵ�����
	int getSearchTopicNum(String SearchStr);
	
	//��������Ϊ����
	boolean acceptTopic(int topicid);
	
	//��ȡһ����top�ش�񣬲������ظ��Ļظ�
	List<TopicInfoEx> getWeekAnswerList();
	
	//�������ӵ���������
	boolean addTopicAnswerNum(int topicid);
	
	//����������������
	boolean decTopicAnswerNum(int topicid);
	
	//��������id��ȡ�û�id
	int getUseridByTopicid(int topicid);
	
	//��ȡ10�������Ӱ�ǰ10��
	List<TopicInfoEx> getHotViewList();
	
	//��ȡ10����������ǰ10��
	List<TopicInfoEx> getHotReplyList();
	
	//��ȡ����ǰ8��
	List<TopicInfoEx> getKissNumList();
	
	//�������(������ҳ��ʾ)
	List<TopicInfoEx> getUserNearQuestionList(int userid);	
	
	//����ش�(������ҳ��ʾ�����������۵�����)
	List<CommentEx> getUserNearAnswerList(int userid);	
	
	//�ҵķ���(����ǰ�û���ȫ�����ӣ�����ҳ����)
	List<TopicInfoEx> getMyTopicList(int userid , int pageIndex , int pageSize);
	
	//�ҵ��ղ�(����ǰ�û��ղص�ȫ�����ӣ�����ҳ����)
	List<TopicInfoEx> getMyCollectTopicList(int userid , int pageIndex , int pageSize);
	
	//��ȡ���ղص����ӵ�����
	int getMyCollectNum(int userid);
}
