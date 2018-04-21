<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%String contextPath = request.getContextPath();%> 
<c:set var="ctx" value="<%=contextPath %>"/>
<html>
 <head>
    
  <title>工序设计</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
 <style type="text/css">
 .mytd{
 	vertical-align: middle;
 }
 </style>
 </head>
<body>

	产成品信息：<input type="text" name="semiProd" id="semiProd" style="width:150px">
			<input type="hidden" name="semiProdId" id="semiProdId">
	成品编码：<input type="text" name="productCode" id="productCode" style="width:150px" >
			<input type="hidden" name="productId" id="productId" >
	成品名称：<input type="text" name="productName" id="productName" style="width:150px">
	<br>
	&nbsp;&nbsp;&nbsp;规格型号：<input type="text" style="width:150px" name="productStandard" id="productStandard">
	计量单位：<input type="text" style="width:150px" name="productUnit" id="productUnit">
	工序信息：<input type="text" style="width:150px" name="processInfo" id="processInfo">
	<br>
	<input type="hidden" name="processId" id="processId" >
	&nbsp;&nbsp;&nbsp;工序名称：<input type="text" style="width:150px" name="processName" id="processName" value="${standardProcess.opname }">
	工序编码：<input type="text" style="width:150px" name="processCode" id="processCode" maxlength="4" value="${processCode }">
<ul class="nav nav-tabs" id="attributeTab">
  <li class="active"><a href="#attrPreProc">上道工序转入</a></li>
  <li><a href="#attrGoods">物料领用</a></li>
  <li><a href="#attrWorkcenter">工作中心/设备</a></li> 
  <li><a href="#attrRole">角色/人员</a></li>
  <li><a href="#attrCost">成本</a></li>
  <li><a href="#attrAudit">审批</a></li>
  <li><a href="#attrRelease">放行</a></li>
  <li><a href="#attrOut">委外</a></li>
</ul>

<form class="form-horizontal" target="hiddeniframe" method="post" id="flow_attribute" name="flow_attribute" action="${ctx }/flow/flow_saveAttribute.emi">
<input type="hidden" name="route_id" value="${routec.processRouteId }"/>
<input type="hidden" name="process_id" value="${routec.gid }"/>
  <div class="tab-content">
    <div class="tab-pane active" id="attrPreProc">
		<table class="table table-bordered table-condensed" contenteditable="true">
			<thead>
				<tr style="background-color: #dcf5fc;">
					<th width="8%" style="text-align: center;">序号</th>
					<th width="22%" style="text-align: center;">工序编码</th>
					<th width="25%" style="text-align: center;">工序名称</th>
					<th width="15%" style="text-align: center;">基本用量</th>
					<th width="15%" style="text-align: center;">基础数量</th>
					<th width="15%" style="text-align: center;">标准用量</th>
				</tr>
			</thead>
			<tbody>
				<tr >
					<td  style="text-align: center;vertical-align: middle;">1
						<input type="hidden" name="preProcessId" value="1">
					</td>
					<td style="text-align: center;vertical-align: middle;">TB - Monthly</td>
					<td style="text-align: center;vertical-align: middle;">01/04/2012</td>
					<td style="text-align: center;vertical-align: middle;"><input type="text" name="baseUse_1" id="baseUse_1" style="width: 100px;height: 16px"> </td>
					<td style="text-align: center;vertical-align: middle;"><input type="text" name="baseQuantity_1" id="baseQuantity_1" style="width: 100px;height: 16px"></td>
					<td style="text-align: center;vertical-align: middle;">Default</td>
				</tr>
				<tr>
					<td style="text-align: center;vertical-align: middle;">2
						<input type="hidden" name="preProcessId" value="2">
					</td>
					<td style="text-align: center;vertical-align: middle;">TB - Monthly</td>
					<td style="text-align: center;vertical-align: middle;">01/04/2012</td>
					<td style="text-align: center;vertical-align: middle;"><input type="text" name="baseUse_2" id="baseUse_2" style="width: 100px;height: 16px"> </td>
					<td style="text-align: center;vertical-align: middle;"><input type="text" name="baseQuantity_2" id="baseQuantity_2" style="width: 100px;height: 16px"></td>
					<td style="text-align: center;vertical-align: middle;">Default</td>
				</tr>
			</tbody>
		</table>
    </div><!-- attrPreProc end -->


    <div class="tab-pane " id="attrGoods">
		
    </div><!-- attrGoods end -->


    <div class="tab-pane" id="attrWorkcenter">

    </div><!-- attrWorkcenter end -->
    
    
    <div class="tab-pane" id="attrRole">

    </div><!-- attrRole end -->
    
    
    <div class="tab-pane" id="attrCost">

    </div><!-- attrCost end -->
    
    
    <div class="tab-pane" id="attrAudit">

    </div><!-- attrAudit end -->
    
    
    <div class="tab-pane" id="attrRelease">

    </div><!-- attrRelease end -->
    
    
    <div class="tab-pane" id="attrOut">
		
    </div><!-- attrOut end -->


  </div>

<div>
  <hr/>
  <span class="pull-right">
  	<button class="btn btn-primary" onclick="onClik()" type="button" id="attributeOK">确定</button>
     <a href="#" class="btn" id="btn_cancel" data-dismiss="modal" aria-hidden="true">取消</a>
      
  </span>
</div>
</form>
<iframe id="hiddeniframe" style="display: none;" name="hiddeniframe"></iframe>


    
<script type="text/javascript">
  /*var flow_id =  '4';//流程ID
  var process_id = '61';//步骤ID
  var get_con_url = "/index.php?s=/Flowdesign/get_con.html";//获取条件
  */
  /*
   * 初始化数据
   */
  $(function(){
	 var routecId = '${routecId}';
	 if("${isQuery}"!="1"){//未经过数据库查询，则从页面获取值
		 if(process_codeJson[routecId]){
			 $('#processCode').val(process_codeJson[routecId]);//编号 
		 }
		 if(process_objs[routecId]){
			 var thisProcess = process_objs[routecId];
			 /*基础数据*/
			 $('#processName').val(thisProcess['base']['processName']);//名称
			 
			 /*上道转入*/
			 
		 }
	 }
  });
  $.fn.serializeObject = function()    
  {    
     var o = {};    
     var a = this.serializeArray();    
     $.each(a, function() {    
         if (o[this.name]) {    
             if (!o[this.name].push) {    
                 o[this.name] = [o[this.name]];    
             }    
             o[this.name].push(this.value || '');    
         } else {    
             o[this.name] = this.value || '';    
         }    
     });    
     return o;    
  };  
    
  function onClik(){  
          //var data = $("#form1").serializeArray(); //自动将form表单封装成json  
         
         //{'xxxxx':{'base':{'processName':'工序1','semiProdId':'','productId':'','processId':''},'attrPreProc':[{'preId':'','baseUse':'3','baseQuantity':'2','standardUse':'1.5'}]}}
         var routecId = '${routecId}';
          var o = {};
         var jsonuserinfo = $('#flow_attribute').serializeObject(); 
        /*  var key = "999";
         o[key] = jsonuserinfo || '';    
         alert(JSON.stringify(jsonuserinfo));   */
         //o['routecId']=routecId;//工艺路线子表id
         //工序基础信息
         var base = {};
         base['processName'] = $('#processName').val();
         base['semiProdId'] = '';
         base['productId'] = '';
         base['processId'] = '';
         o['base'] = base;
         //上道工序转入[数组]
         var attrPreProc = new Array();
         for(var i=0;i<jsonuserinfo['preProcessId'].length;i++){
        	 var preObj = {};
        	 var preId = jsonuserinfo['preProcessId'][i];
        	 preObj['preId'] = preId;
        	 preObj['baseUse'] = jsonuserinfo['baseUse_'+preId];
        	 preObj['baseQuantity'] = jsonuserinfo['baseQuantity_'+preId];
        	 preObj['standardUse'] = preObj['baseUse']/preObj['baseQuantity'];
        	 attrPreProc.push(preObj);
         }
         o['attrPreProc'] = attrPreProc;
         
         process_objs[routecId]=o;
         // 整体数据加入到数组
         //var o_e = false;//是否已存在
        
         /* for(i=0;i<process_objs.length;i++){
        	 if(process_objs[i]['routecId']==routecId){
        		 //存在则替换
        		 process_objs.splice(i,1,o);
        		 o_e = true;
        		 break;
        	 }
         }
         if(!o_e){process_objs.push(o); }  *///不存在则加入
         
         //工序编号更新
         process_codeJson[routecId] = $('#processCode').val();
         push2updProcess(routecId);
         _canvas.updateProcessName(routecId, $('#processName').val() || '');
         _canvas.updateProcessCode(routecId, $('#processCode').val() || '');
         document.getElementById('btn_cancel').click();
  } 
  var _out_condition_data = {"63":{"condition":"<option value=\"'data_1' = '1'  AND\">'\u6587\u672c\u6846' = '1'  AND<\/option><option value=\"( 'data_2' = '1' AND 'data_2' = '2' OR 'data_2' = '3' OR 'data_2' = '4' OR 'data_2' = '45')\">( '\u4e0b\u62c9\u83dc\u5355' = '1' AND '\u4e0b\u62c9\u83dc\u5355' = '2' OR '\u4e0b\u62c9\u83dc\u5355' = '3' OR '\u4e0b\u62c9\u83dc\u5355' = '4' OR '\u4e0b\u62c9\u83dc\u5355' = '45')<\/option>","condition_desc":"\u5feb\u6377\u5ba1\u6279\u6761\u4ef6\u4e0d\u6210\u7acb"},"64":{"condition":"<option value=\"'data_2' = '1'  AND\">'\u4e0b\u62c9\u83dc\u5355' = '1'  AND<\/option><option value=\"'data_2' = '2'\">'\u4e0b\u62c9\u83dc\u5355' = '2'<\/option>","condition_desc":"\u597d\u5427"}};
</script>
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/flowdesign/attribute.js"></script>


</body>
</html>