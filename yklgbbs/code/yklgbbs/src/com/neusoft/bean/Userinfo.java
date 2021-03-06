package com.neusoft.bean;

import java.util.*;
import com.tgb.lk.annotation.*;

@AutoBean(alias = "Userinfo",table="tab_bbs_userinfo")
public class Userinfo {
	
  @AutoField(alias = "序号", column = "id", isKey = true , isRequired = true , type="Integer", length=0)
  @ExcelVOAttribute(name = "id", column = "A")
  private int id;

  @AutoField(alias = "email", column = "email", isRequired = true, length = 60)
  @ExcelVOAttribute(name = "email", column = "B")
  private String email;

  @AutoField(alias = "nickname", column = "nickname", isRequired = true, length = 100)
  @ExcelVOAttribute(name = "nickname", column = "C")
  private String nickname;

  @AutoField(alias = "city", column = "city", length = 100)
  @ExcelVOAttribute(name = "city", column = "D")
  private String city;

  @AutoField(alias = "sex", column = "sex", isRequired = true, type = "Integer", combo = {"男","女"})
  @ExcelVOAttribute(name = "sex", column = "E")
  private int sex;

  @AutoField(alias = "headUrl", column = "head_url", isRequired = true, length = 100)
  @ExcelVOAttribute(name = "head_url", column = "F")
  private String headUrl;

  @AutoField(alias = "password", column = "password", isRequired = true, length = 64)
  @ExcelVOAttribute(name = "password", column = "G")
  private String password;

  @AutoField(alias = "signName", column = "sign_name", isRequired = true, length = 500)
  @ExcelVOAttribute(name = "sign_name", column = "H")
  private String signName;

  @AutoField(alias = "kissNum", column = "kiss_num", isRequired = true, type = "Integer")
  @ExcelVOAttribute(name = "kiss_num", column = "I")
  private int kissNum;
  
  //新添加的
  private String joinTime;  //在注册的时候没有向javabean中存入jointime，是在dao层中直接生成的Date
  private int isManager;  //是否为管理员，0为非管理员，1为管理员
  private Date signTime;
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
public Date getSignTime() {
	return signTime;
}
public void setSignTime(Date signTime) {
	this.signTime = signTime;
}
  public int getIsManager() {
	return isManager;
  }
  public void setIsManager(int isManager) {
	  this.isManager = isManager;
  }
  public String getJoinTime() {
	return joinTime;
  }
  public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
  }
  
  public int getId() {
    return id;
  }
  public void setId(int id){
    this.id = id;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email){
    this.email = email;
  }
  public String getNickname() {
    return nickname;
  }
  public void setNickname(String nickname){
    this.nickname = nickname;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city){
    this.city = city;
  }
  public int getSex() {
    return sex;
  }
  public void setSex(int sex){
    this.sex = sex;
  }
  public String getHeadUrl() {
    return headUrl;
  }
  public void setHeadUrl(String headUrl){
    this.headUrl = headUrl;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password){
    this.password = password;
  }
  public String getSignName() {
    return signName;
  }
  public void setSignName(String signName){
    this.signName = signName;
  }
  public int getKissNum() {
    return kissNum;
  }
  public void setKissNum(int kissNum){
    this.kissNum = kissNum;
  }
  
  @Override
	public String toString() {
		// TODO Auto-generated method stub
		return "城市："+this.city+"  邮箱："+this.email+"  头像地址："+this.headUrl+"  id："+this.id+"  飞吻数："+this.kissNum+"  昵称："+this.nickname+"  密码："+this.password+"  性别："+this.sex+"  签名："+this.signName+"  加入时间："+this.joinTime;
	}
}
