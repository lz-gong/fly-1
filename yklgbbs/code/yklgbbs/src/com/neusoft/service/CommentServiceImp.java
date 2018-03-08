package com.neusoft.service;

import java.util.List;

import com.neusoft.bean.Comment;
import com.neusoft.bean.CommentEx;
import com.neusoft.bean.MessageEx;
import com.neusoft.dao.CommentDaoImp;
import com.neusoft.dao.ICommentDao;

public class CommentServiceImp implements ICommentService{

	//本方法体现了管家的价值
	@Override
	public int[] addComment(Comment comment) {
		//评论之前先要判断是否已经结贴，这样是为了防止用户非法修改提交信息给已经结贴的帖子进行回复
		ICommentDao icd=new CommentDaoImp();
		//如果评论的是帖子的话，判断是否已经结贴
		if(comment.getIsTopic()==0) {
			if(icd.checkIsEnd(comment.getTopicOrCommentId())==true) { //如果为true说明已经结帖子
				int temp[]=new int[1];
				temp[0]=2;
				return temp;
			}
		}
		int arr[]=icd.addComment(comment);
		return arr;
	}

	@Override
	public List<CommentEx> getAllComment(int topicId) {
		ICommentDao icd=new CommentDaoImp();
		List<CommentEx> list=icd.getAllComment(topicId);
		return list;
	}

	@Override
	public int getCommentNum(int topicId) {
		ICommentDao icd=new CommentDaoImp();
		return icd.getCommentNum(topicId);
	}

	@Override
	public boolean increaseSupportNum(int commentid) {
		ICommentDao icd=new CommentDaoImp();
		return icd.increaseSupportNum(commentid);
	}

	@Override
	public boolean checkCommentBelongAuthor(int commentid, int userid) {
		ICommentDao icd=new CommentDaoImp();
		return icd.checkCommentBelongAuthor(commentid, userid);
	}

	@Override
	public boolean acceptComment(int commentid) {
		ICommentDao icd=new CommentDaoImp();
		return icd.acceptComment(commentid);
	}

	@Override
	public int getCommentUserid(int commentid) {
		ICommentDao icd=new CommentDaoImp();
		return icd.getCommentUserid(commentid);
	}

	@Override
	public boolean deleteComment(int commentid) {
		ICommentDao icd=new CommentDaoImp();
		return icd.deleteComment(commentid);
	}

	@Override
	public String getCommentContent(int commentid) {
		ICommentDao icd=new CommentDaoImp();
		return icd.getCommentContent(commentid);
	}

	@Override
	public boolean editComment(int commentid, String newContent) {
		ICommentDao icd=new CommentDaoImp();
		return icd.editComment(commentid, newContent);
	}

	@Override
	public boolean addMessage(int commentid, int topicUserid) {
		ICommentDao icd=new CommentDaoImp();
		return icd.addMessage(commentid, topicUserid);
	}

	@Override
	public List<MessageEx> getMyMessageList(int userid, int pageIndex, int pageSize) {
		ICommentDao icd=new CommentDaoImp();
		return icd.getMyMessageList(userid, pageIndex, pageSize);
	}

	@Override
	public int getMyMessageNum(int userid) {
		ICommentDao icd=new CommentDaoImp();
		return icd.getMyMessageNum(userid);
	}

	@Override
	public boolean checkMessageIsBelongNowUser(int messageid,int userid) {
		ICommentDao icd=new CommentDaoImp();
		return icd.checkMessageIsBelongNowUser(messageid, userid);
	}

	@Override
	public boolean deleteOneMessage(int messageid) {
		ICommentDao icd=new CommentDaoImp();
		return icd.deleteOneMessage(messageid);
	}

	@Override
	public boolean deleteAllMessage(int userid) {
		ICommentDao icd=new CommentDaoImp();
		return icd.deleteAllMessage(userid);
	}
}
