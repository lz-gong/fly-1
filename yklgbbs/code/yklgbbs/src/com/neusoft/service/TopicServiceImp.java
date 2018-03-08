package com.neusoft.service;

import java.util.List;

import com.neusoft.bean.Category;
import com.neusoft.bean.CommentEx;
import com.neusoft.bean.TopicInfoEx;
import com.neusoft.bean.Topicinfo;
import com.neusoft.dao.ITopicDao;
import com.neusoft.dao.TopicDaoImp;

public class TopicServiceImp implements ITopicService{

	@Override
	public List<Category> getCategoryInfo() {
		ITopicDao itd=new TopicDaoImp();
		List<Category> list=itd.getCategoryInfo();
		return list;
	}

	@Override 
	public int addTopic(Topicinfo topicinfo) {
		
		ITopicDao itd=new TopicDaoImp();
		int num = itd.addTopic(topicinfo); //可见Service和dao做的是同样的事情
		
		return num;
	}

	@Override
	public List<TopicInfoEx> getAllTopic() {
		ITopicDao itd=new TopicDaoImp();
		List<TopicInfoEx> list =itd.getAllTopic();
		return list;
	}
	
	//可见此时的Service做了两件事，这里体现了Service的价值。
	@Override
	public TopicInfoEx topicShowView(int id) {
		//首先增加帖子访问量
		ITopicDao tid=new TopicDaoImp();
		tid.viewConutIncrement(id);
		//然后获取帖子详细信息！
		TopicInfoEx topicInfoEx=tid.topicShowView(id);
		if(topicInfoEx!=null) {
			topicInfoEx.setCommentCount(new CommentServiceImp().getCommentNum(id));
		}
		return topicInfoEx;
	}

	@Override
	public int getTopicNum() {
		ITopicDao itd=new TopicDaoImp();
		return itd.getTopicNum();
	}

	@Override
	public List<TopicInfoEx> getTopicInfoExList(int pageIndex, int pageSize) {
		ITopicDao itd=new TopicDaoImp();
		List<TopicInfoEx> list=itd.getTopicInfoExList(pageIndex, pageSize);
		if(list!=null && list.size()>0) {
			ICommentService ics=new CommentServiceImp(); //为了获取帖子的评论数量
			for(TopicInfoEx tie:list) {
				tie.setCommentCount(ics.getCommentNum(tie.getId()));
			}
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getTopTopicInfoExList() {
		ITopicDao itd=new TopicDaoImp();
		List<TopicInfoEx> list=itd.getTopTopicInfoExList();
		if(list!=null && list.size()>0) {
			ICommentService ics=new CommentServiceImp(); //为了获取帖子的评论数量
			for(TopicInfoEx tie:list) {
				tie.setCommentCount(ics.getCommentNum(tie.getId()));
			}
		}
		return list;
	}

	@Override
	public boolean deleteTopic(int topicid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.deleteTopic(topicid);
	}

	@Override
	public boolean setTopicTopGood(int topicid, int isTop, int isGood) {
		ITopicDao itd=new TopicDaoImp();
		return itd.setTopicTopGood(topicid, isTop, isGood);
	}

	@Override
	public boolean checkTopicBelongAuthor(int topicid, int userid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.checkTopicBelongAuthor(topicid, userid);
	}

	@Override
	public boolean editTopic(int topicid,String title,String content) {
		ITopicDao itd=new TopicDaoImp();
		return itd.editTopic(topicid, title, content);
	}

	@Override
	public TopicInfoEx getEditTopic(int topicid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.getEditTopic(topicid);
	}

	@Override
	public boolean findTopic(int topicid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.findTopic(topicid);
	}

	@Override
	public boolean collectTopic(int topicid, int userid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.collectTopic(topicid, userid);
	}

	@Override
	public boolean cancleCollectTopic(int topicid, int userid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.cancleCollectTopic(topicid, userid);
	}

	@Override
	public boolean checkRepeatCollect(int topicid, int userid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.checkRepeatCollect(topicid, userid);
	}

	@Override
	public boolean checkTopicIsend(int topicid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.checkTopicIsend(topicid);
	}

	@Override
	public boolean checkTopicIsaccepted(int topicid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.checkTopicIsaccepted(topicid);
	}

	@Override
	public int geTopictRewardKiss(int topicid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.geTopictRewardKiss(topicid);
	}

	@Override
	public List<TopicInfoEx> getNotAcceptTopicInfoExList(int pageIndex, int pageSize) {
		ITopicDao itd=new TopicDaoImp();
		List<TopicInfoEx> list=itd.getNotAcceptTopicInfoExList(pageIndex, pageSize);
		if(list!=null && list.size()>0) {
			ICommentService ics=new CommentServiceImp(); //为了获取帖子的评论数量
			for(TopicInfoEx tie:list) {
				tie.setCommentCount(ics.getCommentNum(tie.getId()));
			}
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getAcceptTopicInfoExList(int pageIndex, int pageSize) {
		ITopicDao itd=new TopicDaoImp();
		List<TopicInfoEx> list=itd.getAcceptTopicInfoExList(pageIndex, pageSize);
		if(list!=null && list.size()>0) {
			ICommentService ics=new CommentServiceImp(); //为了获取帖子的评论数量
			for(TopicInfoEx tie:list) {
				tie.setCommentCount(ics.getCommentNum(tie.getId()));
			}
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getNiceTopicInfoExList(int pageIndex, int pageSize) {
		ITopicDao itd=new TopicDaoImp();
		List<TopicInfoEx> list=itd.getNiceTopicInfoExList(pageIndex, pageSize);
		if(list!=null && list.size()>0) {
			ICommentService ics=new CommentServiceImp(); //为了获取帖子的评论数量
			for(TopicInfoEx tie:list) {
				tie.setCommentCount(ics.getCommentNum(tie.getId()));
			}
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getMyTopicTopicInfoExList(int pageIndex, int pageSize,int userid) {
		ITopicDao itd=new TopicDaoImp();
		List<TopicInfoEx> list=itd.getMyTopicTopicInfoExList(pageIndex, pageSize,userid);
		if(list!=null && list.size()>0) {
			ICommentService ics=new CommentServiceImp(); //为了获取帖子的评论数量
			for(TopicInfoEx tie:list) {
				tie.setCommentCount(ics.getCommentNum(tie.getId()));
			}
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getSearchTopicInfoExList(int pageIndex, int pageSize, String SearchStr) {
		ITopicDao itd=new TopicDaoImp();
		List<TopicInfoEx> list=itd.getSearchTopicInfoExList(pageIndex, pageSize,SearchStr);
		if(list!=null && list.size()>0) {
			ICommentService ics=new CommentServiceImp(); //为了获取帖子的评论数量
			for(TopicInfoEx tie:list) {
				tie.setCommentCount(ics.getCommentNum(tie.getId()));
			}
		}
		return list;
	}

	@Override
	public int getNotAcceptTopicNum() {
		ITopicDao itd=new TopicDaoImp();
		return itd.getNotAcceptTopicNum();
	}

	@Override
	public int getAcceptTopicNum() {
		ITopicDao itd=new TopicDaoImp();
		return itd.getAcceptTopicNum();
	}

	@Override
	public int getNiceTopicNum() {
		ITopicDao itd=new TopicDaoImp();
		return itd.getNiceTopicNum();
	}

	@Override
	public int getMyTopicTopicNum(int userid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.getMyTopicTopicNum(userid);
	}

	@Override
	public int getSearchTopicNum(String SearchStr) {
		ITopicDao itd=new TopicDaoImp();
		return itd.getSearchTopicNum(SearchStr);
	}

	@Override
	public boolean acceptTopic(int topicid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.acceptTopic(topicid);
	}

	@Override
	public List<TopicInfoEx> getWeekAnswerList() {
		ITopicDao itd=new TopicDaoImp();
		return itd.getWeekAnswerList();
	}

	@Override
	public boolean addTopicAnswerNum(int topicid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.addTopicAnswerNum(topicid);
	}

	@Override
	public boolean decTopicAnswerNum(int topicid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.decTopicAnswerNum(topicid);
	}

	@Override
	public int getUseridByTopicid(int topicid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.getUseridByTopicid(topicid);
	}

	@Override
	public List<TopicInfoEx> getHotViewList() {
		ITopicDao itd=new TopicDaoImp();
		return itd.getHotViewList();
	}

	@Override
	public List<TopicInfoEx> getHotReplyList() {
		ITopicDao itd=new TopicDaoImp();
		return itd.getHotReplyList();
	}

	@Override
	public List<TopicInfoEx> getKissNumList() {
		ITopicDao itd=new TopicDaoImp();
		return itd.getKissNumList();
	}

	@Override
	public List<TopicInfoEx> getUserNearQuestionList(int userid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.getUserNearQuestionList(userid);
	}

	@Override
	public List<CommentEx> getUserNearAnswerList(int userid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.getUserNearAnswerList(userid);
	}

	@Override
	public List<TopicInfoEx> getMyTopicList(int userid, int pageIndex, int pageSize) {
		ITopicDao itd=new TopicDaoImp();
		return itd.getMyTopicList(userid, pageIndex, pageSize);
	}

	@Override
	public List<TopicInfoEx> getMyCollectTopicList(int userid, int pageIndex, int pageSize) {
		ITopicDao itd=new TopicDaoImp();
		return itd.getMyCollectTopicList(userid, pageIndex, pageSize);
	}

	@Override
	public int getMyCollectNum(int userid) {
		ITopicDao itd=new TopicDaoImp();
		return itd.getMyCollectNum(userid);
	}
}
