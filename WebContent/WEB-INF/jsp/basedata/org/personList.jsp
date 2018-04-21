<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物料</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/emicom.css">
</head>


	<script type="text/javascript">
		
		$(function(){
			$('.addBtn').click(function() {
			    $.dialog({
				drag: true,
				lock: true,
				resize: false,
				title:'新增物料档案',
			    width: '800px',
				height: '500px',
				content: 'url:${ctx}/wms/org_toAddDepartment.emi',
				button: [
				          {
				              name: '保存',
				              callback: function () {
				            	  if(!this.content.checkdata()){
					  					return false;
					  				}
				            		$.ajax({
				      				  data: $("#myform",this.content.document).serialize(),
				      				  type: 'POST',
				      				  url: '${ctx}/wms/org_addDepartment.emi',
				      				  success: function(req){
				      					  if(req=='success'){
				      						  $.dialog.alert_s('添加成功',function(){location.href="${ctx}/wms/org_getDepartment.emi";});
				      					  }else{
				      						  $.dialog.alert_e(req);
				      					  }
				      				  },
				      				  error:function(){
				      					  $.dialog.alert_e("添加失败");
				      				  }
				      			});
				                  return false;
				              },
				              focus: true
				          },

				          {
				              name: '关闭'
				          }
				      ]
				});
			});
			
			
			$('.editBtn').click(function() {
				var gid;
				if((document.getElementById("rightframe").contentDocument).getElementById('depGid').value==""){
					$.dialog.alert(tip_editSelect);
					return;
				}else{
					gid=(document.getElementById("rightframe").contentDocument).getElementById('depGid').value;
				}
				
			    $.dialog({
					drag: true,
					lock: true,
					resize: false,
					title:'修改物料档案',
				    width: '800px',
					height: '500px',
					content: 'url:${ctx}/wms/org_toEditDepartment.emi?gid='+gid,
					button: [
					          {
					              name: '保存',
					              callback: function () {
					            	  if(!this.content.checkdata()){
						  					return false;
						  				}
					            		$.ajax({
					      				  data: $("#myform",this.content.document).serialize(),
					      				  type: 'POST',
					      				  url: '${ctx}/wms/org_editDepartment.emi',
					      				  success: function(req){
					      					  if(req=='success'){
					      						  $.dialog.alert_s('修改成功',function(){location.href="${ctx}/wms/org_getDepartment.emi";});
					      					  }else{
					      						  $.dialog.alert_e(req);
					      					  }
					      				  },
					      				  error:function(){
					      					  $.dialog.alert_e("添加失败");
					      				  }
					      			});
					                  return false;
					              },
					              focus: true
					          },

					          {
					              name: '关闭'
					          }
					      ]

				});
			});
			
			$('.delBtn').click(function() {
				
				
				var gid;
				if((document.getElementById("rightframe").contentDocument).getElementById('depGid').value==""){
					$.dialog.alert(tip_deleteSelect);
					return;
				}else if($('#isLast').val()=="true"){
					$.dialog.alert_w("非末级,不能删除");
					return;
				}
				else{
					gid=(document.getElementById("rightframe").contentDocument).getElementById('depGid').value;
				}
				
				$.dialog.confirm(tip_confirmDelete, function(){		
					$.ajax({
						  data:$("#myform").serialize(),
						  type: 'POST',
						  url: "${ctx}/wms/org_deleteDepartment.emi?gid="+gid,
						  dataType:'text',
						  success: function(req){
							  if(req=='success'){
								  $.dialog.alert_s('删除成功',function(){
									  location.href="${ctx}/wms/org_getDepartment.emi";
								  });
							  }else {
								  $.dialog.alert_e("删除失败");
							  }
							  
						  }
					});
				}, function(){
				    //$.dialog.tips('执行取消操作');
				});
				
			});
			
		});
	</script>
	
	




<body>
	 <div class="EMonecontent">
	 	<div style="width: 100%;height: 15px;"></div>

	 	<!--按钮部分 end-->
	 	<!--主体部分-->
	 	<div class="mainword">
	 		<div class="mainaddword" style="">
	 			<div class="creattable" style="width:90%;margin-top: 20px;margin-bottom: 15px;">
		 			<div class="">
			 			<table>
				 			<tbody>
				 				<tr>
				 					 
				 					<th style="width: 120px;">序号</th>
				 					<th>人员编码</th>
				 					<th>人员名称</th>
				 					<th>性别</th>
				 					<th>出生年月</th>
				 					<th>入职日期</th>
				 					<th>离职日期</th>
				 					<th>备注</th>
				 				</tr>
				 				 
				 				<tr>
				 					<td style="width: 120px;">1</td>
				 					<td>010</td>
				 					<td>张淑珍</td>
				 					<td>男</td>
				 					<td>1985-12-12</td>
				 					<td>2010-12-12</td>
				 					<td>2010-12-12</td>
				 					<td></td>
				 				</tr>
				 				 		 
				 			</tbody>
				 		</table>
			 		</div>
			 		<!--分页部分-->
				 	<div class="EMpage" style="	border: 1px solid red;">
				 		分页
				 	</div>
				 	<!--分页部分 end-->
		 		</div>
	 			<div class="cf"></div>
	 			
	 		</div>
	 	</div>
	 	<!--主体部分 end-->	
	 	 
	</div>

</body>
</html>