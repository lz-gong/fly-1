<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<c:if test="${empty requestScope.topicDetail }">  <!-- 直接访问这里就跳转到index.jsp -->
	<c:redirect url="/goIndex/all"></c:redirect>
</c:if>
 
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <!-- <title>Fly Template v2.0，基于 layui 的轻量级社区模版</title> -->
  <title>${requestScope.topicDetail.title}</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="keywords" content="fly,layui,前端社区">
  <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/res/layui/css/layui.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/global.css">
  
  <!-- 这是是将json字符串中的内容解析到模板中 -->
  <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/template-web.js"></script>
  <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/util/transformContent.js"></script>
  <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/config/config.js"></script>
  
  <script type="text/javascript">
        window.onload=function () {
        	
       	//判断是否有top榜的cookie，如果没有则添加
       	if(get_cookie('weekAnswerList')==''){
       		var hidd=document.getElementById('hidden_topicid').value;
       		window.location.href="${pageContext.request.contextPath}/jsp/add_cookie.jsp?topicid="+hidd+"&check=0";
       	}
       	
		/* 
			注：只有进入到index才会获取到下面的这些cookie，否则在本页面和index_fenye页面不能正常显示
		*/
		
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
        	
        	
        	var main_content=document.getElementById('main_content');
        	main_content.innerHTML=replaceAll(getFaceConfig(),replaceAll(getContentConfig(),main_content.innerHTML));
        	
            var collect_button=document.getElementById('collect_button');
            var cancale_collect_button=document.getElementById('cancle_collect_button');
            var hidden_topicid=document.getElementById('hidden_topicid');
            var collect_onOff=document.getElementById('collect_onOff');
            var hidden_userid=document.getElementById('hidden_userid').value;
            var nickName='${sessionScope.userInfo.nickname}';
            
            collect_button.onclick=function () {
                //创建ajax对象
                var xhr=new XMLHttpRequest();
                //连接服务器
                xhr.open("get","${pageContext.request.contextPath}/collectServlet.do?topicid="+hidden_topicid.value+"&collect_onOff=0",true);
                //发送
                xhr.send();
                //响应
                xhr.onreadystatechange=function () {
                    if(xhr.readyState===4){
                        if(xhr.status===200){
                        	var temp=xhr.responseText;
                            if(temp=='true'){f
                            	collect_button.style.display='none';
                                cancale_collect_button.style.display="inline-block";
                            }else{
                            	window.location.href=temp; 
                            }
                        }
                    }
                }
            }
            
            cancale_collect_button.onclick=function () {
                //创建ajax对象
                var xhr=new XMLHttpRequest();
                //连接服务器
                xhr.open("get","${pageContext.request.contextPath}/collectServlet.do?topicid="+hidden_topicid.value+"&collect_onOff=1",true);
                //发送
                xhr.send();
                //响应
                xhr.onreadystatechange=function () {
                    if(xhr.readyState===4){
                        if(xhr.status===200){
                        	var temp=xhr.responseText;
                            if(temp=='true'){
                            	collect_button.style.display='inline-block';
                                cancale_collect_button.style.display="none";
                            }
                        }
                    }
                }
            }
            
            var comment_submit=document.getElementById('comment_submit');
            var confirm_edit_comment=document.getElementById('confirm_edit_comment');
            var L_content=document.getElementById('L_content'); //获取写评论的文本域
            
            
            //开始回复
            comment_submit.onclick=function () {
            	if(L_content.value.trim()==''){ //输入为空，不能提交
            		return;
            	}
            	var xhr = new XMLHttpRequest();
            	xhr.open("post","${pageContext.request.contextPath}/addComment.do",true);
            	// 如果 使用post发送数据 必须 设置 如下内容
                // 修改了 发送给 服务器的 请求报文的 内容
                // 如果需要像 HTML 表单那样 POST 数据，请使用 setRequestHeader() 来添加 HTTP 头。然后在 send() 方法中规定您希望发送的数据：
                xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
             	// 发送
                // post请求 发送的数据 写在 send方法中
                // 格式 name=jack&age=18 字符串的格式
                xhr.send('content='+transformLinkForAjaxPost(L_content.value)+'&topicId='+hidden_topicid.value);
             	// 注册事件
                xhr.onreadystatechange = function () {
                    /* if (ajax.readyState==4&&ajax.status==200) {  //网上的写法
                        console.log(ajax.responseText);
                    } */
                	if(xhr.readyState===4){
                        if(xhr.status===200){
                        	var temp=xhr.responseText;
                        	//var obj = JSON.parse(temp); //这里必须将获得的json字符串转换为json(obj)对象才能用template解析！
                        	//data为ajax返回数据
    						//var html=template('ajax_comment',obj); //temp渲染到test模板中
    						//document.getElementById('jieda').innerHTML=html; 
    						//L_content.value=''; //清空文本域中的内容
    						if(temp=='true'){
    							getComment();
    							L_content.value='';
    						}else{
    							window.location.href=temp;
    						}
                        }
                    }
                }
            } 
            
            

            /* 第一次进入帖子时，主动加载评论 */
            function getComment() {
            	var xhr = new XMLHttpRequest();
            	xhr.open("post","${pageContext.request.contextPath}/getCommentListServlet.do",true);
            	// 如果 使用post发送数据 必须 设置 如下内容
                // 修改了 发送给 服务器的 请求报文的 内容
                // 如果需要像 HTML 表单那样 POST 数据，请使用 setRequestHeader() 来添加 HTTP 头。然后在 send() 方法中规定您希望发送的数据：
                xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
             	// 发送
                // post请求 发送的数据 写在 send方法中
                // 格式 name=jack&age=18 字符串的格式
                xhr.send('topicId='+hidden_topicid.value);
             	// 注册事件
                xhr.onreadystatechange = function () {
                    /* if (ajax.readyState==4&&ajax.status==200) {  //网上的写法
                        console.log(ajax.responseText);
                    } */
                	if(xhr.readyState===4){
                        if(xhr.status===200){
                        	var temp=xhr.responseText;
                        	var obj = JSON.parse(temp); //这里必须将获得的json字符串转换为json(obj)对象才能用template解析！
                        	var arrCommentExJson=obj['list']; //获取的是一个数组，数组中的每个元素为CommentEx对象所转换为的json
							
                        	//因为我们在ajax中上传帖子的回复 和 回复的回复时，对&+%符号进行了转义，这里我们要进行反转
                        	var face_json=getFaceConfig();
  							var content_json=getContentConfig();
                        	for(var i=0;i<arrCommentExJson.length;i++){
                        		//下面这两个顺序不可以换的
                        		arrCommentExJson[i].content=replaceAll(content_json,arrCommentExJson[i].content);
  								arrCommentExJson[i].content=replaceAll(face_json,arrCommentExJson[i].content);
  								//设置对应评论用户的等级（根据experience）
  								arrCommentExJson[i].grade=getGrade(arrCommentExJson[i].experience);
  							}
                        	
                        	/* for(key in obj){
                            alert(key); //输出是json中的每一个key
                            alert(obj[key]); //输出json中的每一个value值
                     		} */
                        	
                        	//data为ajax返回数据
    						var html=template('ajax_comment',obj); //temp渲染到test模板中
    						document.getElementById('jieda').innerHTML=html; 
      						var OJieda=document.getElementById('jieda');
      						
      						//开始给评论添加评论
      						for(var i=0;i<arrCommentExJson.length;i++){
      							
      							if(arrCommentExJson[i].isTopic==1){ //说明这条评论是评论的评论
      								var topicOrCommentId=arrCommentExJson[i].topicOrCommentId;
      								var OLi=document.getElementById('li_'+topicOrCommentId);
      								var replaysHomePath='${pageContext.request.contextPath}/home/'+arrCommentExJson[i].userId; //评论的评论的用户home主页(replays就评论的评论的意思)
      								OLi.innerHTML+=
      									'<span style="line-height: 30px">'+
      				             			'<a style="text-decoration: none; color: #4f99cf" href="'+replaysHomePath+'">'+arrCommentExJson[i].nickName+'</a>&nbsp;'+
      				             			'<span> 回复：</span>&nbsp;'+
      				             			'<span>'+arrCommentExJson[i].content+'</span>'+
      				            		'</span><br/>'; 
      							}
      						}
      						
      						
    						if(OJieda.innerHTML.trim()==''){
    							OJieda.innerHTML='<li data-id="12" class="jieda-daan"><div class="detail-body jieda-body" style="text-align:center">消灭零回复</div></li>';
    						}else{

    							dianZan(); //点赞方法
        						caiNa();   //采纳方法
        						deleteComment(); //删除评论方法
        						editComment(); //编辑评论方法
        						replyCommentClick(); //回复的回复点击动作
        						submitReplyComment(); //提交回复的回复点击动作
        						
        						var Mao=location.hash;
        						if(Mao!=null && Mao!=''){
        							location.href = Mao ;  
        						}
    						}
    						
                        }
                    }
                }
            } 
            
            getComment();
            
            
            
            //ajax点赞开始
            function dianZan(){
                var AZan=document.getElementsByClassName('supportCoat');
                for(var i=0;i<AZan.length;i++){
                    AZan[i].onclick=incSupportAjax;
                }
                
                //获取网页中的点赞cookie,并将其点红
                var cookies=getAllCookie();
                var userid=document.getElementById('hidden_userid').value;
                
                if(userid!=''){
                	for(var i=0;i<cookies.length;i++){
                    	var temp2=cookies[i].split('_')[0];
                    	if(userid===temp2.trim()){
                    		var topicid=hidden_topicid.value;
                    		var temp3=cookies[i].split('_')[2];
                    		if(temp3==topicid){
                    			var temp1='li_'+cookies[i].split('=')[0].split('_')[1];
                        		var OLi=document.getElementById(temp1);
                        		var OZan=OLi.getElementsByClassName('jieda-zan')[0];
                        		OZan.className='jieda-zan zanok';
                    		}
                        }
                    }
                }
            }
            
            //获取全部cookie方法
            function getAllCookie(){
			var strcookie = document.cookie;//获取cookie字符串
			var arrcookie = strcookie.split("; ");//分割
			return arrcookie;
            }

            function incSupportAjax() {
            	
            	
            	var OUserid=document.getElementById('hidden_userid');
            	var temp=OUserid.value;
            	var userid=null;
            	if(temp==''){
            		window.location.href="${pageContext.request.contextPath}/jsp/login.jsp";
            	}else{
            			userid=parseInt(temp);
            			
            			if(!isNaN(userid)){
            				/* 
                    		重要：这里可以用this
    	                	*/
    	                	var OSpan=this.getElementsByTagName('span')[0];
    	                    var commentid=OSpan.getElementsByTagName('input')[0].value;
    	                    if(get_cookie(userid+'_'+commentid+'_'+hidden_topicid.value+'_')!=''){
    	                    	alert("您已经点过赞了！");
    	                    	return;
    	                    }
    	                  	//创建ajax对象
    	                    var xhr=new XMLHttpRequest();
    	                    //连接服务器
    	                    xhr.open("get","${pageContext.request.contextPath}/increaseSupportServlet.do?commentid="+commentid,true);
    	                    //发送
    	                    xhr.send();
    	                    //响应
    	                    xhr.onreadystatechange=function () {
    	                    	/* 
    	                    		重要：这里的this不是上面的this了
    	                    	*/
    	                        if(xhr.readyState===4){
    	                            if(xhr.status===200){
    	                            	var temp=xhr.responseText;
    	                                if(temp=='true'){
    	                                	var OEm=OSpan.getElementsByTagName('em')[0];
    	                                	var supportNum=parseInt(OEm.innerHTML);
    	                                	OEm.innerHTML=supportNum+1;

    	                                		OSpan.className='jieda-zan zanok';

    	                                	
    	                                	//设置本用户在本次对话中不可以对本评论再次点赞

    	                                	document.cookie=userid+'_'+commentid+'_'+hidden_topicid.value+'_='+'bygong';
    	                                }else if(temp=='false'){
    	                                	alert("呀！点赞失败了");
    	                                }else{
    	                                	window.location.href="${pageContext.request.contextPath}/jsp/login.jsp";
    	                                }
    	                            }
    	                        }
    	                    }
            			}else{
            				alert("呀！点赞出错了，换个浏览器试试吧");
            			}
            			
            	}
            	
            }
            function get_cookie(Name) {
         	   var search = Name + "="; //查询检索的值
         	   var returnvalue = ""; //返回值（重要：所以判断一个cookie是否存在，判断返回值是否为’’,不是判断返是否为null）
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
            
            
            //采纳jajx
            function caiNa(){
            	
                var AAccept=document.getElementsByClassName('jieda-accept');
                for(var i=0;i<AAccept.length;i++){
                    AAccept[i].onclick=acceptAjax;
                }
            }
            
            function acceptAjax() {
            	if(confirm('确定采纳吗？')==false){
            		return;
            	}
                //获取评论id
                var commentid=this.getElementsByTagName('input')[0].value;
                //创建ajax对象
                var xhr=new XMLHttpRequest();
                //连接服务器
                xhr.open("get","${pageContext.request.contextPath}/acceptServlet.do?commentid="+commentid+'&topicid='+hidden_topicid.value,true);
                //发送
                xhr.send();
                //响应
                xhr.onreadystatechange=function () {
                    if(xhr.readyState===4){
                        if(xhr.status===200){
                            var temp=xhr.responseText;
                            if(temp=='true'){
                                getComment();  //采纳后重新加载评论
                                //标题栏显示已采纳
                                var OChangeToCaiNa=document.getElementById('changeToCaiNa');
                                OChangeToCaiNa.innerHTML='已采纳';
                                OChangeToCaiNa.className='fly-tip-jie';
                            }else if(temp=='false'){
                                alert("呀！采纳失败了");
                            }else{
                                window.location.href="${pageContext.request.contextPath}/jsp/login.jsp";
                            }
                        }
                    }
                }
            }
          	
            //删除
            function deleteComment(){
                var ADelete=document.getElementsByClassName('delete_button');
                for(var i=0;i<ADelete.length;i++){
                	ADelete[i].onclick=deleteCommentAjax;
                }
            } 
            
            function deleteCommentAjax() {
            	if(confirm('确定删除吗？')==false){
            		return;
            	}
                //获取评论id
                var commentid=this.getElementsByTagName('input')[0].value;
                //创建ajax对象
                var xhr=new XMLHttpRequest();
                //连接服务器
                xhr.open("get","${pageContext.request.contextPath}/deleteCommentServlet.do?commentid="+commentid+'&topicid='+hidden_topicid.value,true);
                //发送
                xhr.send();
                //响应
                xhr.onreadystatechange=function () {
                    if(xhr.readyState===4){
                        if(xhr.status===200){
                            var temp=xhr.responseText;
                            if(temp=='true'){
                                //标题栏显示已采纳
                                var tempid='li_'+commentid;
                                var OLi=document.getElementById(tempid);
                                OLi.style.display='none';
                            }else if(temp=='false'){
                                alert("呀！删除失败了");
                            }else{
                                window.location.href="${pageContext.request.contextPath}/jsp/login.jsp";
                            }
                        }
                    }
                }
            }
            
            
            
          	//编辑评论点击动作
          	var scrollPosition=null;  //滚动条距离页面顶端的距离
            function editComment(){
                var AEdit=document.getElementsByClassName('edit_button');
                for(var i=0;i<AEdit.length;i++){
                	AEdit[i].onclick=editCommentAjax;
                }
            } 
            
            function editCommentAjax() {
                //获取评论id
                var commentid=this.getElementsByTagName('input')[0].value;
                //创建ajax对象
                var xhr=new XMLHttpRequest();
                //连接服务器
                xhr.open("get","${pageContext.request.contextPath}/editCommentServlet.do?commentid="+commentid,true);
                //发送
                xhr.send();
                //响应
                xhr.onreadystatechange=function () {
                    if(xhr.readyState===4){
                        if(xhr.status===200){
                        	//获取滚动条现在的位置
                        	scrollPosition=document.body.scrollTop;
                        	
                            var temp=xhr.responseText;
                            var L_content=document.getElementById('L_content');
                            L_content.value=temp;
                            
                            //下面这两个元素在最上面已经获取过了
                            comment_submit.style.display='none';
                            confirm_edit_comment.style.display='inline-block';
                            
                            //点击编辑后跳到这个锚点，锚点要设置在textarea附近
                            window.location.href="#jump_to_textarea";
                            
                            //将要编辑的评论的id存入隐藏域中
                            var hidden_edit_commentid=document.getElementById('hidden_edit_commentid');
                            hidden_edit_commentid.value=commentid;
                        }
                    }
                }
            }
            
          	//确认编辑评论
            function ConfirmEditComment(){  //此处有点多此一举，不过这样的话格式就和上面的函数一样了
          		confirm_edit_comment.onclick=ConfirmEditCommentAjax;
            } 
          	
            if("${requestScope.topicDetail.isEnd}"=='0'){
            	ConfirmEditComment();
			}
            
            function ConfirmEditCommentAjax() {
                //获取评论id
                var commentid=document.getElementById('hidden_edit_commentid').value;
                //创建ajax对象
               	var xhr = new XMLHttpRequest();
            	xhr.open("post","${pageContext.request.contextPath}/confirmEditCommentServlet.do",true);
            	// 如果 使用post发送数据 必须 设置 如下内容
                // 修改了 发送给 服务器的 请求报文的 内容
                // 如果需要像 HTML 表单那样 POST 数据，请使用 setRequestHeader() 来添加 HTTP 头。然后在 send() 方法中规定您希望发送的数据：
                xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
             	// 发送
                // post请求 发送的数据 写在 send方法中
                // 格式 name=jack&age=18 字符串的格式
                
                //重要：这里用的是util下的js中的方法，上面引入js了
                var newComment=transformLinkForAjaxPost(L_content.value);
                xhr.send('content='+newComment+'&topicid='+hidden_topicid.value+"&commentid="+commentid);
                //响应
                xhr.onreadystatechange=function () {
                    if(xhr.readyState===4){
                        if(xhr.status===200){
                            var temp=xhr.responseText;
                            
                            if(temp=='true'){
                                var tempid='li_'+commentid;
                                var OLi=document.getElementById(tempid);
                                var OP=OLi.getElementsByClassName('comment_content')[0];
                                OP.innerHTML=replaceAll(getFaceConfig(),replaceAll(getContentConfig(),L_content.value));
                                L_content.value='';
                                
                                //修改后将按钮换回
                                comment_submit.style.display='inline-block';
                                confirm_edit_comment.style.display='none';
                                
                                //将滚动条位置返回到被编辑的评论的位置
                                document.body.scrollTop = document.documentElement.scrollTop = scrollPosition; //js实现点击返回页面顶部
                                
                            }else if(temp=='false'){
                                alert("呀！编辑失败了");
                            }else{
                                window.location.href="${pageContext.request.contextPath}/jsp/login.jsp";
                            }
                        }
                    }
                }
            }
            
            //开始 回复的回复
            //给所有回复按钮添加动作
            function replyCommentClick() {
            	var AReplyComment=document.getElementsByClassName('reply_button');
            	for(var i=0;i<AReplyComment.length;i++){
            		AReplyComment[i].onOff=false;
            		AReplyComment[i].onclick=function(){
            			if(hidden_userid==''){
            				window.location.href="${pageContext.request.contextPath}/jsp/login.jsp";
            				return;
            			}
            			var textareaId='reply_'+this.getElementsByTagName('input')[0].value;
        				var OReplyCommentDiv=document.getElementById(textareaId);
            			if(this.onOff==false){
            				
            				//消除其他回复的回复框
            				for(var j=0;j<AReplyComment.length;j++){
            					var temp1='reply_'+AReplyComment[j].getElementsByTagName('input')[0].value;
            					AReplyComment[j].onOff=false;
            					var temp2=document.getElementById(temp1);
            					temp2.style.display='none';
            				} 
            				
            				OReplyCommentDiv.style.display='block';
            			}else{
            				OReplyCommentDiv.style.display='none';
            			}
            			this.onOff=!this.onOff;
            		}
            	}
            }
            
            //给确认提交回复的回复设置点击事件
            function submitReplyComment(){
            	var ASubmit_reply_comment=document.getElementsByClassName('submit_reply_comment');
            	for(var i=0;i<ASubmit_reply_comment.length;i++){
            		ASubmit_reply_comment[i].onclick=submitReplyCommentAjax;
            	}
            }
            
            //ajax提交回复的回复
            function submitReplyCommentAjax(){
            	var commentid=this.getElementsByTagName('input')[0].value;
    			var ODiv=document.getElementById('reply_'+commentid);
    			var content=ODiv.getElementsByTagName('textarea')[0].value;
    			if(content.trim()==''){ //输入为空，不能提交
    				alert('输入点内容吧！');
            		return;
            	}
            	var xhr = new XMLHttpRequest();
            	xhr.open("post","${pageContext.request.contextPath}/addCommentCommentServlet.do",true);
                xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xhr.send('content='+transformLinkForAjaxPost(content)+'&commentid='+commentid);
                xhr.onreadystatechange = function () {
                    /* if (ajax.readyState==4&&ajax.status==200) {  //网上的写法
                        console.log(ajax.responseText);
                    } */
                	if(xhr.readyState===4){
                        if(xhr.status===200){
                        	var temp=xhr.responseText;
    						if(temp=='true'){
    							ODiv.style.display='none';
    							var OLi=document.getElementById('li_'+commentid);
    							var replaysHomePath='${pageContext.request.contextPath}/home/'+hidden_userid; //评论的评论的用户home主页(replays就评论的评论的意思)
    							OLi.innerHTML+=
  									'<span style="line-height: 30px">'+
  				             			'<a style="text-decoration: none; color: #4f99cf" href="'+replaysHomePath+'">'+nickName+'</a>&nbsp;'+
  				             			'<span> 回复：</span>&nbsp;'+
  				             			'<span>'+replaceAll(getFaceConfig(),replaceAll(getContentConfig(),content))+'</span>'+
  				            		'</span><br/>';
  				            		
  				            		
  				            		//注，以为进行回复的li的innerHTML改变了，因为现在的js不知道，li的内容改变后，之前的点赞标签，删除标签还在不在，
  				            		//   所以需要重新获取这个li中的相关标签，然后再设置点击事件，下面的将页面的所有评论都从新设置了一遍，虽然有些多余，但是也只能这样做了
  				            		dianZan(); //点赞方法
  	        						caiNa();   //采纳方法
  	        						deleteComment(); //删除评论方法
  	        						editComment(); //编辑评论方法
  	        						replyCommentClick(); //回复的回复点击动作
  	        						submitReplyComment(); //提交回复的回复点击动作
    						}else{
    							window.location.href=temp;
    						}
                        }
                    }
                }
            }
            
            var hotReplyList=document.getElementById('hotReplyList');
            var top_none=document.getElementsByClassName('top_none');
  		    var temp_htl=hotReplyList.getElementsByTagName('dd');
	        if(temp_htl.length==0){
	       		top_none[0].style.display='block';
            }
           
        }
    </script>
    
</head>
<body>

		<input id="hidden_userid" type="hidden" value="${sessionScope.userInfo.id }">

		<!-- ajax评论模板 -->
		<script id="ajax_comment" type="text/html"> 
		{{ each list as CommentEx i}}
			{{if CommentEx.isTopic==0}}
				<a name="{{CommentEx.id}}"></a>
				<li id="li_{{CommentEx.id}}" data-id="13">
            <div class="detail-about detail-about-reply">
              <a class="jie-user" href="${pageContext.request.contextPath}/home/{{CommentEx.userId}}">
                <img src="{{CommentEx.headUrl}}" alt="">
                <cite>
                  <i>{{CommentEx.nickName}}</i>&nbsp;&nbsp;
                  <em style="font-size:8px;color:#4aca6d;width: 30px;height: 18px;border: 1px solid #4aca6d;border-radius: 10px;">&nbsp;{{CommentEx.grade}}&nbsp;&nbsp;&nbsp;</em>
                </cite>
              </a>
              <div class="detail-hits">
                <span>{{CommentEx.createTime}}</span>
              </div>
			  {{if CommentEx.isAccepted==1}}
					<i class="iconfont icon-caina" title="最佳答案"></i>
			  {{/if}}
            </div>
            <div class="detail-body jieda-body">
              <div class="comment_content">{{#CommentEx.content}}</div>
            </div>
            <div class="jieda-reply">

					<span class="supportCoat"><span class="jieda-zan"><i class="iconfont icon-zan"></i><em>{{CommentEx.supportNum}}</em><input value="{{CommentEx.id}}" type="hidden"/></span></span>
			  
			  <span class="reply_button"><i class="iconfont icon-svgmoban53"></i>回复<input type="hidden" value="{{CommentEx.id}}"/></span>
              <div class="jieda-admin">
				{{if CommentEx.showEdit}}
					<span class="edit_button">编辑<input type="hidden" value="{{CommentEx.id}}"/></span>
				{{/if}}
				{{if CommentEx.showDelete}}
					<span class="delete_button">删除<input type="hidden" value="{{CommentEx.id}}"/></span>
				{{/if}}
				{{if CommentEx.showAccept}}
					<span class="jieda-accept" >采纳<input type="hidden" value="{{CommentEx.id}}"/></span>
				{{/if}}
              </div>
            </div>
			<div style="height:6px"></div>
				<div style="display:none;" id="reply_{{CommentEx.id}}">
					<textarea  name="content" required lay-verify="required" placeholder="我的回复"  class="layui-textarea" style="height: 80px;"></textarea>
					<span class="submit_reply_comment layui-btn"  lay-submit style="display:inline-block; margin:10px 0" >回复<input type="hidden" value="{{CommentEx.id}}"/></span>
					<br/>
				</div>
          </li>
			{{/if}}
		{{ /each}}   
		</script>
		

<!-- 以下为各种排行榜 -->		
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
        <img src="${sessionScope.userInfo.headUrl}">
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
    <div class="content detail">
      
     <div class="fly-panel detail-box">   <!-- 开始 -->
        
        <!-- 标题div块！ -->
        <h1>${requestScope.topicDetail.title}</h1>
        <div class="fly-tip fly-detail-hint" data-id="{{rows.id}}">
          <c:if test="${requestScope.topicDetail.isTop==1}">
		  	<span class="fly-tip-stick">置顶帖</span>
          </c:if>
          <c:if test="${requestScope.topicDetail.isGood==1}">
          	<span class="fly-tip-jing">精帖</span>
          </c:if>
          <c:if test="${requestScope.topicDetail.isEnd==0}">
          	<span >未结贴</span>
          </c:if>
          <c:if test="${requestScope.topicDetail.isEnd==1}">
          	<span class="fly-tip-jing">已结贴</span>
          </c:if>
          <c:if test="${requestScope.isAccepted==false}">
          	<span id="changeToCaiNa" class="weicaina">未采纳</span>
          </c:if>
          <c:if test="${requestScope.isAccepted==true}">
          	<span class="fly-tip-jie">已采纳</span>
          </c:if>
          
          <!-- <span class="jie-admin" type="del" style="margin-left: 20px;">删除</span>
          <span class="jie-admin" type="set" field="stick" rank="1">置顶</span> 
          <span class="jie-admin" type="set" field="stick" rank="0" style="background-color:#ccc;">取消置顶</span>
          <span class="jie-admin" type="set" field="status" rank="1">加精</span> 
          <span class="jie-admin" type="set" field="status" rank="0" style="background-color:#ccc;">取消加精</span> -->
          
          <div class="fly-list-hint"> 
            <i class="iconfont" title="回答">&#xe60c;</i> ${requestScope.topicDetail.commentCount}
            <i class="iconfont" title="人气">&#xe60b;</i> ${requestScope.topicDetail.viewCount}
          </div>
        </div>
        
        <!-- 用户信息div块 -->
        <div class="detail-about">
          <a class="jie-user" href="${pageContext.request.contextPath}/home/${requestScope.topicDetail.userId}">
            <img src="${requestScope.topicDetail.headUrl}" alt="">
            <cite>
              ${requestScope.topicDetail.nickName }
              <em>${requestScope.topicDetail.createTime }</em>
            </cite>
          </a>
          <div class="detail-hits" data-id="{{rows.id}}">
            <span style="color:#FF7200">悬赏：${requestScope.topicDetail.rewardKiss}飞吻</span>
            <c:if test="${requestScope.topicDetail.userId==sessionScope.userInfo.id}">
            	<span class="layui-btn layui-btn-mini jie-admin" type="edit"><a href="${pageContext.request.contextPath }/editTopicServlet.do?topicid=${requestScope.topicDetail.id}">编辑此贴</a></span>
            </c:if>
              

            
            <c:if test="${requestScope.ifCollect==false || sessionScope.userInfo==null}"> <!-- 如果用户没收藏过 -->
            	<span id="collect_button" class="layui-btn layui-btn-mini jie-admin " data-type="add">收藏</span>
            	<span style="display:none" id="cancle_collect_button" class="layui-btn layui-btn-mini jie-admin  layui-btn-danger"  data-type="add">取消收藏</span>
            </c:if>
            <c:if test="${requestScope.ifCollect==true}"> <!-- 如果用户收藏过 -->
            	<span style="display:none" id="collect_button" class="layui-btn layui-btn-mini jie-admin " data-type="add">收藏</span>
            	<span id="cancle_collect_button" class="layui-btn layui-btn-mini jie-admin  layui-btn-danger"  data-type="add">取消收藏</span>
            </c:if>

            <!-- 去掉其中的type就不会弹出请求异常 -->
            <!-- <span id="collect_button" class="layui-btn layui-btn-mini jie-admin " type="collect" data-type="add">收藏</span>
            <span id="cancle_collect_button" class="layui-btn layui-btn-mini jie-admin  layui-btn-danger" type="collect" data-type="add">取消收藏</span> -->
          
          </div>
        </div>
        
        <!-- 评论内容div块 -->
        <div id="main_content" class="detail-body photos" style="margin-bottom: 20px;">${requestScope.topicDetail.content}</div>
        
        
    </div>   <!-- 结束 -->
        

      <div class="fly-panel detail-box" style="padding-top: 0;">
        <a name="comment"></a>
        <ul class="jieda photos" id="jieda">
          <%-- <li data-id="12" class="jieda-daan">
            <a name="item-121212121212"></a>
            <div class="detail-about detail-about-reply">
              <a class="jie-user" href="">
                <img src="${pageContext.request.contextPath}/res/images/avatar/default.png" alt="">
                <cite>
                  <i>纸飞机</i>
                  <!-- <em>(楼主)</em>
                  <em style="color:#5FB878">(管理员)</em>
                  <em style="color:#FF9E3F">（活雷锋）</em>
                  <em style="color:#999">（该号已被封）</em> -->
                </cite>
              </a>
              <div class="detail-hits">
                <span>3分钟前</span>
              </div>
              <i class="iconfont icon-caina" title="最佳答案"></i>
            </div>
            <div class="detail-body jieda-body">
              <p>么么哒</p>
            </div>
            <div class="jieda-reply">
              <span class="jieda-zan zanok" type="zan"><i class="iconfont icon-zan"></i><em>12</em></span>
              <span type="reply"><i class="iconfont icon-svgmoban53"></i>回复</span>
              <!-- <div class="jieda-admin">
                <span type="edit">编辑</span>
                <span type="del">删除</span>
                <span class="jieda-accept" type="accept">采纳</span>
              </div> -->
            </div>
          </li> --%>
          
         <%--  <li data-id="13">
            <a name="item-121212121212"></a>
            <div class="detail-about detail-about-reply">
              <a class="jie-user" href="">
                <img src="${pageContext.request.contextPath}/res/images/avatar/default.png" alt="">
                <cite>
                  <i>香菇</i>
                  <em style="color:#FF9E3F">活雷锋</em>
                </cite>
              </a>
              <div class="detail-hits">
                <span>刚刚</span>
              </div>
            </div>
            <div class="detail-body jieda-body">
              <p>蓝瘦</p>
            </div>
            <div class="jieda-reply">
              <span class="jieda-zan" type="zan"><i class="iconfont icon-zan"></i><em>0</em></span>
              <span type="reply"><i class="iconfont icon-svgmoban53"></i>回复</span>
              <div class="jieda-admin">
                <span type="edit">编辑</span>
                <span type="del">删除</span>
                <span class="jieda-accept" type="accept">采纳</span>
              </div>
            </div>
          </li> --%>
          <%-- <c:forEach items="${requestScope.commentEx}" var="comm">
          		<li data-id="13">
            <a name="item-121212121212"></a>
            <div class="detail-about detail-about-reply">
              <a class="jie-user" href="">
                <img src="${comm.headUrl}" alt="">
                <cite>
                  <i>${comm.nickName}</i>
                  <em style="color:#FF9E3F">初学者</em>
                </cite>
              </a>
              <div class="detail-hits">
                <span>${comm.createTime}</span>
              </div>
            </div>
            <div class="detail-body jieda-body">
              <p>${comm.content}</p>
            </div>
            <div class="jieda-reply">
              <span class="jieda-zan" type="zan"><i class="iconfont icon-zan"></i><em>0</em></span>
              <span type="reply"><i class="iconfont icon-svgmoban53"></i>回复</span>
              <div class="jieda-admin">
                <span type="edit">编辑</span>
                <span type="del">删除</span>
                <span class="jieda-accept" type="accept">采纳</span>
              </div>
            </div>
          </li>
          </c:forEach> --%>
          
          <!-- <li class="fly-none">没有任何回答</li> -->
        </ul>
        
        <!-- 标记：发表评论再此 -->
        
        <div class="layui-form layui-form-pane">
          <form action="${pageContext.request.contextPath}/addComment.do" method="post">
            <div class="layui-form-item layui-form-text">
              <div class="layui-input-block">
              <a name="jump_to_textarea"></a>
              <c:if test="${requestScope.topicDetail.isEnd==1}">
          	  	<textarea readonly disabled id="L_content" name="content" required lay-verify="required" placeholder="已结贴！"  class="layui-textarea fly-editor" style="height: 150px;"></textarea>
          	  </c:if>
          	  <c:if test="${requestScope.topicDetail.isEnd==0}">
          	  	<textarea  id="L_content" name="content" required lay-verify="required" placeholder="我要回答"  class="layui-textarea fly-editor" style="height: 150px;"></textarea>
          	  </c:if>
              </div>
            </div>
            <div class="layui-form-item">
              <input type="hidden" name="jid" value="{{rows.id}}">
              
              <!-- 添加一个隐藏域来存储帖子id -->
              <input id="hidden_topicid" type="hidden" name="topicId" value="${requestScope.topicDetail.id}"/>
              
              <!-- <button class="layui-btn" lay-filter="*" lay-submit>提交回答</button> -->
              
              <c:if test="${requestScope.topicDetail.isEnd==0}">
          	  	<span id="comment_submit"  class="layui-btn"  lay-submit >提交回答</span>
          	  	<span style="display:none;" id="confirm_edit_comment" class="layui-btn" lay-submit>确认修改</span>
          	  	<input id="hidden_edit_commentid" type="hidden" value="" />
          	  </c:if>
          	  <c:if test="${requestScope.topicDetail.isEnd==1}">
          	  	<span class="layui-btn" style="background-color:grey; cursor:default;" >已结贴</span>
          	  	<input id="comment_submit" type="hidden"/>
          	  </c:if>
              
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
  
  <div class="edge">
    
    <dl id="hotViewList" class="fly-panel fly-list-one"> 
      <dt  class="fly-panel-title">近期热帖</dt>


    </dl>
    
    <dl id="hotReplyList" class="fly-panel fly-list-one"> 
      <dt class="fly-panel-title">近期热议</dt>
	  <p class="top_none" style="display:none; color:#a9b7b7; text-align:center; position:relative; top:5px" >暂无</p>
    </dl>
    
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
  <%-- <script src="${pageContext.request.contextPath}/res/js/jquery-2.1.4.min.js"></script>
  <script src="${pageContext.request.contextPath}/res/mods/index.js"></script> --%>
<script>
layui.cache.page = 'jie';
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
}).use('fly', function(){
  var fly = layui.fly;
  
  //如果你是采用模版自带的编辑器，你需要开启以下语句来解析。
  /* $('.detail-body').each(function(){
    var othis = $(this), html = othis.html();
    othis.html(fly.content(html));
  }); */

});
</script>

</body>
</html>