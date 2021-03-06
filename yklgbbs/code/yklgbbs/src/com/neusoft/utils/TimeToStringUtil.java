package com.neusoft.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//将Date转换为时间戳
public class TimeToStringUtil {
	
	//将Date转换为时间戳
	public static Timestamp dateToTiemstamp(Date date) {
		return new Timestamp(date.getTime());
	}
	
	//即忽略时分秒比较日期是否相等
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
			System.out.println("传入的日期参数含有null");
			return false;
		}
	}
	
	public static String timestampToStringtime(Timestamp timestamp) {
		if(timestamp!=null) {
			Date date=new Date(timestamp.getTime());
			return parse(date);
		}else {
			return "传入的Timestamp对象为null";
		}
	}
	
	public static String timestampToStringtime(Date date) {
		if(date!=null) {
			return parse(date);
		}else {
			return "传入的Date对象为null";
		}
	}
	
	public static String timestampToStringtime(String strDate) {
		if(strDate!=null) {
			Date date=null;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				date=sdf.parse(strDate);
			} catch (ParseException e) {
				return "请输入正确日期格式，例如：2018-1-1 00:00:00";
			}
			return parse(date);
		}else {
			return "传入的String对象为null";
		}
	}
	
	private static String parse(Date date) {
		Date nowTime=new Date();
		SimpleDateFormat sdf=null;
		sdf=new SimpleDateFormat("yyyy-MM-dd");
		String temp=sdf.format(nowTime);
		Date todayTime = null;
		try {
			//今天0点的时间
			todayTime = sdf.parse(temp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long temp1=nowTime.getTime()-date.getTime();  //现在的时间 - 日期的时间
		long temp2=nowTime.getTime()-todayTime.getTime(); //现在的时间 - 今天0点的时间 。  比较哪个大
		if(temp1<=temp2) {
			if(temp1<=600000) {
				return "刚刚";
			}else if(temp1>=600000 && temp1<3600000) {
				return temp1/60000+"分钟前";
			}else if(temp1>=3600000 && temp1<86400000) {
				sdf=new SimpleDateFormat("HH:mm");
				return "今天"+sdf.format(date);
			}
		}else {
			long temp3=temp1-temp2;
			sdf=new SimpleDateFormat("HH:mm");
			if(temp3<86400000) {
				return "昨天"+sdf.format(date);
			}else if(temp3>=86400000 && temp3<=86400000*2){
				return "前天"+sdf.format(date);
			}else {
				sdf=new SimpleDateFormat("yyyy");
				String temp4=sdf.format(date);
				String temp5=sdf.format(nowTime);
				if(temp4.equals(temp5)) {
					sdf=new SimpleDateFormat("MM月dd日  HH:mm");
					return sdf.format(date);
				}else {
					sdf=new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
					return sdf.format(date);
				}
			}
		}
		return "";
	}
	
	//原版
/*	//将Timestamp转换为字符串Timestamp timestamp
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
				System.out.println("请输入正确日期格式，例如：2018-1-1 00:00:00");
			}
		}else {
			return "该类型不可转换！";
		}
		Date nowTime=new Date();
		SimpleDateFormat sdf=null;
		sdf=new SimpleDateFormat("yyyy-MM-dd");
		String temp=sdf.format(nowTime);
		Date todayTime = null;
		try {
			//今天0点的时间
			todayTime = sdf.parse(temp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long temp1=nowTime.getTime()-date.getTime();  //现在的时间 - 日期的时间
		long temp2=nowTime.getTime()-todayTime.getTime(); //现在的时间 - 今天0点的时间 。  比较哪个大
		if(temp1<=temp2) {
			if(temp1<=600000) {
				return "刚刚";
			}else if(temp1>=600000 && temp1<3600000) {
				return temp1/60000+"分钟前";
			}else if(temp1>=3600000 && temp1<86400000) {
				sdf=new SimpleDateFormat("HH:mm");
				return "今天"+sdf.format(date);
			}
		}else {
			long temp3=temp1-temp2;
			sdf=new SimpleDateFormat("HH:mm");
			if(temp3<86400000) {
				return "昨天"+sdf.format(date);
			}else if(temp3>=86400000 && temp3<=86400000*2){
				return "前天"+sdf.format(date);
			}else {
				sdf=new SimpleDateFormat("yyyy");
				String temp4=sdf.format(date);
				String temp5=sdf.format(nowTime);
				if(temp4.equals(temp5)) {
					sdf=new SimpleDateFormat("MM月dd日  HH:mm");
					return sdf.format(date);
				}else {
					sdf=new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
					return sdf.format(date);
				}
			}
		}
		return "";
	}*/
}


