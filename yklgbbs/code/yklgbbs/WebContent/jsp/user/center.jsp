<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${userInfo==null}">
	<c:redirect url="/jsp/login.jsp"></c:redirect>
</c:if>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>用户中心</title>
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
  	
  	
  	<script type="text/javascript">
  		window.onload=function(){
			//这里就是ajax生成局部页面的内容
			//1：给我的发帖进行分页
			$("#page_mytopic").pagination({ 
				pageIndex:0, //从pageIndex+1页开始显示（重要）
				pageSize:10, //分页后每页显示10条数据
				pageBtnCount:9, //按钮个数，包括上下页，首尾页
				showFirstLastBtn:true, //是否显示首页，尾页按钮
				firstBtnText:"首页", //这里可以设置个性显示内容
				lastBtnText:"尾页",
				prevBtnText:"上一页",
				nextBtnText:"下一页",
				remote:{
					//每次点击按钮提交到处理请求的Servlet
					url:'${pageContext.request.contextPath}//ajaxUserCenter.do?req=myTopic', 
					success:function(data){ //注：data为：json字符串通过转换为了json对象
						var html=template('mdl_mytopic',data); //date渲染到test模板中
						document.getElementById('mytopic_num').innerHTML=data['total']; //直接获取，不用模板
						document.getElementById('myTopic').innerHTML=html;
						
						var myTopic=document.getElementById('myTopic');
						var ALi=myTopic.getElementsByTagName('li');
						if(ALi.length==0){
							document.getElementById('no_topic').style.display='block';
						}
					}
				}
			});
			
			
			//这里就是ajax生成局部页面的内容
			//2：给我收藏的帖子进行分页
			$("#page_mycollect").pagination({ 
				pageIndex:0, //从pageIndex+1页开始显示（重要）
				pageSize:10, //分页后每页显示10条数据
				pageBtnCount:9, //按钮个数，包括上下页，首尾页
				showFirstLastBtn:true, //是否显示首页，尾页按钮
				firstBtnText:"首页", //这里可以设置个性显示内容
				lastBtnText:"尾页",
				prevBtnText:"上一页",
				nextBtnText:"下一页",
				remote:{
					//每次点击按钮提交到处理请求的Servlet
					url:'${pageContext.request.contextPath}//ajaxUserCenter.do?req=myCollect', 
					success:function(data){ //注：data为：json字符串通过转换为了json对象
						var html=template('mdl_mycollect',data); //date渲染到test模板中
						document.getElementById('mycollect_num').innerHTML=data['total']; //直接获取，不用模板
						document.getElementById('myCollect').innerHTML=html;
						
						var myCollect=document.getElementById('myCollect');
						var ALi=myCollect.getElementsByTagName('li');
						if(ALi.length==0){
							document.getElementById('no_collect').style.display='block';
						}
					}
				}
			});
  		}
  	</script>
  
</head>
<body>

<script id="mdl_mytopic" type="text/html">
	  {{ each list as topicInfoEx}}
			<li>
              <a class="jie-title" href="${pageContext.request.contextPath}/topicDetail.do?topicid={{topicInfoEx.id}}" target="_blank">{{topicInfoEx.title}}</a>
              <i>{{topicInfoEx.createTime}}</i>
              <a class="mine-edit" href="${pageContext.request.contextPath}/editTopicServlet.do?topicid={{topicInfoEx.id}}">编辑</a>
              <em>{{topicInfoEx.viewCount}}阅/{{topicInfoEx.answerNum}}答</em>
            </li>
	  {{/each}}
</script>

<script id="mdl_mycollect" type="text/html">
	  {{ each list as topicInfoEx}}
			<li>
              <a class="jie-title" href="${pageContext.request.contextPath}/topicDetail.do?topicid={{topicInfoEx.id}}" target="_blank">{{topicInfoEx.title}}</a>
              <i>收藏于{{topicInfoEx.collectTopicTime}}</i>  
            </li>
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
      <!-- 登入后的状态 -->
      <a class="avatar" href="${pageContext.request.contextPath }/jsp/user/center.jsp">
        <img src="${sessionScope.userInfo.headUrl}">
        <cite>${sessionScope.userInfo.nickname }</cite>
      </a>
      <a style="display:inline-block; position:relative; top:-1px" class="avatar" href="${pageContext.request.contextPath }/jsp/grade_list.jsp"><i>${sessionScope.userInfo.grade }</i> </a>
      <div class="nav">
        <a href="${pageContext.request.contextPath }/jsp/user/set.jsp"><i class="iconfont icon-shezhi"></i>设置</a>
        <a href="${pageContext.request.contextPath }/logout.do"><i class="iconfont icon-tuichu" style="top: 0; font-size: 22px;"></i>退了</a>
      </div>
      
    </div>
  </div>
</div>

<div class="main fly-user-main layui-clear">
  <ul class="layui-nav layui-nav-tree layui-inline" lay-filter="user">
    <li class="layui-nav-item">
      <a href="${pageContext.request.contextPath }/home/${sessionScope.userInfo.id}">
        <i class="layui-icon">&#xe609;</i>
        我的主页
      </a>
    </li>
    <li class="layui-nav-item layui-this">
      <a href="${pageContext.request.contextPath }/jsp/user/center.jsp">
        <i class="layui-icon">&#xe612;</i>
        用户中心
      </a>
    </li>
    <li class="layui-nav-item">
      <a href="${pageContext.request.contextPath }/jsp/user/set.jsp">
        <i class="layui-icon">&#xe620;</i>
        基本设置
      </a>
    </li>
    <li class="layui-nav-item">
      <a href="${pageContext.request.contextPath }/jsp/user/message.jsp">
        <i class="layui-icon">&#xe611;</i>
        我的消息
      </a>
    </li>
  </ul>
  
  <div class="site-tree-mobile layui-hide">
    <i class="layui-icon">&#xe602;</i>
  </div>
  <div class="site-mobile-shade"></div>
  
  <div class="fly-panel fly-panel-user" pad20>
    <!--
    <div class="fly-msg" style="margin-top: 15px;">
      您的邮箱尚未验证，这比较影响您的帐号安全，<a href="activate.html">立即去激活？</a>
    </div>
    -->
    <div class="layui-tab layui-tab-brief" lay-filter="user">
      <ul class="layui-tab-title" id="LAY_mine">
        <li data-type="mine-jie" lay-id="index" class="layui-this">我发的帖（<span id="mytopic_num">89</span>）</li>
        <li data-type="collection" data-url="/collection/find/" lay-id="collection">我收藏的帖（<span id="mycollect_num" >16</span>）</li>
      </ul>
      <div class="layui-tab-content" style="padding: 20px 0;">
        <div class="layui-tab-item layui-show">
          <div style="display:none;font-size:20px;" id="no_topic" class="fly-none">还没有发表过帖子!</div>
          <ul id="myTopic" class="mine-view jie-row">
<!--             <li>
              <a class="jie-title" href="/jie/8116.html" target="_blank">LayIM 3.5.0 发布，移动端版本大更新（带演示图）</a>
              <i>2017/3/14 上午8:30:00</i>
              <a class="mine-edit" href="/jie/edit/8116">编辑</a>
              <em>661阅/10答</em>
            </li> -->
<!--             <li>
              <a class="jie-title" href="/jie/8116.html" target="_blank">LayIM 3.5.0 发布，移动端版本大更新（带演示图）</a>
              <i>2017/3/14 上午8:30:00</i>
              <a class="mine-edit" href="/jie/edit/8116">编辑</a>
              <em>661阅/10答</em>
            </li>
            <li>
              <a class="jie-title" href="/jie/8116.html" target="_blank">LayIM 3.5.0 发布，移动端版本大更新（带演示图）</a>
              <i>2017/3/14 上午8:30:00</i>
              <a class="mine-edit" href="/jie/edit/8116">编辑</a>
              <em>661阅/10答</em>
            </li> -->
          </ul>
      <!-- 分页页码  下面的class去掉貌似也没什么问题-->
	  <div id="page_mytopic" class="m-pagination"></div>
          <div id="LAY_page"></div>
        </div>
        <div class="layui-tab-item">
        <div style="display:none;font-size:20px;" id="no_collect" class="fly-none">还没有收藏任何帖子!</div>
          <ul id="myCollect" class="mine-view jie-row">
<!--             <li>
              <a class="jie-title" href="http://fly.layui.com/jie/5366.html" target="_blank">layui 常见问题的处理和实用干货集锦</a>
              <i>收藏于23小时前</i>  
            </li> -->
          </ul>
      <!-- 分页页码  下面的class去掉貌似也没什么问题-->
	  <div id="page_mycollect" class="m-pagination"></div>
          <div id="LAY_page1"></div>
        </div>
      </div>
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
<script src="../../res/layui/layui.js"></script>
<script>
layui.cache.page = 'user';
layui.cache.user = {
  username: '游客'
  ,uid: -1
  ,avatar: '../../res/images/avatar/00.jpg'
  ,experience: 83
  ,sex: '男'
};
layui.config({
  version: "2.0.0"
  ,base: '../../res/mods/'
}).extend({
  fly: 'index'
}).use('fly');
</script>

</body>
</html>