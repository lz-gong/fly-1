package com.neusoft.utils;

public class GradeUtil {
	public static String getGrade(int experience) {
        if(experience<=50){
            return "������˿";
        }else if(experience>50 && experience<=150){
            return "�м���˿";
        }else if(experience>150 && experience<=300){
            return "�߼���˿";
        }else if(experience>300 && experience<=500){
            return "��ʽ��Ա";
        }else if(experience>500 && experience<=750){
            return "���Ļ�Ա";
        }else if(experience>750 && experience<=1050){
            return "���˻�Ա";
        }else if(experience>1050 && experience<=1400){
            return "֪����ʿ";
        }else if(experience>1400 && experience<=1800){
            return "������ģ";
        }else if(experience>1800 && experience<=2250){
            return "�������";
        }else if(experience>2250 && experience<=3250){
            return "����Ԫ��";
        }else if(experience>3250 && experience<=5000){
            return "����Ԫ��";
        }else if(experience>5000 && experience<=8000){
            return "��ҫԪ��";
        }else if(experience>8000){
            return "����";
        }else{
            return "������";
        }
	}
}