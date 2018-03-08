package com.neusoft.bean;

public class CommentEx {
	private int id;  //评论的id
	private String headUrl;
	private String nickName;
	private String createTime;
	private String content;
	private int isAccepted;
	private int supportNum;
	
	//新增的
	private boolean showAccept; //用于验证遍历出来的评论是否在页面显示采纳按钮
	private int userId;
	private boolean showDelete;
	private boolean showEdit;
	private int isTopic;
	private int topicOrCommentId;
	private String topicTitle; //评论的标题的id
	private int experience;
	private String grade;
	
	
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getTopicTitle() {
		return topicTitle;
	}
	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}
	public int getTopicOrCommentId() {
		return topicOrCommentId;
	}
	public void setTopicOrCommentId(int topicOrCommentId) {
		this.topicOrCommentId = topicOrCommentId;
	}
	public int getIsTopic() {
		return isTopic;
	}
	public void setIsTopic(int isTopic) {
		this.isTopic = isTopic;
	}
	public boolean isShowEdit() {
		return showEdit;
	}
	public void setShowEdit(boolean showEdit) {
		this.showEdit = showEdit;
	}
	public boolean isShowDelete() {
		return showDelete;
	}
	public void setShowDelete(boolean showDelete) {
		this.showDelete = showDelete;
	}
	public boolean isShowAccept() {
		return showAccept;
	}
	public void setShowAccept(boolean showAccept) {
		this.showAccept = showAccept;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIsAccepted() {
		return isAccepted;
	}
	public void setIsAccepted(int isAccepted) {
		this.isAccepted = isAccepted;
	}
	public int getSupportNum() {
		return supportNum;
	}
	public void setSupportNum(int supportNum) {
		this.supportNum = supportNum;
	}
	
}
