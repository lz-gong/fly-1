<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>登入</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="keywords" content="fly,layui,前端社区">
  <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
  <link rel="stylesheet" href="${pageContext.request.contextPath }/res/layui/css/layui.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath }/res/css/global.css">
  <script type="text/javascript">
  		window.onload=function(){
  			document.cookie='jumpToAll=yes;Path=/';
  			
  			//点击验证码图片实现刷新
  			var OCheckCodeImg=document.getElementById('checkCode');
  			OCheckCodeImg.onclick=function (){
  				var freClick=get_cookie('freClick');
  				if(freClick==''){ //对于频繁点击的操作
  					document.cookie="freClick=0";
  				}else{
  					if(freClick<=10){
  						OCheckCodeImg.src='${pageContext.request.contextPath }/checkCodeServlet.do?'+Math.random();
  						document.cookie="freClick="+(freClick-1+2); //为了将字符串转换为数字
  					}else{
  						alert('检测到您刷新频率过快！');
  					}
  				}
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
  		}
  </script>
</head>
<body>

<!-- 判断用户是否已经登录，如果登录了，直接跳到主页 -->
<c:if test="${!empty sessionScope.userInfo}">
	<c:redirect url="/jsp/index.jsp"></c:redirect>
</c:if>

<div class="header">
  <div class="main">
    <a class="logo" href="${pageContext.request.contextPath}/goIndex/all" title="Fly">Fly社区</a>
    <div class="nav">
      <a class="nav-this" href="${pageContext.request.contextPath }/goIndex/all">
        <i class="iconfont icon-wenda"></i>问答
      </a>
      <a href="http://www.layui.com/" target="_blank">
        <i class="iconfont icon-ui"></i>框架
      </a>
    </div>
    
    <div class="nav-user">      
      
      <!-- 未登入状态 -->
      <a class="unlogin" href="${pageContext.request.contextPath }/jsp/login.jsp"><i class="iconfont icon-touxiang"></i></a>
      <span><a href="${pageContext.request.contextPath }/jsp/login.jsp">登入</a><a href="${pageContext.request.contextPath }/jsp/reg.jsp">注册</a></span>
      <p class="out-login">
        <a href="" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-qq" title="QQ登入"></a>
        <a href="" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-weibo" title="微博登入"></a>
      </p>   
      
      <!-- 登入后的状态 -->
      <!-- <a class="avatar" href="${pageContext.request.contextPath }/user/index.html">
        <img src="http://tp4.sinaimg.cn/1345566427/180/5730976522/0">
        <cite>贤心</cite>
        <i>VIP2</i>
      </a>
      <div class="nav">
        <a href="${pageContext.request.contextPath }/user/set.html"><i class="iconfont icon-shezhi"></i>设置</a>
        <a href=""><i class="iconfont icon-tuichu" style="top: 0; font-size: 22px;"></i>退了</a>
      </div> -->
      
    </div>
  </div>
</div>

<div class="main layui-clear">

  <div class="fly-panel fly-panel-user" pad20>
    <div class="layui-tab layui-tab-brief">
      <ul class="layui-tab-title">
        <li class="layui-this">登入</li>
        <li><a href="${pageContext.request.contextPath }/jsp/reg.jsp">注册</a></li>
      </ul>
      <div class="layui-form layui-tab-content" id="LAY_ucm" style="padding: 20px 0;">
        <div class="layui-tab-item layui-show">
          <div class="layui-form layui-form-pane">
          
          <!-- 表单在此 login -->
            <form action="${pageContext.request.contextPath}/login.do" method="post">
              <div style="position: relative;" class="layui-form-item">
                <label for="L_email" class="layui-form-label">邮箱</label> 
                <div class="layui-input-inline">
                  <input value="${cookie.username.value}" type="email" id="L_email" name="email" required lay-verify="required" autocomplete="off" class="layui-input">
                </div>
                
                <c:if test="${cookie.error_login!=null }">
                	<span style="color: #c00; position: absolute; left:314px;top:6px;" >用户不存在或密码错误！</span>
                </c:if>
                <c:if test="${cookie.error_email!=null }">
                	<span style="color: #c00; position: absolute; left:314px;top:6px;" >邮箱格式不正确！</span>
                </c:if>
                
              </div>
              <div style="position: relative;" class="layui-form-item">
                <label for="L_pass" class="layui-form-label">密码</label>
                <div class="layui-input-inline">
                  <input type="password" id="L_pass" name="pass" required lay-verify="required" autocomplete="off" class="layui-input">
                </div>
                
				<c:if test="${cookie.error_password!=null }">
                	<span style="color: #c00; position: absolute; left:314px;top:6px;" >密码长度小于6位！</span>
                </c:if>
                
              </div>
               <div style="position: relative;" class="layui-form-item">
                <label for="L_pass" class="layui-form-label">验证码</label>
                <div class="layui-input-inline">
                  <input placeholder="点击验证码刷新！" id="checkCode_input" type="text" name="checkCode" required lay-verify="required" autocomplete="off" class="layui-input">
                </div>
                <img id="checkCode" src="${pageContext.request.contextPath }/checkCodeServlet.do" />
				<c:if test="${cookie.error_checkCode!=null }">
<!--                 	<script type="text/javascript">
                		var checkCodeInput=document.getElementById('checkCode_input');
                		checkCodeInput.placeholder='验证码错误！';
                	</script> -->
                	<span style="color: #c00; position: absolute; left:472px;top:20px;" >验证码错误！</span>
                </c:if>
              </div>
              
              <!-- <div class="layui-form-item">
                <label for="L_vercode" class="layui-form-label">人类验证</label>
                <div class="layui-input-inline">
                  <input type="text" id="L_vercode" name="vercode" required lay-verify="required" placeholder="请回答后面的问题" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-mid">
                  <span style="color: #c00;">1+1=?</span>
                </div>
              </div> -->
              
              <div style="position: relative; top:7px;" class="layui-form-item">
             <!--  <input type="submit" class="layui-btn" lay-filter="*" lay-submit value="立即登录"> -->
                <input  type="submit" class="layui-btn" lay-submit value="立即登录">
                <span style="padding-left:20px;">
                  <a href="${pageContext.request.contextPath}/jsp/tips.jsp?tips=该功能还在路上~~~">忘记密码？</a>
                </span>
              </div>
<!--               <div class="layui-form-item fly-form-app">
                <span>或者使用社交账号登入</span>
                <a href="http://fly.layui.com:8098/app/qq" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-qq" title="QQ登入"></a>
                <a href="http://fly.layui.com:8098/app/weibo/" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-weibo" title="微博登入"></a>
              </div> -->
<!--                <div class="layui-form-item fly-form-app">
                <span>或者使用社交账号登入</span>
                <a href="javascript:;" onclick="layer.msg('暂不支持！', {icon:16, shade: 0.1, time:0})" class="iconfont icon-qq" title="QQ登入"></a>
                <a href="javascript:;" onclick="layer.msg('暂不支持！', {icon:16, shade: 0.1, time:0})" class="iconfont icon-weibo" title="微博登入"></a>
              </div> -->
            </form>
          </div>
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
<script src="${pageContext.request.contextPath }/res/layui/layui.js"></script>
<script>
layui.cache.page = 'user';
layui.cache.user = {
  username: '游客'
  ,uid: -1
  ,avatar: '${pageContext.request.contextPath }/res/images/avatar/00.jpg'
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
</html></html>