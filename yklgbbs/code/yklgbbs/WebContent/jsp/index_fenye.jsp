<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 
 
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
  
  <!-- 首先需要引入css样式表 与 js -->
    <link href="${pageContext.request.contextPath}/res/css/mricode.pagination.css" rel="stylesheet" type="text/css" charset="utf-8">
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/mricode.pagination.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/template-web.js"></script>
	
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
   </style>
	
	<!-- 下面为分页部分 -->
	<!-- 注：下面完成的是Ajax刷新 -->
	<script type="text/javascript">
		window.onload=function(){
			
			//判断是否有top榜的cookie，如果没有则添加
	       	if(get_cookie('weekAnswerList')==''){
	       		window.location.href="${pageContext.request.contextPath}/jsp/add_cookie.jsp?check=1";
	       	}
			
			/* 
				注：只有进入到index才会获取到下面的这些cookie，否则在本页面和detail页面不能正常显示
			*/
			
        	//将json字符串渲染到指定模板中
        	//1：本周top回答渲染 与 将weekAnswerList存入cookie
			//var str1='${requestScope.weekAnswerList}'; //此时的str1为字符串
        	//document.cookie='weekAnswerList='+str1+';Path=/';
        	var str1=get_cookie('weekAnswerList');
			var json1=JSON.parse(str1);
			var html1=template('wal',json1);
			document.getElementById('weekAnswerTop').innerHTML=html1;
			
			//2：飞吻数前8榜渲染 与 将hotViewList存入cookie
			//var str2='${requestScope.kissNumList}'; //此时的str1为字符串
        	//document.cookie='kissNumList='+str2+';Path=/';
        	var str2=get_cookie('kissNumList');
			var json2=JSON.parse(str2);
			var html2=template('knl',json2);
			document.getElementById('kissNumList').innerHTML=html2;
			
			//3：10天内热视渲染 与 将hotViewList存入cookie
			//var str3='${requestScope.hotViewList}'; //此时的str1为字符串
        	//document.cookie='hotViewList='+str3+';Path=/';
        	var str3=get_cookie('hotViewList');
			var json3=JSON.parse(str3);
			var html3=template('hvl',json3);
			document.getElementById('hotViewList').innerHTML+=html3;
			
			//4：10天内热答渲染 与 将hotReplyList存入cookie
        	//var str4='${requestScope.hotReplyList}'; //此时的str1为字符串
        	//document.cookie='hotReplyList='+str4+';Path=/';
        	var str4=get_cookie('hotReplyList');
			var json4=JSON.parse(str4);
			var html4=template('hrl',json4);
			document.getElementById('hotReplyList').innerHTML+=html4;
			
			
			
			
			
			if(get_cookie('jumpToAll')=='yes'){
				window.location.href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=all";
				document.cookie='jumpToAll=no;Path=/';
				return;
			}
			
			//分析判断用户访问index_fenye.jsp的方式
			var pageNum;
			if(get_cookie('pageNum')!=''){ //用户通过index.jsp页面进入index_fenye.jsp，或点击了页码后直接访问的index_fenye.jsp
				pageNum=get_cookie('pageNum');
			}else{ //用户直接进入index_fenye.jsp
				var pageNum=0;  //如果用户直接访问的index_fenye.jsp,就从第1页开始
			}
			
            
			//这里就是ajax生成局部页面的内容
			$("#page_fenye").pagination({ 
				pageIndex:pageNum, //从pageIndex+1页开始显示（重要）
				pageSize:10, //分页后每页显示10条数据
				pageBtnCount:9, //按钮个数，包括上下页，首尾页
				showFirstLastBtn:true, //是否显示首页，尾页按钮
				firstBtnText:"首页", //这里可以设置个性显示内容
				lastBtnText:"尾页",
				prevBtnText:"上一页",
				nextBtnText:"下一页",
				remote:{
					//每次点击按钮提交到处理请求的Servlet
					url:'${pageContext.request.contextPath}/fenyeServlet.do?target=${param.target}&searchStr=${param.searchStr}', 
					success:function(data){ //注：data为：json字符串通过转换为了json对象
						if(data.check=='notLogin'){
							window.location.href="${pageContext.request.contextPath}/jsp/login.jsp";
						}else if(data.check=='jumpAll'){
							window.location.href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=all";
						}else if(data.check=='404'){
							window.location.href="${pageContext.request.contextPath}/jsp/404.jsp";
						}else if(data.check=='noSearchStr'){
							window.location.href="${pageContext.request.contextPath}/jsp/tips.jsp?tips=输入点内容再搜索吧！";
						}else if(data.total==0){
							var content_fenye=document.getElementById('content_fenye');
							content_fenye.style.display='none';
							var noTopic=document.getElementById('noTopic');
							noTopic.style.display='block';
						}else{
							var html=template('test_fenye',data); //date渲染到test模板中
							document.getElementById('content_fenye').innerHTML=html;
							upEditorButton(); //重要：为每个帖子的编辑设置点击事件
							confirmDelete(); //为每个删除设置确认删除
						}	
					}
				}
			});
			
			//设置页码（a标签）的点击事件
            var OPageFenye=document.getElementById('page_fenye'); 
			OPageFenye.onclick=function () { //这里应用了冒泡事件，直接点击的其实不是OPageFenye，而是其中的a标签
		    var pageIndex=$("#page_fenye").pagination('getPageIndex'); //获取当前码，前用户查看帖子详情后，
		            														   //点击浏览器的返回，返回的依然是原来的index
		    document.cookie='pageNum='+pageIndex+';Path=/';
		    //注：下面的可能是兼容写法
		    document.body.scrollTop = document.documentElement.scrollTop = 0; //js实现点击返回页面顶部
		    }
			        
			//获取js中的cookie的方法
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
			
			function upEditorButton() {
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
            }
			
			//确认删除点击事件
	        function confirmDelete(){
	        	  var ADelete=document.getElementsByClassName('delete_topic');
		          for(var i=0;i<ADelete.length;i++){
		        	  ADelete[i].onclick=function(){
		        		  return confirm('确认删除吗？');
		        	  } 
		          }
			}
			
			//每次点击分页jsp中的跳转套其他类型的帖子，就要重新设置一下页码
			var AA=document.getElementsByClassName('jump_to_target');
			for(var i=0;i<AA.length;i++){
				AA[i].onclick=function(){
					document.cookie='pageNum=0;Path=/';
				}
			}
			
			//这个是搜索点击动作
			var OForm=document.getElementsByClassName('fly-search')[0];
			var OLayuiInput=document.getElementsByClassName('layui-input')[0];
			OForm.onclick=function(){
				if(OLayuiInput.value!=''){
					document.cookie='pageNum=0;Path=/';
				}
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
        <a href="" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-qq" title="QQ登入"></a>
        <a href="" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-weibo" title="微博登入"></a>
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
          <a class="jump_to_target" href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=all">全部</a>
          <a class="jump_to_target" href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=naccept">未采纳</a>
          <a class="jump_to_target" href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=accept">已采纳</a>
          <a class="jump_to_target" href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=nice">精帖</a>
          <a class="jump_to_target" href="${pageContext.request.contextPath}/jsp/index_fenye.jsp?target=mytopic">我的帖</a>
        </span>
        <form action="${pageContext.request.contextPath }/jsp/index_fenye.jsp?target=search" class="fly-search" method="post">
          <i class="iconfont icon-sousuo"></i>
          <input class="layui-input" autocomplete="off" placeholder="搜索内容，回车跳转" type="text" name="searchStr">
        </form>
        <a href="${pageContext.request.contextPath }/loadOption.do" class="layui-btn jie-add">发布问题</a>
      </div>
      
      
      <!-- 顶置帖子 -->
      <%-- <ul class="fly-list fly-list-top">
      	<c:if test="${requestScope.topTopicList==null }">  
			<c:redirect url="/fenyeStaticServlet.do"></c:redirect>
		</c:if>
        
        <c:forEach items="${requestScope.topTopicList}" var="topTopicList">
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
			            <span class="fly-list-hint"> 
			              <i class="iconfont" title="回答">&#xe60c;</i> ${topTopicList.commentCount}
			              <i class="iconfont" title="人气">&#xe60b;</i> ${topTopicList.viewCount}
			            </span>
			          </p>
			        </li>
		</c:forEach>	
      </ul> --%>
      
      <!-- 新帖 新帖 新帖 新帖 新帖 新帖 新帖 新帖 新帖 新帖 新帖 -->
      <!-- 数据内容显示块 -->
      <ul class="fly-list" id="content_fenye">
              
      </ul>
      
      <div id="noTopic" style="display:none;">
      	<br/>
      	<h1 style="text-align:center;font-size:16px;">呀 ! 没有找到你想要的帖子</h1>
      </div>
      
      <!-- 分页页码  下面的class去掉貌似也没什么问题-->
	  <!-- <div id="page_fenye" class="m-pagination"></div> -->
	  <div id="page_fenye" class="m-pagination" ></div>
      
		<script id="test_fenye" type="text/html"> 
		{{ each list as topicInfoEx i}}

					  <li class="fly-list-li">
			          <!-- <a href="user/home.html" class="fly-list-avatar"> -->
			          <a href="${pageContext.request.contextPath}/home/{{topicInfoEx.userId}}" class="fly-list-avatar">
			            <img src="{{topicInfoEx.headUrl}}" alt="">
			          </a>
			          <h2 class="fly-tip">
			            <a href="${pageContext.request.contextPath}/topicDetail.do?topicid={{topicInfoEx.id}}">{{topicInfoEx.title}}</a>
						{{if topicInfoEx.isGood==1 }}  
						<span class="fly-tip-jing">精帖</span>
						{{/if}}
			          </h2>
			          <p>
			            <span><a href="user/home.html">{{topicInfoEx.nickName}}</a></span>
			            <span>{{topicInfoEx.createTime}}</span>
			            <span>{{topicInfoEx.classname}}</span>
						<c:if test="${sessionScope.userInfo.isManager==1}">
							<a class='delete_topic' href="${pageContext.request.contextPath}/deleteTopicServlet.do?topicid={{topicInfoEx.id}}">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
					   		<span class='editor_topic'>编辑</span>
						</c:if>
			            <span class="fly-list-hint"> 
			              <i class="iconfont" title="回答">&#xe60c;</i> {{topicInfoEx.commentCount}}
			              <i class="iconfont" title="人气">&#xe60b;</i> {{topicInfoEx.viewCount}}
			            </span>
			          </p>
						<form class="top_good_form" action="${pageContext.request.contextPath}/editTopGoodServlet.do" method="get" style="position: absolute;left: 385px;top: 33px;">
					        ( 加精 <input type="checkbox" value="good" class="top_good" name="topAndGood">
					                    顶置 <input type="checkbox" value="top" class="top_good" name="topAndGood">
					        <input type="hidden" name="topicid" value="{{topicInfoEx.id}}"/>
					        <input type="submit" value="确定"/> )
					    </form>
			          </li>
		{{ /each}}
		              
		</script>
	
      
<!--       <div style="text-align: center">
        <div class="laypage-main">
          <a href="jie/index.html" class="laypage-next">更多求解</a>
        </div>
      </div> -->
      
    </div>
  </div>
  
  <div class="edge">
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