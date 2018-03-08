<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	window.onload=function(){
		//1：创建ajax对象
		var xhr=new XMLHttpRequest();
		//2:连接服务器
		xhr.open("get","${pageContext.request.contextPath}/ajaxDemo.do",true);
		//3:发送请求
		xhr.send();  //注：如果上面的是post请求，send()中就需要写参数了
		//4:接收响应
		xhr.onreadystatechange=function(){
			if(xhr.readyState===4){
				if(xhr.status===200){
					//响应成功做什么
					alert(xhr.responseText); //注：responseText为响应字段
				}else{
					//响应失败做什么
				}
			}
		}
	}
</script>
</head>
<body>

</body>
</html>