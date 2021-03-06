<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.neusoft.service.TopicServiceImp"%>
<%@page import="com.neusoft.service.ITopicService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
  <script type="text/javascript">
        window.onload=function(){
    	
        	//判断浏览器是否禁用了cookie
         	if(navigator.cookieEnabled==false){
         		window.location.href="${pageContext.request.contextPath}/jsp/tips.jsp?tips=请开启cookie后再访问本站吧！";
        		return;
         	}
         	
         	
        	<%
	    		ITopicService its=new TopicServiceImp();
	    		request.setAttribute("weekAnswerList", JSON.toJSONString(its.getWeekAnswerList()));
	    		request.setAttribute("hotViewList", JSON.toJSONString(its.getHotViewList()));
	    		request.setAttribute("hotReplyList", JSON.toJSONString(its.getHotReplyList()));
	    		request.setAttribute("kissNumList", JSON.toJSONString(its.getKissNumList()));
        	%>
        	
        	
        	//1：将weekAnswerList存入cookie
        	var str1='${requestScope.weekAnswerList}'; //此时的str1为字符串
        	document.cookie='weekAnswerList='+str1+';Path=/';
			
			//2：将hotViewList存入cookie
			var str2='${requestScope.kissNumList}'; //此时的str2为字符串
        	document.cookie='kissNumList='+str2+';Path=/';
			
			//3：将hotViewList存入cookie
			var str3='${requestScope.hotViewList}'; //此时的str3为字符串
        	document.cookie='hotViewList='+str3+';Path=/';
			
			//4：将hotReplyList存入cookie
        	var str4='${requestScope.hotReplyList}'; //此时的str4为字符串
        	document.cookie='hotReplyList='+str4+';Path=/';
        	
        	var check='${param.check}';
        	if(check==0){
        		window.location.href="${pageContext.request.contextPath}/topicDetail.do?topicid="+'${param.topicid}';
        	}else if(check==1){
        		window.location.href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=all";
        	}
        }
  </script>
</head>
<body>
</body>
</html>