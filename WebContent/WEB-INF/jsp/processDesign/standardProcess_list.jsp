<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!-- 这个页面lhgdialog需要在整个窗口弹出，设置如下 -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<c:set var="lhg_self" value="false"/>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>创建新企业</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index,follow" />

	<link rel="stylesheet" href="<%=contextPath %>/css/common.css" />
	<link rel="stylesheet" href="<%=contextPath %>/css/emicom.css" />
	<script type="text/javascript" src="<%=contextPath %>/scripts/lhgdialog/lhgdialog.js"></script>
	 <!--设置content宽度-->
	<script type="text/javascript">
	$(function() {

		var width = $('#createdTable').width();
	
		$('#content').css('width', width+10+"px");

	});
	//点击列表功能，弹出对话框
	$(function(){
		$('tr > td.toolTd').click(function(){
			$(this).children().show();
			$('.tools').children().mouseover(function(){
				$(this).addClass('liEffect');
			});

			$('.tools').children().mouseleave(function(){
					$(this).removeClass('liEffect');
			});		
		});
		
		$('tr > td.toolTd').mouseleave(function(){
			$(this).children('.tools').hide();
		});				

	});

	//新增
	function addRoute(){
		location.href = "${ctx}/wms/basepd_toEditBaseRoute.emi";
	}
	//编辑基本信息
	function editRoute(){
		var routeId = checkSelectId('userCheck');
		if(routeId!=''){
			location.href = "${ctx}/wms/basepd_toEditBaseRoute.emi?routeId="+routeId;
		}
	}
	
	//设计
	function designRoute(){
		var routeId = checkSelectId('userCheck');
		if(routeId!=''){
			var maxWidth = window.screen.width-40;//document.body.clientWidth;
			var maxHeight = window.screen.height-200;//document.body.clientHeight;
			$.dialog({ 
				drag: true,
				lock: false,
				resize: true,
				title:'标准工艺路线设计',
			    width: maxWidth+'px',
				height: maxHeight+'px',
				content: 'url:${ctx}/wms/basepd_toDesignBaseRoutePage.emi?routeId='+routeId,
				okVal:"关闭",
				ok:function(){
					//document.getElementById('comment').value = this.content.document.getElementById('dia_comment').value;
				},
				//cancelVal:"关闭",
				//cancel:true
			});	
		}
	}
	
	//删除
	function deleteRoute(){
		var gid = checkSelectId('userCheck',true);
		if (gid!=''){
			$.dialog.confirm(tip_confirmDelete,function(){
				$.ajax({
					data: $("#myform").serialize(),
					type: 'POST',
					url: "${ctx}/wms/basepd_deleteBaseRoute.emi?routeId="+gid,
					success: function(req){
						if(req=='success'){
							$.dialog.alert_s('删除成功',function(){
								location.href="${ctx}/wms/basepd_toBaseRouteList.emi";
							});
							
						}else if(req=='error'){
							$.dialog.alert_e('删除失败');
						}
					},
					error:function(){
						$.dialog.alert_e('error');
					}
				});
			});
		}
	}
	
	//搜索
	function searchRoute(){
		document.forms[0].submit();
	}
	
	//checkbox点击事件
	function clickCheck(){
		var allchecked = true;
		var uck = document.getElementsByName('userCheck');
		var allck = document.getElementById('all');
		
		for(var i=0;i<uck.length;i++){
			if(!uck[i].checked){
				allchecked = false;
			}
		}
		if(allchecked){
			allck.checked=true;
		}else{
			allck.checked=false;
		}
		
	}
	
	//全选按钮
	function selectAll(){
		var ckAll = document.getElementById('all');
		var isck = ckAll.checked;
		var uck = document.getElementsByName('userCheck');
		for(var i=0;i<uck.length;i++){
			uck[i].checked=isck;
		}
	}
</script>

</head>
<body>
	<form action="${ctx}/wms/basepd_toBaseRouteList.emi" method="post" >
	
	<div class="EMonecontent">
		<!--按钮栏-->
		<div class="toolBar">
		
			<ul>
	 			<li class="fl"><input class="editBtn" type="button" value="设计" onclick="designRoute()"></li>
	 			<li class="fl">
						&nbsp;&nbsp;
						<input class="addBtn" type="button" value="新增" onclick="addRoute()">
						<input class="editBtn" type="button" value="修改" onclick="editRoute()">
						<input class="delBtn" type="button" value="删除" onclick="deleteRoute()">
						&nbsp;&nbsp;
				</li>
	 			<li class="fl">
	 				<input style="margin-left: 100px" type="text" id="keyWord" name="keyWord" >
	 				<input type="button" class="searchBtn" placeholder="编码或名称" value="搜索" onclick="searchRoute();">
	 			</li>
	 			<div class="cf"></div>
	 		</ul>
		</div>
		<!--内容部分-->
		<div class="creattable">
	 		<div class="tabletitle">标准工艺路线列表</div>		 		
	 		<div class="createdList">
			<table>
				<tbody>
					<tr>
						<th width="5%" style="text-align: center;"><input type="checkbox" id="all" onclick="selectAll()"></th>
						<th width="5%" style="text-align: center;">序号</th>
						<th width="20%" style="text-align: center;">编码</th>
						<th width="20%" style="text-align: center;">名称</th>
						<th width="30%" style="text-align: center;">描述</th>
						<th width="20%" style="text-align: center;">创建时间</th>
					</tr>
					<c:forEach var="flow" items="${data.list }" varStatus="stat">
						<tr>
							<td><input type="checkbox" name="userCheck" value="${flow.gid }" onclick="clickCheck();"></td>
							<td>${stat.count+(data.pageIndex-1)*(data.pageSize) }</td>
							<td nowrap class="longText" title="${flow.routcode }">${flow.routcode }</td>
							<td nowrap class="longText" title="${flow.routname }">${flow.routname }</td>
							<td nowrap class="longText" title="${flow.routdes }">${flow.routdes }</td>
							<td nowrap class="longText" title="${flow.effdate }">${fn:substring(flow.effdate,0,19) }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	<!-- ---------------------分页开始--------------------- -->
	<%--如果不使用data作为变量名称，在此增加如下代码： <c:set var="data" value="${data }"></c:set> 这里value中的data改成自定义的名称--%> 
	<%@ include file="/WEB-INF/jsp/common/emi_pager.jsp"%>
	<!-- ----------------------分页结束-------------------- -->
	</form>
</body>
</html>