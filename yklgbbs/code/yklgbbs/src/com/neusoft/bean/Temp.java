package com.neusoft.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.neusoft.utils.OtherUtil;

public class Temp {
	public static void main(String[] args) {
		/*//������ת��Ϊjson�ַ���
		System.out.println(JSON.toJSONString(123)); //��������123
		//���ַ���ת��Ϊjson�ַ���
		System.out.println(JSON.toJSONString("���")); //��������"���"
		//������ת��Ϊjson�ַ���
		Userinfo user=new Userinfo();
		System.out.println(JSON.toJSONString(user)); //��ʱֻ�����{"id":0,"kissNum":0,"sex":0}
													 //����ֻ�����int��������
		user.setId(101);
		System.out.println(JSON.toJSONString(user)); //��ʱֻ�����{"id":101,"kissNum":0,"sex":0}
		user.setCity("����");
		System.out.println(JSON.toJSONString(user)); //��������{"city":"����","id":0,"kissNum":0,"sex":0}\
		System.out.println(JSON.toJSONString(true).equals("true")); //˵��trueҲ��ת�������ַ���
		
		List list=new ArrayList();
		list.add("���");
		list.add("���ǹ�����");
		String jsonStr=JSON.toJSONString(list);
		System.out.println(jsonStr);
		
		List list2=new ArrayList();
		list2.add("���");
		list2.add("���ǹ�����");
		String jsonString=JSON.toJSONString(list2);
		System.out.println(JSON.toJSONString(jsonString));*/
		
		//���1
//		String text="��a��a��a��a��";
//		String index="a";
//		String arr1[]=text.split(index);
//		String arr2[]=OtherUtil.splitRewrite(text, index);
//		System.out.println("�Դ������ָ�ȣ�"+arr1.length);
//		System.out.println("�ҵķ����ָ�ȣ�"+arr2.length);	
		
		//���2
//		String text="aaaa";
//		String index="a";
//		String arr1[]=text.split(index);
//		String arr2[]=OtherUtil.splitRewrite(text, index);
//		System.out.println("�Դ������ָ�ȣ�"+arr1.length);
//		System.out.println("�ҵķ����ָ�ȣ�"+arr2.length);
		
//		//���3
//		String text="��a��a��a��a";
//		String index="a";
//		String arr1[]=text.split(index);
//		String arr2[]=OtherUtil.splitRewrite(text, index);
//		System.out.println("�Դ������ָ�ȣ�"+arr1.length);
//		System.out.println("�ҵķ����ָ�ȣ�"+arr2.length);
//		
//		int arr[]=new int[1];
//		System.out.println(arr.length);
		
		System.out.println(new java.sql.Date(946656000000l));
		System.out.println(new Date(946656000000l));
		
		
	}
	
	
	public static int temp() {
		try{
			System.out.println("����try��");
			return 2;
		}catch(Exception e) {
		
		}finally {
			System.out.println("�����ˣ�");
		}
		return 1;
	}
}