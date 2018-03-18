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
  <title>编辑帖子</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="keywords" content="fly,layui,前端社区">
  <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
  <link rel="stylesheet" href="${pageContext.request.contextPath }/res/layui/css/layui.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath }/res/css/global.css">
</head>
<body>

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
      
      <a class="avatar" href="${pageContext.request.contextPath }/user/index.html">
        <img src="${sessionScope.userInfo.headUrl}">
        <cite>${sessionScope.userInfo.nickname }</cite>
      </a>
      <a style="display:inline-block; position:relative; top:-1px" class="avatar" href="${pageContext.request.contextPath }/jsp/grade_list.jsp"><i>${sessionScope.userInfo.grade }</i> </a>
      <div class="nav">
        <a href="${pageContext.request.contextPath }/user/set.html"><i class="iconfont icon-shezhi"></i>设置</a>
        <a href="${pageContext.request.contextPath }/logout.do"><i class="iconfont icon-tuichu" style="top: 0; font-size: 22px;"></i>退了</a>
      </div>
      
    </div>
  </div>
</div>

<div class="main layui-clear">
  <div class="fly-panel" pad20>
    <h2 class="page-title">编辑帖子</h2>
    
    <!-- <div class="fly-none">并无权限</div> -->

    <div class="layui-form layui-form-pane">
      <form action="${pageContext.request.contextPath }//editTopicSubmitServlet.do" method="post">
      	<input type="hidden" value="${requestScope.topicInfoEx.id }" name="topicid"/>
      
        <div class="layui-form-item">
          <label for="L_title" class="layui-form-label">标题</label>
          <div class="layui-input-block">
            <input value="${requestScope.topicInfoEx.title }" type="text" id="L_title" name="title" required lay-verify="required" autocomplete="off" class="layui-input">
          </div>
        </div>
        <div class="layui-form-item layui-form-text">
          <div class="layui-input-block">
            <textarea id="L_content" name="content" required lay-verify="required" class="layui-textarea fly-editor" style="height: 260px;">${requestScope.topicInfoEx.content }</textarea>
          </div>
          <label for="L_content" class="layui-form-label" style="top: -2px;">描述</label>
        </div>
        <div class="layui-form-item">
          <div class="layui-inline">
            <label class="layui-form-label">所在类别</label>
            <div class="layui-input-block">
              <select lay-verify="required" name="class" >
                <option >${requestScope.topicInfoEx.classname }</option>
                
                <c:forEach items="${option}" var="c">
                	<option value="${c.id}">${c.classname}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="layui-inline">
            <label class="layui-form-label">悬赏飞吻</label>
            <div class="layui-input-block">
              <select name="kiss">
                <option value="${requestScope.topicInfoEx.rewardKiss }" >5</option>
              </select>
            </div>
          </div>
        </div>
       <!--  <div class="layui-form-item">
          <label for="L_vercode" class="layui-form-label">人类验证</label>
          <div class="layui-input-inline">
            <input type="text" id="L_vercode" name="vercode" required lay-verify="required" placeholder="请回答后面的问题" autocomplete="off" class="layui-input">
          </div>
          <div class="layui-form-mid">
            <span style="color: #c00;">1+1=?</span>
          </div>
        </div> -->
        <div class="layui-form-item">
        
          <!-- <button class="layui-btn" lay-filter="*" lay-submit>立即发布</button> -->
          <!-- 注：请求异常是lay-filter="*"引起的，   lay-submit不要删掉，这个可以帮我们检查表单是否填写了-->
        <input type="submit" class="layui-btn" value="确认编辑" lay-submit>
          
        </div>
      </form>
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
layui.cache.page = 'jie';
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
</html>