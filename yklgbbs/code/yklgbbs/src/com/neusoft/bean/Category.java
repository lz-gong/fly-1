package com.neusoft.bean;

import java.util.*;
import com.tgb.lk.annotation.*;

@AutoBean(alias = "Category",table="tab_bbs_category")
public class Category {

  @AutoField(alias = "ÐòºÅ", column = "id", isKey = true , isRequired = true , type="Integer", length=0)
  @ExcelVOAttribute(name = "id", column = "A")
  private int id;

  @AutoField(alias = "classname", column = "classname", isRequired = true, length = 100)
  @ExcelVOAttribute(name = "classname", column = "B")
  private String classname;


  public int getId() {
    return id;
  }
  public void setId(int id){
    this.id = id;
  }
  public String getClassname() {
    return classname;
  }
  public void setClassname(String classname){
    this.classname = classname;
  }
}
