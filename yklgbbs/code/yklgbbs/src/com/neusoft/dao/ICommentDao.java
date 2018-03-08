package com.neusoft.dao;

import java.util.List;

import com.neusoft.bean.Comment;
import com.neusoft.bean.CommentEx;
import com.neusoft.bean.MessageEx;

public interface ICommentDao {
	//添加一条评论
	int[] addComment(Comment comment);
	
	//查询即将评论的帖子是否已经结贴
	boolean checkIsEnd(int topicId);
	
	//返回所有评论的集合
	List<CommentEx> getAllComment(int topicId);
	
	int getCommentNum(int topicId);
	
	//增加某个评论的赞数
	boolean increaseSupportNum(int commentid);
	
	//查询评论是否属于本作者
	boolean checkCommentBelongAuthor(int commentid,int userid);
	
	//采纳评论
	boolean acceptComment(int commentid);
	
	//获取评论者的id
	int getCommentUserid(int commentid);
	
	//删除某个评论
	boolean deleteComment(int commentid);
	
	//获取评论内容
	String getCommentContent(int commentid);
	
	//修改评论的内容
	boolean editComment(int commentid,String newContent);
	
	//添加一个message
	boolean addMessage(int commentid,int topicUserid);
	
	//获取我的全部消息（即获取当前用户的全部消息）
	List<MessageEx> getMyMessageList(int userid , int pageIndex , int pageSize);
	
	//获取当前用户的消息总数
	int getMyMessageNum(int userid);
	
	//查询该消息是否属于当前用户
	boolean checkMessageIsBelongNowUser(int messageid,int userid);
	
	//删除一个message
	boolean deleteOneMessage(int messageid);
	
	//删除全部消息
	boolean deleteAllMessage(int userid);
}
