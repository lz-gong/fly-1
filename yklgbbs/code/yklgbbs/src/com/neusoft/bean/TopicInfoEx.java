package com.neusoft.bean;

public class TopicInfoEx {
	private String headUrl;
	private String title;
	private String nickName;
	private String createTime; //�����ֱ��ת��Ϊ�ַ�����
	private String classname;
	private int commentCount;
	private int viewCount;
	//������������
	private int id;  //���ӵ�id
	private String content; //���ӵ�����
	private int rewardKiss; //���ӵ����ͷ�����
	private int userId;  //�����Ϊ�˵��index�����Ӹ�Ҫ��Ϣ�е��û�ͷ��ֱ�������û�����ҳ��
	private int isGood;  //�Ƿ�Ϊ�Ӿ�����0Ϊ���Ӿ���1Ϊ�Ӿ�
	private int isTop;  //�Ƿ�Ϊ������
	private int isEnd;  //�Ƿ����
	private int answerNum; //���������������������ظ��Ļظ�
	private int KissNum; //�û�ӵ�еķ�������
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
