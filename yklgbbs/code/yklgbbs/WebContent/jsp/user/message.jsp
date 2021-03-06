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
  <title>我的消息</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="keywords" content="fly,layui,前端社区">
  <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
  <link rel="stylesheet" href="../../res/layui/css/layui.css">
  <link rel="stylesheet" href="../../res/css/global.css">
  
    <!-- 首先需要引入css样式表 与 js -->
    <link href="${pageContext.request.contextPath}/res/css/mricode.pagination.css" rel="stylesheet" type="text/css" charset="utf-8">
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/mricode.pagination.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/template-web.js"></script>
  
  	<script type="text/javascript">
  		window.onload=function(){
			//这里就是ajax生成局部页面的内容
			//1：给我的消息进行分页
			$("#page_message").pagination({ 
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
					url:'${pageContext.request.contextPath}/ajaxMessage.do', 
					success:function(data){ //注：data为：json字符串通过转换为了json对象
						var html=template('mdl_message',data); //date渲染到test模板中
						document.getElementById('myMessage').innerHTML=html;
						var OMessage=document.getElementById('myMessage');
						var ALi=OMessage.getElementsByTagName('li');
						if(ALi.length!=0){
							document.getElementById('delete_all').style.display='block';
							foreachDeleteMessage();
						}else{
							document.getElementById('no_message').style.display='block';
						}
					}
				}
			});
			
			function foreachDeleteMessage(){
				//遍历删除单个消息事件
				var ADeleteOne=document.getElementsByClassName('delete_one');
				for(var i=0;i<ADeleteOne.length;i++){
					ADeleteOne[i].onclick=deleteOneMessageAjax;
				}
			}
			
			//删除单个消息
            function  deleteOneMessageAjax(){
            	if(confirm('确定删除该消息吗？')==false){
            		return;
            	}
                //获取评论id
                var messageid=this.getElementsByTagName('input')[0].value;
                //创建ajax对象
                var xhr=new XMLHttpRequest();
                //连接服务器
                xhr.open("get","${pageContext.request.contextPath}//deleteMessage.do?req="+messageid,true);
                //发送
                xhr.send();
                //响应
                xhr.onreadystatechange=function () {
                    if(xhr.readyState===4){
                        if(xhr.status===200){
                            var temp=xhr.responseText;
                            if(temp=='true'){
								document.getElementById('li_'+messageid).style.display='none';
                            }else{
                                alert("呀！删除失败了");
                            }
                        }
                    }
                }
            }
			
			//删除全部消息
			var delete_all=document.getElementById('delete_all');
			delete_all.onclick=function () {
            	if(confirm('确定清空全部消息吗？')==false){
            		return;
            	}
                //创建ajax对象
                var xhr=new XMLHttpRequest();
                //连接服务器
                xhr.open("get","${pageContext.request.contextPath}//deleteMessage.do?req=all",true);
                //发送
                xhr.send();
                //响应
                xhr.onreadystatechange=function () {
                    if(xhr.readyState===4){
                        if(xhr.status===200){
                            var temp=xhr.responseText;
                            if(temp=='true'){
                            	window.location.href="${pageContext.request.contextPath}/jsp/user/message.jsp";
                            }else{
                                alert("呀！清空失败了");
                            }
                        }
                    }
                }
            }
  		}
  	</script>
</head>
<body>

<script id="mdl_message" type="text/html">
	  {{ each list as messageEx}}
          <li id="li_{{messageEx.id}}">
            <blockquote class="layui-elem-quote">
              <a href="${pageContext.request.contextPath}/home/{{messageEx.userid}}" target="_blank"><cite>{{messageEx.nickname}}</cite></a>回答了您的求解<a target="_blank" href="${pageContext.request.contextPath}/topicDetail.do?topicid={{messageEx.topicid}}${'#'}{{messageEx.commentid}}"><cite>{{messageEx.title}}</cite></a>
            </blockquote>
            <p><span>{{messageEx.commentTime}}</span><a href="javascript:;" class="delete_one layui-btn layui-btn-small layui-btn-danger">删除<input type="hidden" value="{{messageEx.id}}"/></a></p>
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
    <li class="layui-nav-item">
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
    <li class="layui-nav-item layui-this">
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
	  <div class="layui-tab layui-tab-brief" lay-filter="user" id="LAY_msg" style="margin-top: 15px;">
	    <!-- <button class="layui-btn layui-btn-danger" id="LAY_delallmsg">清空全部消息</button> -->
	    <button style="display:none;" class="layui-btn layui-btn-danger" id="delete_all">清空全部消息</button>
	    <div style="display:none;font-size:20px;" id="no_message" class="fly-none">呀！还没有任何消息</div>
	    <div  id="LAY_minemsg" style="margin-top: 10px;">
        <!--<div class="fly-none">您暂时没有最新消息</div>-->
        <ul id="myMessage" class="mine-msg">
<!--           <li data-id="123">
            <blockquote class="layui-elem-quote">
              <a href="/jump?username=Absolutely" target="_blank"><cite>Absolutely</cite></a>回答了您的求解<a target="_blank" href="/jie/8153.html/page/0/#item-1489505778669"><cite>layui后台框架</cite></a>
            </blockquote>
            <p><span>1小时前</span><a href="javascript:;" class="layui-btn layui-btn-small layui-btn-danger fly-delete">删除</a></p>
          </li> -->
<!--           <li data-id="123">
            <blockquote class="layui-elem-quote">
              系统消息：欢迎使用 layui
            </blockquote>
            <p><span>1小时前</span><a href="javascript:;" class="layui-btn layui-btn-small layui-btn-danger fly-delete">删除</a></p>
          </li> -->
        </ul>
      <!-- 分页页码  下面的class去掉貌似也没什么问题-->
	  <div id="page_message" class="m-pagination"></div>
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