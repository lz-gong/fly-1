package com.neusoft.bean;

import java.util.*;
import com.tgb.lk.annotation.*;

@AutoBean(alias = "Comment",table="tab_bbs_comment")
public class Comment {

  @AutoField(alias = "ÐòºÅ", column = "id", isKey = true , isRequired = true , type="Integer", length=0)
  @ExcelVOAttribute(name = "id", column = "A")
  private int id;

  @AutoField(alias = "topicOrCommentId", column = "topic_or_comment_id", isRequired = true, type = "Integer")
  @ExcelVOAttribute(name = "topic_or_comment_id", column = "B")
  private int topicOrCommentId;

  @AutoField(alias = "isTopic", column = "is_topic", isRequired = true, type = "Integer")
  @ExcelVOAttribute(name = "is_topic", column = "C")
  private int isTopic;

  @AutoField(alias = "commentContent", column = "comment_content", isRequired = true, length = 2000)
  @ExcelVOAttribute(name = "comment_content", column = "D")
  private String commentContent;

  @AutoField(alias = "userid", column = "userid", isRequired = true, type = "Integer")
  @ExcelVOAttribute(name = "userid", column = "E")
  private int userid;

  @AutoField(alias = "commentTime", column = "comment_time", isRequired = true, type = "Date")
  @ExcelVOAttribute(name = "comment_time", column = "F")
  private Date commentTime;

  @AutoField(alias = "supportNum", column = "support_num", isRequired = true, type = "Integer")
  @ExcelVOAttribute(name = "support_num", column = "G")
  private int supportNum;

  @AutoField(alias = "isAccepted", column = "is_accepted", isRequired = true, type = "Integer")
  @ExcelVOAttribute(name = "is_accepted", column = "H")
  private int isAccepted;


  public int getId() {
    return id;
  }
  public void setId(int id){
    this.id = id;
  }
  public int getTopicOrCommentId() {
    return topicOrCommentId;
  }
  public void setTopicOrCommentId(int topicOrCommentId){
    this.topicOrCommentId = topicOrCommentId;
  }
  public int getIsTopic() {
    return isTopic;
  }
  public void setIsTopic(int isTopic){
    this.isTopic = isTopic;
  }
  public String getCommentContent() {
    return commentContent;
  }
  public void setCommentContent(String commentContent){
    this.commentContent = commentContent;
  }
  public int getUserid() {
    return userid;
  }
  public void setUserid(int userid){
    this.userid = userid;
  }
  public Date getCommentTime() {
    return commentTime;
  }
  public void setCommentTime(Date commentTime){
    this.commentTime = commentTime;
  }
  public int getSupportNum() {
    return supportNum;
  }
  public void setSupportNum(int supportNum){
    this.supportNum = supportNum;
  }
  public int getIsAccepted() {
    return isAccepted;
  }
  public void setIsAccepted(int isAccepted){
    this.isAccepted = isAccepted;
  }
}
