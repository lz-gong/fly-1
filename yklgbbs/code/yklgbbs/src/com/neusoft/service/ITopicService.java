package com.neusoft.service;

import java.util.List;

import com.neusoft.bean.Category;
import com.neusoft.bean.CommentEx;
import com.neusoft.bean.TopicInfoEx;
import com.neusoft.bean.Topicinfo;

public interface ITopicService {
	List<Category> getCategoryInfo();
	
	int addTopic(Topicinfo topicinfo);
	
	//获取全部的帖子，有了分页后这个应该就不会用了，可以删掉
	List<TopicInfoEx> getAllTopic();
	
	//获取帖子的详细信息
	TopicInfoEx topicShowView(int id);
	
	//获取帖子的总数量
	int getTopicNum();
	
	//得到List(每一页的list)
	List<TopicInfoEx> getTopicInfoExList(int pageIndex,int pageSize);
	
	//得到顶置的TopicInfoEx
	List<TopicInfoEx> getTopTopicInfoExList();
	
	//删除帖子
	boolean deleteTopic(int topicid);
	
	//帖子顶置与加精
	boolean setTopicTopGood(int topicid,int isTop,int isGood);
	
	//检查帖子是否归属本作者
	boolean checkTopicBelongAuthor(int topicid,int userid);
	
	//编辑帖子内容
	boolean editTopic(int topicid,String title,String content);
	
	//获取编辑内容
	TopicInfoEx getEditTopic(int topicid);
	
	//查找帖子是否存在
	boolean findTopic(int topicid);
	
	//收藏帖子
	boolean collectTopic(int topicid,int userid);
	
	//取消收藏帖子
	boolean cancleCollectTopic(int topicid,int userid);
	
	//检查用户是否收藏过本帖子
	boolean checkRepeatCollect(int topicid,int userid);
	
	//查看帖子是否已结贴
	boolean checkTopicIsend(int topicid);
	
	//查询帖子是否已采纳某个评论
	boolean checkTopicIsaccepted(int topicid);
	
	//获取帖子悬赏的飞吻数
	int geTopictRewardKiss(int topicid);
	
	//得到未采纳帖子List(每一页的list)
	List<TopicInfoEx> getNotAcceptTopicInfoExList(int pageIndex,int pageSize);
	
	//得到已采纳帖子List(每一页的list)
	List<TopicInfoEx> getAcceptTopicInfoExList(int pageIndex,int pageSize);
	
	//得到精帖List(每一页的list)
	List<TopicInfoEx> getNiceTopicInfoExList(int pageIndex,int pageSize);
	
	//得到我的帖子List(每一页的list)
	List<TopicInfoEx> getMyTopicTopicInfoExList(int pageIndex,int pageSize,int userid);
	
	//搜索相关帖子，只根据标题List(每一页的list)
	List<TopicInfoEx> getSearchTopicInfoExList(int pageIndex,int pageSize, String SearchStr);

	
	//得到未采纳帖子的总数
	int getNotAcceptTopicNum();
	
	//得到已采纳帖子的总数
	int getAcceptTopicNum();
	
	//得到精帖的总数
	int getNiceTopicNum();
	
	//得到我的帖子的总数
	int getMyTopicTopicNum(int userid);
	
	//搜索相关帖子的总数
	int getSearchTopicNum(String SearchStr);
	
	//设置帖子为采纳
	boolean acceptTopic(int topicid);
	
	//获取一周内top回答榜，不包括回复的回复
	List<TopicInfoEx> getWeekAnswerList();
	
	//增加帖子的评论数量
	boolean addTopicAnswerNum(int topicid);
	
	//减少帖子评论数量
	boolean decTopicAnswerNum(int topicid);
	
	//根据帖子id获取用户id
	int getUseridByTopicid(int topicid);
	
	//获取10天内热视榜（前10）
	List<TopicInfoEx> getHotViewList();
	
	//获取10天内热评榜（前10）
	List<TopicInfoEx> getHotReplyList();
	
	//获取飞吻前8榜
	List<TopicInfoEx> getKissNumList();
	
	//最近提问(个人主页显示)
	List<TopicInfoEx> getUserNearQuestionList(int userid);	
	
	//最近回答(个人主页显示，不包括评论的评论)
	List<CommentEx> getUserNearAnswerList(int userid);	
	
	//我的发帖(即当前用户的全部帖子，带分页功能)
	List<TopicInfoEx> getMyTopicList(int userid , int pageIndex , int pageSize);
	
	//我的收藏(即当前用户收藏的全部帖子，带分页功能)
	List<TopicInfoEx> getMyCollectTopicList(int userid , int pageIndex , int pageSize);
	
	//获取我收藏的帖子的数量
	int getMyCollectNum(int userid);
}
