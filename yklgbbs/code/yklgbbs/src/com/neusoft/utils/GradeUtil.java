package com.neusoft.utils;

public class GradeUtil {
	public static String getGrade(int experience) {
        if(experience<=50){
            return "初级粉丝";
        }else if(experience>50 && experience<=150){
            return "中级粉丝";
        }else if(experience>150 && experience<=300){
            return "高级粉丝";
        }else if(experience>300 && experience<=500){
            return "正式会员";
        }else if(experience>500 && experience<=750){
            return "核心会员";
        }else if(experience>750 && experience<=1050){
            return "铁杆会员";
        }else if(experience>1050 && experience<=1400){
            return "知名人士";
        }else if(experience>1400 && experience<=1800){
            return "人气楷模";
        }else if(experience>1800 && experience<=2250){
            return "意见领袖";
        }else if(experience>2250 && experience<=3250){
            return "进阶元老";
        }else if(experience>3250 && experience<=5000){
            return "资深元老";
        }else if(experience>5000 && experience<=8000){
            return "荣耀元老";
        }else if(experience>8000){
            return "超神";
        }else{
            return "无名者";
        }
	}
}
