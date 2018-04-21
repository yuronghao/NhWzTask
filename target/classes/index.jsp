<!DOCTYPE html >
<%@page import="com.emi.sys.util.SysPropertites"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/jsp/common/header.jsp"%>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>一米移动ERP</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index,follow" />
	<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath() %>/emi.ico"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/canclose.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/common.css">
	<script type="text/javascript" src="<%=contextPath %>/scripts/websjy.js"></script>
	<script type="text/javascript">
	</script>
	<!--自动调整宽度\高度-->
	<script type="text/javascript">
		function getHeight(){
			var valueH=document.body.clientHeight;
			var mainH=valueH-0;
			var cH=valueH-113;
			//alert(cH);
			$('#mainContent').attr("style","height:"+mainH+"px");
			$('#rightMain').attr("style","height:"+cH+"px");
		}
		$(function(){				
			window.onresize = function winResize() {					
				if( $('#mainBody').width()<1204  ){
					$('#mainContainer').attr("style","min-width:1204px;");
				}else{
					$('#mainContainer').removeAttr("style");
				}
			};
			getHeight();
		});	
		
 					//frame 展开收藏	
		$(function(){
			
			var rh=$('body').height();
			$('#openClose').attr('style','top:'+(rh/2)+"px");
			$('#openClose').addClass('open');
			
			$('#openClose').click(function(){
				if($('#leftFrame').width()>0 ){
					$('#leftFrame').attr('style','width:0%;height:100%;display: none;');
					$('#rightFrame').attr('style','width:100%;height:100%');
					
					$('#openClose').removeClass('open');
					$('#openClose').addClass('close');
				}else if($('#leftFrame').width()==0){
					$('#leftFrame').attr('style','width:15%;height:100%;display: block;');
					$('#rightFrame').attr('style','width:85%;height:100%');
					
					$('#openClose').removeClass('close');
					$('#openClose').addClass('open');
				}				
			});
			
		
		});
		
		$(function(){
			var eh=$('body').height();
			$('#div_pannel').attr('style','height:'+(eh-110)+"px");
		})
		
		$(function(){
			var wh=$('#leftFrame').width();
			$('#nav').attr('style','width:'+wh+"px");
			$('#nav').addClass('whone')
		})
	 
		
		
		$(function(){
			$(".EMmessagedown").mouseover(function(){
				$(".EMmessage").slideDown(400);
				$(this).addClass("messgb");
			})
			
		})
		$(function(){
			$(".EMmessagedown").mouseleave(function(){
				$(".EMmessage").slideUp(300);
				$(this).removeClass("messgb");
			})
		})
	
	</script>
	<script>
	/*	$(function(){
		  $('.changeimg').toggle(function() {
		    $(this).addClass('EMshoucang');
		  }, function() {
		     $(this).removeClass('EMshoucang');
		  });
		   
		})*/
	
		 
	//添加到收藏
	function addFav(rightId,rightName,rightUrl,rightCode){
		$.ajax({
			data:{rightId:rightId,rightName:rightName},
			type: 'POST',
			url:'${ctx}/right_addToMyFavorite.emi',
			dataType:'text',
			success: function (mes){
				var obj = eval("("+mes+")");
				if(obj.success=='0'){
					$.dialog.alert("收藏失败");
				}
				if(obj.success=='1'){
					var favId = obj.favId;
					$.dialog({
						title: '消息',
						time: 1,
					    content: '收藏成功'
					});
					if (rightUrl.indexOf("?") > 0) { 
						rightUrl += "&menuCode="+rightCode;
					}else{
						rightUrl += "?menuCode="+rightCode;
					}
					/* var txt = '<li id="li_'+favId+'" onmouseover="showOpt(\''+favId+'\')"  onmouseout="hideOpt(\''+favId+'\')">';
						txt += '<span id="opt_'+favId+'" style="float: right;margin-top: 10px;display: none">';
						txt += '	<img style="cursor: pointer;" alt="" title="修改名称" src="'+ctx+'/images/common/edit.png" onclick="editFav(\''+favId+'\')">';
						txt += '	<img style="cursor: pointer;" alt="" title="删除" src="'+ctx+'/images/common/erase.png" onclick="deleteFav(\''+favId+'\')">';
						txt += '</span>';
						txt += '<a id="a_'+favId+'" href="'+ctx+rightUrl+'"  target="rightMain">'+rightName+'</a>';
						txt += '<input type="text" id="text_'+favId+'" style="display: none" maxlength="10" value="'+rightName+'" onblur="saveFav(\''+favId+'\');">';
						txt += '</li>'; 
						$('#menu_ul2').append(txt);*/
					var txt = '<li><a href="javaScript:void(0);" onclick="clickFav(\''+rightUrl+'\',\''+rightName+'\');">'+rightName+'</a> </li>';
					$('#scmenu').append(txt);
				}
			}
		});
	}
	//删除收藏的菜单
	function deleteFav(favId){
		if(confirm("确定删除?")){
			var _div = document.getElementById('li_'+favId);
			var content = document.getElementById('menu_ul2');
			content.removeChild(_div);
			//异步删除
			jQuery.ajax({
				data: {gid:favId},
				type: 'POST',
				url: '${ctx}/right_deleteFavorite.emi',
				success: function(req){
					//刷新页面
					//location.reload();
				}
			});
			
		}
	}
	
	//子菜单
	function childMenu(c_menu,level){
		var html="";
		if(level>3){
			return;//目前不支持超过3级菜单
		}
		for(var i=0;i<c_menu.length;i++){
			var mItem = c_menu[i];
			if(level==2){
				html += '<h2 onClick="javascript:ShowMenu(this,'+i+')">';
			}else if(level==3){
				html += '<li class="fl EMshoucang changeimg" onclick="addFav(\''+mItem.gid+'\',\''+mItem.rightName+'\',\''+mItem.rightUrl+'\',\''+mItem.rightCode+'\')"></li> ';
			}
			
			if(mItem.childNodes && mItem.childNodes.length>0){
				//有字节的菜单
				html += '<a href="javascript:void(0)">&nbsp;&nbsp;+ '+mItem.rightName +'</a></h1>';
				html += '<ul class="no">';
				html += 	childMenu(mItem.childNodes,level+1);
				html += '</ul>';
			}else{
				//没有子节点的菜单
				html += '<a href="javascript:void(0)" onclick="CreateDiv(\''+mItem.rightName+'\',\'${ctx}/'+mItem.rightUrl+'\',\''+mItem.rightName+'\')"/> ';
				if(level<3){html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';} 
				html += mItem.rightName +'</a></h2>';
				if(level<3){html += '<ul class="no"></ul>';}
			}
			//html += '</li>';
		}
		return html;
	}
	
	$(function(){
		//var menu = [{rightUrl:'/login.jsp',rightName:'控制中心',childNodes:[{rightUrl:'/login1.jsp',rightName:'用户管理',childNodes:[{rightUrl:'/login1.jsp',rightName:'用户管理',childNodes:[]}]}]}];
		//初始化菜单
		var menu = ${rights};
		var html = "";
		for(var i=0;i<menu.length;i++){
			var mItem = menu[i];
			//<h1 onClick="javascript:ShowMenu(this,${stat.index})">
		 	//<a href="javascript:void(0)"><img src="${ctx}/${right.imgUrl }" />&nbsp;&nbsp;${right.rightName }</a></h1>
			html += '<h1 onClick="javascript:ShowMenu(this,'+i+')">';
			if(mItem.childNodes && mItem.childNodes.length>0){
				//有字节的菜单
				html += '<a href="javascript:void(0)"><img src="${ctx}/'+mItem.imgUrl+'" />&nbsp;&nbsp;'+mItem.rightName +'</a></h1>';
				html += '<span class="no">';
				html += 	childMenu(mItem.childNodes,2);
				html += '</span>';
			}else{
				//没有字节点的菜单
				html += '<a href="javascript:void(0)"><img src="${ctx}/'+mItem.imgUrl+'" onclick="CreateDiv(\''+mItem.rightName+'\',\'${ctx}/'+mItem.rightUrl+'\',\''+mItem.rightName+'\')"/>&nbsp;&nbsp;'+mItem.rightName +'</a></h1>';
				html += '<span class="no"></span>';
			}
			//html += '</li>';
		}
		document.getElementById('menuul').innerHTML=html;
		
		
		/* $('.changeimg').click(function(){
			$(this).toggleClass('EMshoucang');	
			alert("收藏成功");
		}) */ 
	})
	
	function clickFav(rightUrl,rightName){
		//创建tab
		CreateDiv(rightName,'${ctx}/'+rightUrl,rightName);
	}
	
	//退出系统
	function exit(){
		parent.window.location.href="${ctx }/login_logout.emi";
	}
	
	//修改密码
	function modifyPassword(){
		var pwdWin = $.dialog({ 
			drag: false,
			lock: true,
			resize: false,
			title:'修改密码',
		    width: '400px',
			height: '180px',
			content: 'url:login_toModifyPassword.emi',
			ok:function(){
				var oldPwd = this.content.document.getElementById('oldPassword').value;
				var newPwd = this.content.document.getElementById('newPassword').value;
				var confirmPwd = this.content.document.getElementById('confirmPassword').value;
				if(oldPwd==''){
					$.dialog.alert("请填写原密码");
				}else if(newPwd==''){
					$.dialog.alert("请填写新密码");
				}else if(confirmPwd!=newPwd){
					$.dialog.alert("新密码与确认密码不一致");
				}
				$.ajax({
					type: 'POST',
					url:'${ctx}/login_modifyPassword.emi',
					data:{oldPassword:oldPwd,newPassword:newPwd},
					dataType:'text',
					success: function (mes){
						var data = eval("("+mes+")");
						if(data.success=='0'){
							$.dialog.alert("修改失败");
							return false;
						}
						if(data.success=='1'){
							$.dialog.alert("修改成功,请重新登录",function(){parent.window.location.href="${ctx}/login_logout.emi";});
							return true;
						}
						if(data.success=='2'){
							$.dialog.alert("原密码错误");
							return false;
						}
					}
				});
				
			},
			cancel:true
		});	
	}
	</script>
	<style>
		/*body{
			background-image: url(img/bj.jpg);
			background-size: 100% 100%;
		}
		 */
		.whone{
			/*background-color: #6699c8;border-top: 1px solid #5e8db8;*/
		}
		#openClose {
		  position: absolute;
		  width: 10px;
		  height: 74px;
		  z-index: 99; 				 
		}
		.close {
		  background: url(${ctx}/img/mainToRight.png) no-repeat;		 	   
		}
		.open {
		  background: url(${ctx}/img/mainToLeft.png) no-repeat;
		   
		}
	</style> 
</head>
<body id="mainbody">
	<div id="mainContainer">		
		<!--整体部分-->
		<div id="mainContent" >
			<!--菜单栏-->
			<div class="fl leftmenu" id="leftFrame">
				<!--<iframe src="EMleft.html" frameborder="0" id="leftMian" name="leftMain"  width="100%" height="100%"></iframe>-->
				<div class="leftcontent" style="overflow: auto;">
					<!--logo-->
					<div class="EMlogo">
						<img src="${ctx }/img/EMlogo.png" />
					</div>
					<!--消息部分-->
					<div class="EMinfor">
						<ul>
							<li class="fl EMinfor_lione">
								<a href="#">
									<ul>
										<li class="EMinfor_word">未完成</li>
										<li class="EMinfor_word">0</li>
									</ul>
								</a>
							</li>
							<li class="fl EMinfor_litwo">
								<a href="#">
									<ul>
										<li class="EMinfor_word">未接收</li>
										<li class="EMinfor_word">0</li>
									</ul>
								</a>
							</li>
							<li class="fl EMinfor_lithree">
								<a href="#">
									<ul>
										<li class="EMinfor_word">已发布</li>
										<li class="EMinfor_word">0</li>
									</ul>
								</a>
							</li>
							<li class="fl EMinfor_lifour">
								<a href="#">
									<ul>
										<li class="EMinfor_word">已完成</li>
										<li class="EMinfor_word">0</li>
									</ul>
								</a>
							</li>
						</ul>
					</div>
					 <!--<div class="closemenu"> 12</div>-->
					<!--导航菜单-->
					<div id="swswsw">
						<div id="menu" >
						<!--tag标题-->
					    <ul id="nav" style="">
					        <li class="fl" style="width: 50%;margin-bottom: 4px;height: 30px;line-height: 20px;color: #FFFFFF;"><a href="#" class="selected1 gf_tag_a " >全部</a></li>
					        <li class="fl" style="width: 50%;margin-bottom: 4px;height: 30px;line-height: 20px; #FFFFFF;"><a href="#" class="gf_tag_a ">收藏&nbsp;&nbsp;<span style="margin-bottom:10px;padding: 2px;background-color: red;border-radius: 20px;">${favoritesSize }</span></a></li>
					       
					         
					        <div style="clear:both;"></div>
					    </ul> 
					   
						<div id="menu_con" >
					    	<!-- one -->
					        <div class="gf_tag" style="display:block">
								<!--菜单开始-->
								<div id="menuul" style="margin-bottom: 30px;">
								
								</div>
								<!--菜单开始 end-->
								 
						 
					        </div> 
					        
							<!-- one end-->
							<!--two -->
					        <div class="gf_tag" style="display:none;">
								<ul id="scmenu" class="scmenu">
									<c:forEach var="fav" items="${favorites }" varStatus="stat">
										<li><a href="javaScript:void(0);" onclick="clickFav('${fav.rightUrl}','${fav.menuName}');">${fav.menuName}</a> </li>
									</c:forEach>
								</ul>
					        </div> 
					        <!--two end-->
					        
				    		 
						</div>
					</div>
					</div>
					</div>
				</div>
			<!---->
		 
			<div class="fl" id="rightFrame" style="min-width:1000px;">
				 <div class="navbar">
					 <ul class="EMtime">
					 	<li class="fl EMtimeli"><img src="${ctx }/img/logintime.png">&nbsp;&nbsp;登录时间：${sessionScope.LoginTime }</li>
					 	<li class="fl EMtimeli"><img src="${ctx }/img/Ezt.png">&nbsp;&nbsp;账套：${sessionScope.SobName }</li>
					 	<li class="fl EMtimeli"><img src="${ctx }/img/zz.png">&nbsp;&nbsp;组织：${sessionScope.OrgName }</li>
					 	<li class="fr EMtimeli EMmessagedown"><img src="img/Euser.png">&nbsp;&nbsp;<a href="#">Hi,${sessionScope.Person.pername }<c:if test="${sessionScope.isAdmin==1 }">(超级管理员)</c:if> <span><img src="${ctx }/img/down.png"> </span></a>
					 		<ul class="EMmessage">
					 			<li class="downimg"></li>	
					 			<li>
					 				<ul>
					 					<li><a href="#" onclick="modifyPassword();"><img src="${ctx }/img/key.png" />&nbsp;&nbsp;密码修改</a></li>
					 					<li><a href="#" onclick="exit();"><img src="${ctx }/img/out.png" />&nbsp;&nbsp;退出登录</a></li>	
					 				</ul>
					 			</li>
					 		</ul>
					 	</li>
					 </ul>
				</div>
				<!--可关闭-->
				 
				<ul class="clearfix EMclosetab" id="div_tab" >
					  <li class="crent" id="首页"><span><a href="javascript:;" onclick="javascript:CreateDiv('首页','http://192.168.2.122:8020/NewERP/yd.html','首页')" title="首页" class="menua">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首页</a><a></a></span></li>
				</ul>
				<!--可关闭 end-->
				 
				<div id="openClose" ></div>		
				<!--<iframe src="index.html" framemagin="0" frameborder="0" id="rightMain" name="rightMain" width="100%" height="100%"></iframe>-->
				<div id="div_pannel" >
					<iframe id="div_首页" src="http://192.168.2.122:8020/NewERP/yd.html" height="100%" frameborder="0" width="100%" style="display: block;"></iframe>
				</div>
			</div>
			<div class="cf"></div>
		</div>		 
	</div>
</body>
<script>	
	/*tab切换*/
		$(function(){
			var i=0;
			var j=0;
		$(".gf_tag").each(function(e){
			
			$(this).attr("id","gf_tag_"+i)
			i++;
		})
		$(".gf_tag_a").each(function(e){
			var i=0;
			$(this).attr("id","gf_tag_a_"+j)
			j++;
		})

		$(".gf_tag_a").click(function(){
			var tagid=$(this).attr("id");
			var divids=tagid.split("_");
			var divid="gf_tag_"+divids[divids.length-1];
			//先给所有的赋值为隐藏
			$(".gf_tag").hide()
			//点击的展示
			$("#"+divid).show();
			//给样式
			/*$(".gf_tag_a").attr("class","gf_tag_a");
			$(this).attr("class","selected1 gf_tag_a");*/
			$(".gf_tag_a").removeClass("selected1");
			$(this).addClass("selected1");
		})
	    })	
	    
 
	</script>
	<script language="JavaScript">
 
	function ShowMenu(obj,n){
		 var Nav = obj.parentNode;
		 if(!Nav.id){
		  var BName = Nav.getElementsByTagName("ul");
		  var HName = Nav.getElementsByTagName("h2");
		  var t = 2;
		 }else{
		  var BName = document.getElementById(Nav.id).getElementsByTagName("span");
		  var HName = document.getElementById(Nav.id).getElementsByTagName("h1");
		  var t = 1;
		 }
		 for(var i=0; i<HName.length;i++){
		  HName[i].innerHTML = HName[i].innerHTML.replace("-","+");
		  HName[i].className = "";
		 }
		 obj.className = "h" + t;
		 for(var i=0; i<BName.length; i++){if(i!=n){BName[i].className = "no";}}
		 if(BName[n].className == "no"){
		  BName[n].className = "";
		  obj.innerHTML = obj.innerHTML.replace("+","-");
		 }else{
		  BName[n].className = "no";
		  obj.className = "";
		  obj.innerHTML = obj.innerHTML.replace("-","+");
		 }
	}
		 
 
 
$(".changeimg").click(function(){
    $(".changeimg>img").toggle();
});
</script>
</html>