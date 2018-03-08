package com.neusoft.bean;

public class TopicInfoEx {
	private String headUrl;
	private String title;
	private String nickName;
	private String createTime; //这个就直接转换为字符串吧
	private String classname;
	private int commentCount;
	private int viewCount;
	//下面是新增的
	private int id;  //帖子的id
	private String content; //帖子的内容
	private int rewardKiss; //帖子的悬赏飞吻数
	private int userId;  //这个是为了点击index中帖子概要信息中的用户头像直接跳到用户的主页。
	private int isGood;  //是否为加精贴，0为不加精，1为加精
	private int isTop;  //是否为顶置贴
	private int isEnd;  //是否结贴
	private int answerNum; //帖子评论数量，不包括回复的回复
	private int KissNum; //用户拥有的飞吻总数
	private String collectTopicTime;
	
	public String getCollectTopicTime() {
		return collectTopicTime;
	}
	public void setCollectTopicTime(String collectTopicTime) {
		this.collectTopicTime = collectTopicTime;
	}
	public int getKissNum() {
		return KissNum;
	}
	public void setKissNum(int kissNum) {
		KissNum = kissNum;
	}
	public int getAnswerNum() {
		return answerNum;
	}
	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}
	public int getIsTop() {
		return isTop;
	}
	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}
	public int getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(int isEnd) {
		this.isEnd = isEnd;
	}
	public int getIsGood() {
		return isGood;
	}
	public void setIsGood(int isGood) {
		this.isGood = isGood;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRewardKiss() {
		return rewardKiss;
	}
	public void setRewardKiss(int rewardKiss) {
		this.rewardKiss = rewardKiss;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	
}
