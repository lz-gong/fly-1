package com.neusoft.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//��Dateת��Ϊʱ���
public class TimeToStringUtil {
	
	//��Dateת��Ϊʱ���
	public static Timestamp dateToTiemstamp(Date date) {
		return new Timestamp(date.getTime());
	}
	
	//������ʱ����Ƚ������Ƿ����
	public static boolean checkStringTimeIsEqulas(Date date1,Date date2) {
		if(date1!=null && date2!=null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String time1=sdf.format(date1);
			String time2=sdf.format(date2);
			if(time1.equals(time2)) {
				return true;
			}else {
				return false;
			}
		}else {
			System.out.println("��������ڲ�������null");
			return false;
		}
	}
	
	public static String timestampToStringtime(Timestamp timestamp) {
		if(timestamp!=null) {
			Date date=new Date(timestamp.getTime());
			return parse(date);
		}else {
			return "�����Timestamp����Ϊnull";
		}
	}
	
	public static String timestampToStringtime(Date date) {
		if(date!=null) {
			return parse(date);
		}else {
			return "�����Date����Ϊnull";
		}
	}
	
	public static String timestampToStringtime(String strDate) {
		if(strDate!=null) {
			Date date=null;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				date=sdf.parse(strDate);
			} catch (ParseException e) {
				return "��������ȷ���ڸ�ʽ�����磺2018-1-1 00:00:00";
			}
			return parse(date);
		}else {
			return "�����String����Ϊnull";
		}
	}
	
	private static String parse(Date date) {
		Date nowTime=new Date();
		SimpleDateFormat sdf=null;
		sdf=new SimpleDateFormat("yyyy-MM-dd");
		String temp=sdf.format(nowTime);
		Date todayTime = null;
		try {
			//����0���ʱ��
			todayTime = sdf.parse(temp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long temp1=nowTime.getTime()-date.getTime();  //���ڵ�ʱ�� - ���ڵ�ʱ��
		long temp2=nowTime.getTime()-todayTime.getTime(); //���ڵ�ʱ�� - ����0���ʱ�� ��  �Ƚ��ĸ���
		if(temp1<=temp2) {
			if(temp1<=600000) {
				return "�ո�";
			}else if(temp1>=600000 && temp1<3600000) {
				return temp1/60000+"����ǰ";
			}else if(temp1>=3600000 && temp1<86400000) {
				sdf=new SimpleDateFormat("HH:mm");
				return "����"+sdf.format(date);
			}
		}else {
			long temp3=temp1-temp2;
			sdf=new SimpleDateFormat("HH:mm");
			if(temp3<86400000) {
				return "����"+sdf.format(date);
			}else if(temp3>=86400000 && temp3<=86400000*2){
				return "ǰ��"+sdf.format(date);
			}else {
				sdf=new SimpleDateFormat("yyyy");
				String temp4=sdf.format(date);
				String temp5=sdf.format(nowTime);
				if(temp4.equals(temp5)) {
					sdf=new SimpleDateFormat("MM��dd��  HH:mm");
					return sdf.format(date);
				}else {
					sdf=new SimpleDateFormat("yyyy��MM��dd��  HH:mm");
					return sdf.format(date);
				}
			}
		}
		return "";
	}
	
	//ԭ��
/*	//��Timestampת��Ϊ�ַ���Timestamp timestamp
	static public String timestampToStringtime(Object obj) {
		Date date=null;
		if(obj instanceof java.util.Date) {
			date=(Date)obj;
		}else if(obj instanceof java.sql.Timestamp) {
			Timestamp timestamp=(Timestamp)obj;
			date=new Date(timestamp.getTime());
		}else if(obj instanceof String) {
			String strDate=(String)obj;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				date=sdf.parse(strDate);
			} catch (ParseException e) {
				System.out.println("��������ȷ���ڸ�ʽ�����磺2018-1-1 00:00:00");
			}
		}else {
			return "�����Ͳ���ת����";
		}
		Date nowTime=new Date();
		SimpleDateFormat sdf=null;
		sdf=new SimpleDateFormat("yyyy-MM-dd");
		String temp=sdf.format(nowTime);
		Date todayTime = null;
		try {
			//����0���ʱ��
			todayTime = sdf.parse(temp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long temp1=nowTime.getTime()-date.getTime();  //���ڵ�ʱ�� - ���ڵ�ʱ��
		long temp2=nowTime.getTime()-todayTime.getTime(); //���ڵ�ʱ�� - ����0���ʱ�� ��  �Ƚ��ĸ���
		if(temp1<=temp2) {
			if(temp1<=600000) {
				return "�ո�";
			}else if(temp1>=600000 && temp1<3600000) {
				return temp1/60000+"����ǰ";
			}else if(temp1>=3600000 && temp1<86400000) {
				sdf=new SimpleDateFormat("HH:mm");
				return "����"+sdf.format(date);
			}
		}else {
			long temp3=temp1-temp2;
			sdf=new SimpleDateFormat("HH:mm");
			if(temp3<86400000) {
				return "����"+sdf.format(date);
			}else if(temp3>=86400000 && temp3<=86400000*2){
				return "ǰ��"+sdf.format(date);
			}else {
				sdf=new SimpleDateFormat("yyyy");
				String temp4=sdf.format(date);
				String temp5=sdf.format(nowTime);
				if(temp4.equals(temp5)) {
					sdf=new SimpleDateFormat("MM��dd��  HH:mm");
					return sdf.format(date);
				}else {
					sdf=new SimpleDateFormat("yyyy��MM��dd��  HH:mm");
					return sdf.format(date);
				}
			}
		}
		return "";
	}*/
}

