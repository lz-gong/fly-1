<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>提示页面</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="keywords" content="fly,layui,前端社区">
  <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/res/layui/css/layui.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/global.css">
  <style type="text/css">
        .td{
            padding: 14px 24px;
        }
        #table1{
        	position:relative;
        	left:37%;
        	top:-20px;
        	border-color: grey;
        	font-size:16px;
        }
  </style>
</head>
<body>

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
<%--       <a class="unlogin" href="user/login.html"><i class="iconfont icon-touxiang"></i></a>
      <span><a href="${pageContext.request.contextPath }/jsp/login.jsp">登入</a><a href="${pageContext.request.contextPath }/jsp/reg.jsp">注册</a></span>
      <p class="out-login">
        <a href="" onclick="layer.msg('正在通过QQ登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-qq" title="QQ登入"></a>
        <a href="" onclick="layer.msg('正在通过微博登入', {icon:16, shade: 0.1, time:0})" class="iconfont icon-weibo" title="微博登入"></a>
      </p>    --%>
      
      <!-- 登入后的状态 -->
      <!-- 
      <a class="avatar" href="user/index.html">
        <img src="http://tp4.sinaimg.cn/1345566427/180/5730976522/0">
        <cite>贤心</cite>
        <i>VIP2</i>
      </a>
      <div class="nav">
        <a href="/user/set/"><i class="iconfont icon-shezhi"></i>设置</a>
        <a href="/user/logout/"><i class="iconfont icon-tuichu" style="top: 0; font-size: 22px;"></i>退了</a>
      </div> -->
      
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

<div class="main">
  <div class="fly-panel">
    <div class="fly-none">
<!--       <h2><i class="iconfont icon-tishilian"></i></h2> -->
<!--       <p>
      	初级粉丝------0-50(经验)<br/>
      	中级粉丝------50-150(经验)<br/>
      	高级粉丝------150-300(经验)<br/>
      	正式会员------300-500(经验)<br/>
      	核心会员------500-750(经验)<br/>
      	铁杆会员------750-1050(经验)<br/>
      	知名人士------1050-1400(经验)<br/>
      	人气楷模------1400-1800(经验)<br/>
      	意见领袖------1800-2250(经验)<br/>
      	进阶元老------2250-3250(经验)<br/>
      	资深元老------3250-5000(经验)<br/>
      	初级粉丝------大于5000(经验)<br/>
      </p> -->
      
 <table id="table1" border="1">
    <tr>
        <td class="td">初级粉丝</td>
        <td class="td">0-50(经验)</td>
    </tr>
    <tr>
        <td class="td">中级粉丝</td>
        <td class="td">50-150(经验)</td>
    </tr>
    <tr>
        <td class="td">高级粉丝</td>
        <td class="td">150-300(经验)</td>
    </tr>
    <tr>
        <td class="td">正式会员</td>
        <td class="td">300-500(经验)</td>
    </tr>
    <tr>
        <td class="td">核心会员</td>
        <td class="td">500-750(经验)</td>
    </tr>
    <tr>
        <td class="td">铁杆会员</td>
        <td class="td">750-1050(经验)</td>
    </tr>
    <tr>
        <td class="td">知名人士</td>
        <td class="td">1050-1400(经验)</td>
    </tr>
    <tr>
        <td class="td">人气楷模</td>
        <td class="td">1400-1800(经验)</td>
    </tr>
    <tr>
        <td class="td">意见领袖</td>
        <td class="td">1800-2250(经验)</td>
    </tr>
    <tr>
        <td class="td">进阶元老</td>
        <td class="td">2250-3250(经验)</td>
    </tr>
    <tr>
        <td class="td">资深元老</td>
        <td class="td">3250-5000(经验)</td>
    </tr>
    <tr>
        <td class="td">荣耀元老</td>
        <td class="td">5000-8000(经验)</td>
    </tr>
    <tr>
        <td class="td">超神</td>
        <td class="td">大于8000(经验)</td>
    </tr>
</table>
	<br/><br/>
	<span style="font-size:18px;position:relative;top:-38px;">tips:发帖10经验、采纳6经验、被采纳10经验、回复1经验（不包含回复的回复）</span>
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
<script src="${pageContext.request.contextPath}/res/layui/layui.js"></script>
<script>
layui.cache.page = '';
layui.cache.user = {
  username: '游客'
  ,uid: -1
  ,avatar: '${pageContext.request.contextPath}/res/images/avatar/00.jpg'
  ,experience: 83
  ,sex: '男'
};
layui.config({
  version: "2.0.0"
  ,base: '${pageContext.request.contextPath}/res/mods/'
}).extend({
  fly: 'index'
}).use('fly');
</script>

</body>
</html>