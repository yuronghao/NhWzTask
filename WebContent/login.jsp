<!DOCTYPE html >
<%@page import="com.emi.common.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %> 



<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>一米移动门ERP</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="shortcut icon" type="image/x-icon" href="${ctx }/emi.ico"/>
	<link rel="stylesheet" href="${ctx }/css/common.css" />
    <link rel="stylesheet" href="${ctx }/css/common/login.css" />
	
      
</head>
<script type="text/javascript">
/*$(function() { 
	 var WsShell = new ActiveXObject('WScript.Shell') ;
	 WsShell.SendKeys('{F11}');  

});*/
</script>
<body onload="getcookie()" onkeydown="loginSubmit(event);">
	<font size="10" color="red" face="verdana"><a href="${ctx}/tplus/wareHouse_tosystemsetting.action">请点======>参数配置</a></font>
	
</body>
<script type="text/javascript">
function logincheck(){
$.ajax({
	  data: $("#loginform").serialize(),
	  type: 'POST',
	  url: '${ctx}/login_checkLogin.emi',
	  success: function(req){
	  if(req=='login'){
		//var sysName = document.getElementById("sysName").value;	  
		//window.location.href="${ctx}/login_toIndex.emi?sysName="+sysName;	 
		document.forms[0].submit();
		  }else{ alert(req);}
		 
	  }
	});

}
function getcookie(){
	$.ajax({
		  type: 'POST',
		  url: '${ctx}/login_getcookie.emi',
		  success: function(req){
			 var data = eval("(" + req+ ")"); 
			 if(data.username!=null){
				 $("#userName").val(data.username);
				 
			 }
			 if(data.password!=null){
				 $("#password").val(data.password);
				 $("#pswcheck").attr("checked",true);
			 }
			 
		  }
		});

}

// 监听键盘事件
function loginSubmit(e){
	var key; 
	if(document.all){ 
		key = window.event.keyCode; 
	}else{ 
		key = e.which;
	}	
	if(key == "13"){
		$("#loginBtn").click(); 
	}
}
</script>
</html>