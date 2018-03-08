package com.neusoft.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.neusoft.utils.OtherUtil;

public class Temp {
	public static void main(String[] args) {
		/*//将数字转换为json字符串
		System.out.println(JSON.toJSONString(123)); //输出结果：123
		//将字符串转换为json字符串
		System.out.println(JSON.toJSONString("你好")); //输出结果："你好"
		//将对象转换为json字符串
		Userinfo user=new Userinfo();
		System.out.println(JSON.toJSONString(user)); //此时只输出：{"id":0,"kissNum":0,"sex":0}
													 //并且只输出：int类型属性
		user.setId(101);
		System.out.println(JSON.toJSONString(user)); //此时只输出：{"id":101,"kissNum":0,"sex":0}
		user.setCity("朝阳");
		System.out.println(JSON.toJSONString(user)); //输出结果：{"city":"朝阳","id":0,"kissNum":0,"sex":0}\
		System.out.println(JSON.toJSONString(true).equals("true")); //说明true也被转换成了字符串
		
		List list=new ArrayList();
		list.add("你好");
		list.add("我是宫佳男");
		String jsonStr=JSON.toJSONString(list);
		System.out.println(jsonStr);
		
		List list2=new ArrayList();
		list2.add("你好");
		list2.add("我是宫佳男");
		String jsonString=JSON.toJSONString(list2);
		System.out.println(JSON.toJSONString(jsonString));*/
		
		//情况1
//		String text="宫a宫a宫a宫a宫";
//		String index="a";
//		String arr1[]=text.split(index);
//		String arr2[]=OtherUtil.splitRewrite(text, index);
//		System.out.println("自带方法分割长度："+arr1.length);
//		System.out.println("我的方法分割长度："+arr2.length);	
		
		//情况2
//		String text="aaaa";
//		String index="a";
//		String arr1[]=text.split(index);
//		String arr2[]=OtherUtil.splitRewrite(text, index);
//		System.out.println("自带方法分割长度："+arr1.length);
//		System.out.println("我的方法分割长度："+arr2.length);
		
//		//情况3
//		String text="宫a宫a宫a宫a";
//		String index="a";
//		String arr1[]=text.split(index);
//		String arr2[]=OtherUtil.splitRewrite(text, index);
//		System.out.println("自带方法分割长度："+arr1.length);
//		System.out.println("我的方法分割长度："+arr2.length);
//		
//		int arr[]=new int[1];
//		System.out.println(arr.length);
		
		System.out.println(new java.sql.Date(946656000000l));
		System.out.println(new Date(946656000000l));
		
		
	}
	
	
	public static int temp() {
		try{
			System.out.println("进入try块");
			return 2;
		}catch(Exception e) {
		
		}finally {
			System.out.println("进来了！");
		}
		return 1;
	}
}