<%@page import="java.util.Date"%>
<%@page import="com.neusoft.utils.TimeToStringUtil"%>
<%@page import="com.neusoft.bean.Userinfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 这个判断就没什么必要了 -->
<%-- <c:if test="${requestScope.topTopicList==null }">  
			<c:redirect url="/goIndex.do"></c:redirect>
</c:if> --%>

<!-- <c:if test="${empty requestScope.topicList }">
   					 </c:if>
   				 -->
   				<!-- 重点：如果是上面这样写的话判断的是list的长度，
   						       实际上只需判断list是否为null即可。
   						      如果用empty 当帖子长度为0时，就会进入死循环。
   				 -->
   				
		      	<c:if test="${requestScope.topicList==null }">  
		      		<c:redirect url="/goIndex/all"></c:redirect>
		      	</c:if> 
		      	
 
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>基于 layui 的极简社区页面模版</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="keywords" content="fly,layui,前端社区">
  <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
  <link rel="stylesheet" href="${pageContext.request.contextPath }/res/layui/css/layui.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath }/res/css/global.css">
  
  <!-- 为编辑和删除帖子设置效果 -->
  <style type="text/css">
        .top_good_form{
            display: none;
        }
        .delete_topic,.editor_topic{
            color: black;
            cursor:pointer;
        }
        .delete_topic:hover,.editor_topic:hover{
            color: #3bc3e0;
            transition: 0.2s;
        }
        .fly-list-li{
        	position: relative;
        }
                #sign_d1{
            width: 100px;
            height: 38px;
            background-color: #f97044;
            color: white;
            line-height: 38px;
            text-align: center;
            border-radius: 5px;
            display: inline-block;
        }
        
        /* 
        	签到设置样式
         */
        #sign_d1:hover{
        	border-radius: 0;
            cursor: pointer;
            color: snow;
            background-color: #ff5722;
            transition: 0.8s;
        }
        #sign_d2{
            width: 100px;
            height: 38px;
            background-color: #fbf8f8;
            border: 1px #e6e6e6 solid;
            color: darkgray;
            line-height: 38px;
            text-align: center;
            border-radius: 4px;
            display: none;
        }
        #sign_d2:hover{
            cursor: not-allowed;
        }
   </style>
    
    
  <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/template-web.js"></script>
    
  <script type="text/javascript">
        window.onload=function(){
        	//将json字符串渲染到指定模板中
        	//1：本周top回答渲染 与 将weekAnswerList存入cookie
        	var str1='${requestScope.weekAnswerList}'; //此时的str1为字符串
        	document.cookie='weekAnswerList='+str1+';Path=/';
        	var json1=JSON.parse(str1);
			var html1=template('wal',json1);
			document.getElementById('weekAnswerTop').innerHTML=html1;
			
			//2：飞吻数前8榜渲染 与 将hotViewList存入cookie
			var str2='${requestScope.kissNumList}'; //此时的str2为字符串
        	document.cookie='kissNumList='+str2+';Path=/';
			var json2=JSON.parse(str2);
			var html2=template('knl',json2);
			document.getElementById('kissNumList').innerHTML=html2;
			
			//3：10天内热视渲染 与 将hotViewList存入cookie
			var str3='${requestScope.hotViewList}'; //此时的str3为字符串
        	document.cookie='hotViewList='+str3+';Path=/';
			var json3=JSON.parse(str3);
			var html3=template('hvl',json3);
			document.getElementById('hotViewList').innerHTML+=html3;
			
			//4：10天内热答渲染 与 将hotReplyList存入cookie
        	var str4='${requestScope.hotReplyList}'; //此时的str4为字符串
        	document.cookie='hotReplyList='+str4+';Path=/';
			var json4=JSON.parse(str4);
			var html4=template('hrl',json4);
			document.getElementById('hotReplyList').innerHTML+=html4;
			
        	
        	//为编辑帖子设置点击事件
			var AEditor=document.getElementsByClassName('editor_topic');
            var ATopGoodForm=document.getElementsByClassName('top_good_form');

            for(var i=0;i<AEditor.length;i++){
                AEditor[i].showForm=i;
                AEditor[i].OnOff=false;
                AEditor[i].onclick=function () {
                    if(this.OnOff===false){
                        ATopGoodForm[this.showForm].style.display='inline-block';
                    }else{
                        ATopGoodForm[this.showForm].style.display='none';
                    }
                    this.OnOff=!this.OnOff;
                }
            }
            
            
        	var OMoreTopic=document.getElementById('moreTopic');
			if(OMoreTopic!=null){
	            OMoreTopic.onclick=function () {
	                document.cookie='pageNum=1;Path=/';  //功能为每次点击 更多求解 都从第2页开始，有时不是很人性化，就取消了,然后又注回来了^_^
	            }
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
          
/*  			 function get_cookie(name) {
 				   var cookies = document.cookie.split(";");
 				   for(var i=0;i<cookies.length;i++) {
 				    var cookie = cookies[i];
 				    var cookieStr = cookie.split("=");
 				    if(cookieStr && cookieStr[0].trim()==name) {
 				     return  decodeURI(cookieStr[1]);
 				    }
 				   }
 				 } */
         
          
          //确认删除点击事件
          var ADelete=document.getElementsByClassName('delete_topic');
          for(var i=0;i<ADelete.length;i++){
        	  ADelete[i].onclick=function(){
        		  return confirm('确认删除吗？');
        	  } 
          }
          
    	  //签到动作
          var sign_d1=document.getElementById('sign_d1');
          var sign_d2=document.getElementById('sign_d2');
          var sign_span1=document.getElementById('sign_span1');
          var sign_span2=document.getElementById('sign_span2');
          var is_sign=document.getElementById('is_sign');
          if(is_sign.value=='false'){ //等于false说明还没有签到
	          	sign_d1.style.display='inline-block';
	        	sign_span1.style.display='inline-block';
	        	sign_d2.style.display='none';
	        	sign_span2.style.display='none';

              sign_d1.onclick=function () {
            	  
            	  //判断用户是否已经登录！
            	  var userid=document.getElementById('userid');
            	  if(userid.value==''){
            		  window.location.href="${pageContext.request.contextPath}/jsp/login.jsp";
            		  return;
            	  }
            	  
                    //创建ajax对象
                    var xhr=new XMLHttpRequest();
                    //连接服务器
                    xhr.open("get","${pageContext.request.contextPath}/signServlet.do",true);
                    //发送
                    xhr.send();
                    //响应
                    xhr.onreadystatechange=function () {
                        if(xhr.readyState===4){
                            if(xhr.status===200){
                            	var temp=xhr.responseText;
                                //无论是true还是false应该都是今天已经签到成功了
                            	sign_d1.style.display='none';
                            	sign_span1.style.display='none';
                            	sign_d2.style.display='inline-block';
                            	sign_span2.style.display='inline-block';
                            }
                        }
                    }
                }
          }else{
          	sign_d1.style.display='none';
        	sign_span1.style.display='none';
        	sign_d2.style.display='inline-block';
        	sign_span2.style.display='inline-block';
          }
          
          var weekAnswerTop=document.getElementById('weekAnswerTop');
          var kissNumList=document.getElementById('kissNumList');
          var hotViewList=document.getElementById('hotViewList');
          var hotReplyList=document.getElementById('hotReplyList');
          var top_none=document.getElementsByClassName('top_none');
          if(weekAnswerTop.innerHTML.trim()==''){
        	  top_none[0].style.display='block';
          }
          if(kissNumList.innerHTML.trim()==''){
        	  top_none[1].style.display='block';
          }
          var temp_hvl=hotViewList.getElementsByTagName('dd');
		  if(temp_hvl.length==0){
			  top_none[2].style.display='block';
          }
		  var temp_htl=hotReplyList.getElementsByTagName('dd');
	      if(temp_htl.length==0){
	    	  top_none[3].style.display='block';
          }
          
          
        }
  </script>
  
</head>
<body>

<!-- 将用户的签到信息放置在隐藏域中，用js来判断是否需要显示签到 -->
<%
	Userinfo user=(Userinfo)session.getAttribute("userInfo");
	String check="false";
	if(user!=null){
		boolean temp=TimeToStringUtil.checkStringTimeIsEqulas(user.getSignTime(), new Date());
		if(temp==true){
			check="true";
		}else{
			check="false";
		}
	}
%>

<input type="hidden" id="is_sign" value="<%=check%>"/>
<input type="hidden" id="userid" value="${sessionScope.userInfo.id}"/>


<!-- 以下为各种排行榜 -->
<script id="wal" type="text/html">
	  {{each $data as topicInfoEx index}}
        <dd>
          <a href="${pageContext.request.contextPath}/home/{{topicInfoEx.userId }}">
            <img src="{{topicInfoEx.headUrl }}">
            <cite>{{topicInfoEx.nickName }}</cite>
            <i>{{topicInfoEx.answerNum }}次回答</i>
          </a>
        </dd>
	  {{/each}}
</script>

<script id="knl" type="text/html">
	  {{each $data as topicInfoEx index}}
        <dd>
          <a href="${pageContext.request.contextPath}/home/{{topicInfoEx.userId }}">
            <img src="{{topicInfoEx.headUrl }}">
            <cite>{{topicInfoEx.nickName }}</cite>
            <i>{{topicInfoEx.kissNum }}飞吻</i>
          </a>
        </dd>
	  {{/each}}
</script>

<script id="hvl" type="text/html">
	  {{each $data as topicInfoEx index}}
      	<dd>
        	<a href="${pageContext.request.contextPath}/topicDetail.do?topicid={{topicInfoEx.id}}">{{topicInfoEx.title}}</a>
        	<span><i class="iconfont">&#xe60b;</i> {{topicInfoEx.viewCount}}</span>
      	</dd>
	  {{/each}}
</script>

<script id="hrl" type="text/html">
	  {{each $data as topicInfoEx index}}
      	<dd>
        	<a href="${pageContext.request.contextPath}/topicDetail.do?topicid={{topicInfoEx.id}}">{{topicInfoEx.title}}</a>
        	<span><i class="iconfont">&#xe60c;</i> {{topicInfoEx.answerNum}}</span>
      	</dd>
	  {{/each}}
</script>
	

<div class="header">
  <div class="main">
    <a class="logo" href="${pageContext.request.contextPath}/goIndex/all" title="Fly">Fly社区</a>
    <div class="nav">
      <a class="nav-this" href="${pageContext.request.contextPath}/goIndex/all">
        <i class="iconfont icon-wenda"></i>问答
      </a>
      <a href="http://www.layui.com/" target="_blank">
        <i class="iconfont icon-ui"></i>框架
      </a>
    </div>
    <div class="nav-user">
    
      <c:if test="${empty sessionScope.userInfo }">
      	<!-- 未登入状态 -->
     	 <a class="unlogin" href="${pageContext.request.contextPath }/jsp/login.jsp"><i class="iconfont icon-touxiang"></i></a>
      	<span><a href="${pageContext.request.contextPath }/jsp/login.jsp">登入</a><a href="${pageContext.request.contextPath }/jsp/reg.jsp">注册</a></span>
      	<p class="out-login">
        <!-- <a href="" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-qq" title="QQ登入"></a>
        <a href="" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-weibo" title="微博登入"></a> -->
        <a href="" onclick="layer.msg('暂不支持！', {icon:16, shade: 0.1, time:0})" class="iconfont icon-qq" title="QQ登入"></a>
        <a href="" onclick="layer.msg('暂不支持！', {icon:16, shade: 0.1, time:0})" class="iconfont icon-weibo" title="微博登入"></a>
      	</p>   
      </c:if>
      
      <!-- 登入后的状态 -->
      <c:if test="${!empty sessionScope.userInfo }">
      	<a class="avatar" href="${pageContext.request.contextPath }/jsp/user/center.jsp">
        <img src="${sessionScope.userInfo.headUrl }">
        <cite>${sessionScope.userInfo.nickname }</cite>
      	</a>
      	<a style="display:inline-block; position:relative; top:-1px" class="avatar" href="${pageContext.request.contextPath }/jsp/grade_list.jsp"><i>${sessionScope.userInfo.grade }</i> </a>
     	<div class="nav">
        <a href="${pageContext.request.contextPath }/jsp/user/set.jsp"><i class="iconfont icon-shezhi"></i>设置</a>
        <a href="${pageContext.request.contextPath }/logout.do"><i class="iconfont icon-tuichu" style="top: 0; font-size: 22px;"></i>退了</a>
      </div>
      </c:if>
      
    </div>
  </div>
</div>


<div class="main layui-clear">
  <div class="wrap">
    <div class="content">
      <div class="fly-tab fly-tab-index">
        <span>
          <a href="${pageContext.request.contextPath }/goIndex/all">全部</a>
          <a href="${pageContext.request.contextPath }/goIndex/naccept">未采纳</a>
          <a href="${pageContext.request.contextPath }/goIndex/accept">已采纳</a>
          <a href="${pageContext.request.contextPath }/goIndex/nice">精帖</a>
          <a href="${pageContext.request.contextPath }/goIndex/mytopic">我的帖</a>
        </span>
        <form action="${pageContext.request.contextPath }/goIndex/search" class="fly-search" method="get">
          <i class="iconfont icon-sousuo"></i>
          <input class="layui-input" autocomplete="off" placeholder="搜索内容，回车跳转" type="text" name="searchStr">
        </form>
        <a href="${pageContext.request.contextPath }/loadOption.do" class="layui-btn jie-add">发布问题</a>
      </div>
      
      
      <!-- 顶置帖子 -->
      <c:if test="${requestScope.topTopicList!=null && !empty requestScope.topTopicList}">
      	<ul class="fly-list fly-list-top">
        <c:forEach items="${requestScope.topTopicList}" var="topTopicList"> <!-- var -->
			          <li class="fly-list-li">
			          <!-- <a href="user/home.html" class="fly-list-avatar"> -->
			          <a href="${pageContext.request.contextPath}/home/${topTopicList.userId}" class="fly-list-avatar">
			            <img src="${topTopicList.headUrl }" alt="">
			          </a>
			          <h2 class="fly-tip">
			            <a href="${pageContext.request.contextPath }/topicDetail.do?topicid=${topTopicList.id}">${topTopicList.title}</a>
			            <span class="fly-tip-stick">置顶</span>

            			<c:if test="${topTopicList.isGood==1}"><span class="fly-tip-jing">精帖</span></c:if>
			          </h2>
			          <p>
			            <span><a href="user/home.html">${topTopicList.nickName}</a></span>
			            <span>${topTopicList.createTime}</span>
			            <span>${topTopicList.classname}</span>
			            <!-- 编辑和删除帖子 -->
			            <c:if test="${sessionScope.userInfo.isManager==1}">
				            <a class='delete_topic' href="${pageContext.request.contextPath }/deleteTopicServlet.do?topicid=${topTopicList.id}" >删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
						    <span class='editor_topic' >编辑</span>
			            </c:if>

			            <span class="fly-list-hint"> 
			              <i class="iconfont" title="回答">&#xe60c;</i> ${topTopicList.commentCount}
			              <i class="iconfont" title="人气">&#xe60b;</i> ${topTopicList.viewCount}
			            </span>
			          </p>
			          <form class="top_good_form" action="${pageContext.request.contextPath}/editTopGoodServlet.do" method="get" style="position: absolute;left: 385px;top: 33px;">
					        ( 加精 <input type="checkbox" value="good" class="top_good" name="topAndGood">
					        顶置 <input type="checkbox" value="top" class="top_good" name="topAndGood">
					        <input type="hidden" name="topicid" value="${topTopicList.id}"/>
					        <input type="submit" value="确定"/> )
					  </form>
			        </li>
		</c:forEach>	
		
		<%-- <li class="fly-list-li">
          <a href="user/home.html" class="fly-list-avatar">
            <img src="http://tp4.sinaimg.cn/1345566427/180/5730976522/0" alt="">
          </a>
          <h2 class="fly-tip">
            <a href="jie/detail.html">基于 layui的轻量级问答社区页面模版 V2版本</a>
            <span class="fly-tip-stick">置顶</span>
            <span class="fly-tip-jing">精帖</span>
          </h2>
          <p>
            <span><a href="user/home.html">贤心</a></span>
            <span>刚刚</span>
            <span>layui框架综合</span>
            <span class="fly-list-hint"> 
              <i class="iconfont" title="回答">&#xe60c;</i> 317
              <i class="iconfont" title="人气">&#xe60b;</i> 6830
            </span>
          </p>
        </li>
        <li class="fly-list-li">
          <a href="user/home.html" class="fly-list-avatar">
            <img src="${pageContext.request.contextPath }/res/images/avatar/00.jpg" alt="">
          </a>
          <h2 class="fly-tip">
            <a href="jie/detail.html">基于 layui的轻量级问答社区页面模版 V2版本</a>
            <span class="fly-tip-stick">置顶</span>
          </h2>
          <p>
            <span><a href="user/home.html">纸飞机</a></span>
            <span>30分钟前</span>
            <span>技术闲谈</span>
            <span class="fly-list-hint"> 
              <i class="iconfont" title="回答">&#xe60c;</i> 502
              <i class="iconfont" title="人气">&#xe60b;</i> 81689
            </span>
          </p>
        </li> --%>
      </ul>
      </c:if>
      
      
      <!-- 新帖 新帖 新帖 新帖 新帖 新帖 新帖 新帖 新帖 新帖 新帖 -->
      <c:if test="${!empty requestScope.topicList}">
      		      <ul class="fly-list">
   				
		        <c:forEach items="${requestScope.topicList}" var="topicInfoEx">
			          <li class="fly-list-li">
			          <!-- <a href="user/home.html" class="fly-list-avatar"> -->
			          <a href="${pageContext.request.contextPath}/home/${topicInfoEx.userId}" class="fly-list-avatar">
			            <img src="${topicInfoEx.headUrl }" alt="">
			          </a>
			          <h2 class="fly-tip">
			            <a href="${pageContext.request.contextPath }/topicDetail.do?topicid=${topicInfoEx.id}">${topicInfoEx.title}</a>
			            <c:if test="${topicInfoEx.isGood=='1'}"><span class="fly-tip-jing">精帖</span></c:if>
			          </h2>
			          <p >
			            <span><a href="user/home.html">${topicInfoEx.nickName}</a></span>
			            <span>${topicInfoEx.createTime}</span>
			            <span>${topicInfoEx.classname}</span>
			            <!-- 编辑和删除帖子 -->
			            <c:if test="${sessionScope.userInfo.isManager==1}">
			            	<a class='delete_topic' href="${pageContext.request.contextPath }/deleteTopicServlet.do?topicid=${topicInfoEx.id}">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
					    	<span class='editor_topic' >编辑</span>
			            </c:if>
					   
			            <span class="fly-list-hint"> 
			              <i class="iconfont" title="回答">&#xe60c;</i> ${topicInfoEx.commentCount}
			              <i class="iconfont" title="人气">&#xe60b;</i> ${topicInfoEx.viewCount}
			            </span>
			          </p>
			          
			          <form class="top_good_form" action="${pageContext.request.contextPath}/editTopGoodServlet.do" method="get" style="position: absolute;left: 385px;top: 33px;">
					        ( 加精 <input type="checkbox" value="good" class="top_good" name="topAndGood">
					        顶置 <input type="checkbox" value="top" class="top_good" name="topAndGood">
					        <input type="hidden" name="topicid" value="${topicInfoEx.id}"/>
					        <input type="submit" value="确定"/> )
					  </form>
					  
			        </li>
				</c:forEach>	
      </ul>
      </c:if>
      <c:if test="${empty requestScope.topicList}">
      	<br/>
      	<h1 style="text-align:center;font-size:16px;">呀 ! 没有找到你想要的帖子</h1>
      </c:if>
      

      <c:if test="${!empty requestScope.topicList}">
		<div style="text-align: center">

        	<%
        		String jumpTarget=(String)request.getAttribute("jumpTarget");
        		if(jumpTarget.equals("all")){
			%>
				<div class="laypage-main">
          			<a id="moreTopic" href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=all" class="laypage-next">更多求解</a>
        		</div>
			<%
        		}else if(jumpTarget.equals("naccept")){
        	%>
        		<div class="laypage-main">
          			<a id="moreTopic" href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=naccept" class="laypage-next">更多求解</a>
        		</div>		
        	<%
        		}else if(jumpTarget.equals("accept")){
        	%>		
        	    <div class="laypage-main">
          			<a id="moreTopic" href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=accept" class="laypage-next">更多求解</a>
        		</div>
        	<%
        		}else if(jumpTarget.equals("nice")){
        	%>	
        		<div class="laypage-main">
          			<a id="moreTopic" href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=nice" class="laypage-next">更多求解</a>
        		</div>
        	<%
        		}else if(jumpTarget.equals("mytopic")){
        	%>
        		<div class="laypage-main">
          			<a id="moreTopic" href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=mytopic" class="laypage-next">更多求解</a>
        		</div>
        	<%		
        		}else if(jumpTarget.equals("search")){
        	%>
        		<div class="laypage-main">
          			<a id="moreTopic" href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=search&searchStr=${requestScope.searchStr}" class="laypage-next">更多求解</a>
        		</div>
        	<%
        		}
        	%>
      	</div>
      </c:if>
      
    </div>
  </div>
  
  <div class="edge">
  	<div style="position:relative;height:113px;" class="fly-panel leifeng-rank"> 
      <!-- <h3 class="fly-panel-title">近一月回答榜 - TOP 12</h3> -->
      <h3 class="fly-panel-title">每日签到</h3>
      	<div style="display:none; position:absolute;left:50px;top:66px;" id="sign_d1">今日签到</div>
      	<span id="sign_span1" style="display:none; position:absolute;left:164px;top:72px;">今日还没有签到哦！</span>
        <div style="position:absolute;left:50px;top:66px;" id="sign_d2">今日已签到</div>
        <span id="sign_span2" style="display:none; position:absolute;left:164px;top:72px;;">获得了<span style="color:#ff5722;"> 5 </span>飞吻</span>
    </div>

    <div class="fly-panel leifeng-rank"> 
      <!-- <h3 class="fly-panel-title">近一月回答榜 - TOP 12</h3> -->
      <h3 class="fly-panel-title">本周回答榜  - TOP 8</h3>
      <p class="top_none" style="display:none; color:#a9b7b7; text-align:center; position:relative; top:5px" >暂无</p>
      <dl id="weekAnswerTop">
      
      </dl>
    </div>
    
    <div class="fly-panel leifeng-rank"> 
      <!-- <h3 class="fly-panel-title">近一月回答榜 - TOP 12</h3> -->
      <h3 class="fly-panel-title">飞吻排行榜  - TOP 4</h3>
      <dl id="kissNumList">
      
	  
      </dl>
      <p class="top_none" style="display:none; color:#a9b7b7; text-align:center; position:relative; top:5px" >暂无</p>
    </div>
    
    <dl id="hotViewList" class="fly-panel fly-list-one"> 
      <dt  class="fly-panel-title">近期热帖</dt>
	  <p class="top_none" style="display:none; color:#a9b7b7; text-align:center; position:relative; top:5px" >暂无</p>
    </dl>
    
    <div > 
    <dl id="hotReplyList" class="fly-panel fly-list-one"> 
      <dt class="fly-panel-title">近期热议</dt>
      <p class="top_none" style="display:none; color:#a9b7b7; text-align:center; position:relative; top:5px" >暂无</p>
    </dl>
    </div>
    
    <div class="fly-panel fly-link"> 
      <h3 class="fly-panel-title">友情链接</h3>
      <dl>
        <dd>
          <a href="http://www.layui.com/" target="_blank">layui</a>
        </dd>
        <dd>
          <a href="http://layim.layui.com/" target="_blank">LayIM</a>
        </dd>
        <dd>
          <a href="http://layer.layui.com/" target="_blank">layer</a>
        </dd>
      </dl>
    </div>


  </div>
</div>

<div class="footer">
  <p><a href="http://fly.layui.com/">Fly社区</a> 2017 &copy; <a href="http://www.layui.com/">layui.com</a></p>
  <p>
    <a href="http://fly.layui.com/auth/get" target="_blank">产品授权</a>
    <a href="http://fly.layui.com/jie/8157.html" target="_blank">获取Fly社区模版</a>
    <a href="http://fly.layui.com/jie/2461.html" target="_blank">微信公众号</a>
  </p>
</div>
<script src="${pageContext.request.contextPath }/res/layui/layui.js"></script>
<script>
layui.cache.page = '';
layui.cache.user = {
  username: '游客'
  ,uid: -1
  ,avatar: '${pageContext.request.contextPath }/res/images/avatar/00.jpg'  //老师说单引号的就不用改了
  ,experience: 83
  ,sex: '男'
};
layui.config({
  version: "2.0.0"
  ,base: '${pageContext.request.contextPath }/res/mods/'
}).extend({
  fly: 'index'
}).use('fly');
</script>

</body>
</html>