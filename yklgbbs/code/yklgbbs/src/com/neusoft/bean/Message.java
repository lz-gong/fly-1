package com.neusoft.bean;

public class Message {
	private int id;
	private int commentId;
	private int topicUserid; //评论的帖子所属用户的id
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getTopicUserid() {
		return topicUserid;
	}
	public void setTopicUserid(int topicUserid) {
		this.topicUserid = topicUserid;
	}
	
}
