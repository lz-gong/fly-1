<%@page import="com.neusoft.bean.Userinfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

    <link href="${pageContext.request.contextPath}/res/css/mricode.pagination.css" type="text/css" charset="utf-8">
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/mricode.pagination.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/template-web.js"></script>
<script type="text/javascript">
	window.onload=function(){
		
		var oBtn=document.getElementById('button');
		document.cookie='jsCookieKey='+'jsCookieValue';
		oBtn.onclick=function(){
			document.cookie='name='+'gong';
			var name=document.cookie.split(";")[0].split("=")[1];
			alert(name);
		}
		
        //获取js中的cookie的方法（这个不是自己写的哦！）
		function get_cookie(Name) {
			var search = Name + "="//查询检索的值
			var returnvalue = "";//返回值
			if (document.cookie.length > 0) {
			sd = document.cookie.indexOf(search);
				if (sd!= -1) {
				sd += search.length;
				end = document.cookie.indexOf(";", sd);
				if (end == -1)
				end = document.cookie.length;
		    	//unescape() 函数可对通过 escape() 编码的字符串进行解码。
				returnvalue=unescape(document.cookie.substring(sd, end))
				}
			} 
		 return returnvalue;	   
		}
        
        alert(get_cookie('javaCookie'));
	}
</script>
</head>
<body>
		<%
		/* Cookie cookie=new Cookie("张三","李四");
		response.addCookie(cookie); */
		Cookie cookie=new Cookie("javaCookie","javaCookie");
		response.addCookie(cookie);
		Cookie cookies[]=request.getCookies();
		for(Cookie c:cookies){
			out.print(c.getName());
			out.print(c.getValue());
		}
	    %>
	<input id="button" type="button" value="点击添加一个cookie">
</body>
</html>