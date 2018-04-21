
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
    
  <title>工艺路线设计</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9,chrome=1">
    <meta name="author" content="leipi.org">
    <link href="${ctx }/scripts/plugins/flowDesign/css/bootstrap/css/bootstrap.css?2025" rel="stylesheet" type="text/css" />
    <!--[if lte IE 6]>
    <link rel="stylesheet" type="text/css" href="${ctx }/scripts/plugins/flowDesign/css/bootstrap/css/bootstrap-ie6.css?2025">
    <![endif]-->
    <!--[if lte IE 7]>
    <link rel="stylesheet" type="text/css" href="${ctx }/scripts/plugins/flowDesign/css/bootstrap/css/ie.css?2025">
    <![endif]-->
    <link href="${ctx }/scripts/plugins/flowDesign/css/site.css?2025" rel="stylesheet" type="text/css" />
	
<!--[if IE 10]> 
		<script type="text/javascript">
	       $(function () {
	            jsPlumb.bind("ready", function () {
	                jsPlumb.setRenderMode(jsPlumb.CANVAS);
	                jsPlumb.setMouseEventsEnabled(true);
	                //jsPlumb.Defaults.Connector = ["Flowchart", { stub: 40}]; jsPlumb.Defaults.PaintStyle = { lineWidth: 2, strokeStyle: "#888888", joinstyle: "round" };
	
	             }
	         });
	    </script> 
	<![endif]--> 
<link rel="stylesheet" type="text/css" href="${ctx }/scripts/plugins/flowDesign/js/flowdesign/flowdesign.css"/>

<!--select 2-->
<link rel="stylesheet" type="text/css" href="${ctx }/scripts/plugins/flowDesign/js/jquery.multiselect2side/css/jquery.multiselect2side.css"/>

 </head>
<body>


	


<!-- fixed navbar -->
<div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <div class="pull-right">
        <button class="btn btn-info" type="button" id="flow_save">保存设计</button>
        <button class="btn btn-danger" type="button" id="flow_clear">清空连接</button>
      </div>

      <div class="nav-collapse collapse">
        <ul class="nav">
           <!-- <a class="brand" href="/demo.html" target="_blank">DEMO</a> -->
            <li><a href="javascript:void(0);">正在设计【${route.routname }】</a></li>
        </ul>
      </div>
      
    </div><!-- container -->
  </div>
</div>
<!-- end fixed navbar -->



<!-- Modal -->
<div id="alertModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3>消息提示</h3>
  </div>
  <div class="modal-body">
    <p>提示内容</p>
  </div>
  <div class="modal-footer">
    <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">确定</button>
  </div>
</div>

<!-- attributeModal -->
<div id="attributeModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width:900px;margin-left:-450px">
  <div class="modal-body" style="max-height:500px;"><!-- body --></div>
  <div class="modal-footer" style="padding:5px;">
    <!--a href="#" class="btn btn-danger" data-dismiss="modal" aria-hidden="true"><i class="icon-remove icon-white"></i></a-->
  </div>
</div>



<!--contextmenu div-->
<div id="processMenu" style="display:none;">
  <ul>
   <!--  <li id="pmBegin"><i class="icon-play"></i>&nbsp;<span class="_label">设为第一步</span></li> -->
    <li id="pmAttribute"><i class="icon-cog"></i>&nbsp;<span class="_label">工序设置</span></li>
    <li id="pmDelete"><i class="icon-trash"></i>&nbsp;<span class="_label">删除</span></li>
    
  </ul>
</div>
<div id="canvasMenu" style="display:none;">
  <ul>
  	<li id="cmAdd"><i class="icon-plus"></i>&nbsp;<span  title="增加一道工序" class="_label">添加工序</span></li>
  	<li id="cmGencode"><i class="icon-th-large"></i>&nbsp;<span title="自动生成编号、上道工序转入" class="_label">生成编码</span></li>
    <li id="cmSave"><i class="icon-ok"></i>&nbsp;<span title="保存工艺路线的设计" class="_label">保存设计</span></li>
    
     <!-- <li id="cmRefresh"><i class="icon-refresh"></i>&nbsp;<span class="_label">刷新 F5</span></li>   -->
  </ul>
</div>
<!--end div--> 

<div class="container mini-layout" id="flowdesign_canvas">
    <!--div class="process-step btn" style="left: 189px; top: 340px;"><span class="process-num badge badge-inverse"><i class="icon-star icon-white"></i>3</span> 步骤3</div-->
</div> <!-- /container -->

    
<div class="navbar navbar-fixed-bottom" style="color:#666;text-align:right;padding-right:10px">
</div>

   

<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jquery-1.7.2.min.js?2025"></script>
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/css/bootstrap/js/bootstrap.min.js?2025"></script>
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jquery-ui/jquery-ui-1.9.2-min.js?2025" ></script>
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jsPlumb/jquery.jsPlumb-1.3.16-all-min.js?2025"></script>
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jquery.contextmenu.r2.js?2025"></script>
<!--select 2-->
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/jquery.multiselect2side/js/jquery.multiselect2side.js?2025" ></script>
<!--flowdesign-->
<script type="text/javascript" src="${ctx }/scripts/plugins/flowDesign/js/flowdesign/leipi.flowdesign.v3.js?2025"></script>
<script type="text/javascript" src="${ctx }/scripts/Math.uuid.js"></script>
<script type="text/javascript">
//默认图标
var DEFAULT_ICON = "icon-cog";
// 图标-开始节点
var ICON_START = "icon-play";
//默认样式
var DEFAULT_STYLE = "width:120px;height:30px;line-height:30px;color:#0e76a8;";

var arr_updProcess = new Array();//更新的工序，存id
var arr_addProcess = new Array();//新增的工序，存节点对象
var arr_delProcess = new Array();//删除的工序，存id

//{'xxxxx':{'base':{'processName':'工序1','processCode':'01','semiProdId':'','productId':'','processId':''},'attrPreProc':[{'preId':'','baseUse':'3','baseQuantity':'2','standardUse':'1.5'}]}}
var process_objs = {};//工序属性详情 数组
var process_codeJson = {};//存放工序编码，在自动生成或手动修改时发生变化

var the_flow_id = '${route.gid}';
var the_flow_pk = '${route.pk}';


/*页面回调执行    callbackSuperDialog
    if(window.ActiveXObject){ //IE  
        window.returnValue = globalValue
    }else{ //非IE  
        if(window.opener) {  
            window.opener.callbackSuperDialog(globalValue) ;  
        }
    }  
    window.close();
*/
function callbackSuperDialog(selectValue){
     var aResult = selectValue.split('@leipi@');
     $('#'+window._viewField).val(aResult[0]);
     $('#'+window._hidField).val(aResult[1]);
    //document.getElementById(window._hidField).value = aResult[1];
    
}
/**
 * 弹出窗选择用户部门角色
 * showModalDialog 方式选择用户
 * URL 选择器地址
 * viewField 用来显示数据的ID
 * hidField 隐藏域数据ID
 * isOnly 是否只能选一条数据
 * dialogWidth * dialogHeight 弹出的窗口大小
 */
function superDialog(URL,viewField,hidField,isOnly,dialogWidth,dialogHeight)
{
    dialogWidth || (dialogWidth = 620)
    ,dialogHeight || (dialogHeight = 520)
    ,loc_x = 500
    ,loc_y = 40
    ,window._viewField = viewField
    ,window._hidField= hidField;
    // loc_x = document.body.scrollLeft+event.clientX-event.offsetX;
    //loc_y = document.body.scrollTop+event.clientY-event.offsetY;
    if(window.ActiveXObject){ //IE  
        var selectValue = window.showModalDialog(URL,self,"edge:raised;scroll:1;status:0;help:0;resizable:1;dialogWidth:"+dialogWidth+"px;dialogHeight:"+dialogHeight+"px;dialogTop:"+loc_y+"px;dialogLeft:"+loc_x+"px");
        if(selectValue){
            callbackSuperDialog(selectValue);
        }
    }else{  //非IE 
        var selectValue = window.open(URL, 'newwindow','height='+dialogHeight+',width='+dialogWidth+',top='+loc_y+',left='+loc_x+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');  
    
    }
}


var _canvas;
$(function(){
    var alertModal = $('#alertModal'),attributeModal =  $("#attributeModal");
    //消息提示
    mAlert = function(messages,s)
    { 
        if(!messages) messages = "";
        if(!s) s = 2000;
        alertModal.find(".modal-body").html(messages);
        alertModal.modal('toggle');
        setTimeout(function(){alertModal.modal("hide")},s);
    }

    //属性设置
    attributeModal.on("hidden", function() {
        $(this).removeData("modal");//移除数据，防止缓存
    });
    ajaxModal = function(url,fn)
    {
        url += url.indexOf('?') ? '&' : '?';
        url += '_t='+ new Date().getTime();
        attributeModal.find(".modal-body").html('<img src="${ctx }/scripts/plugins/flowDesign/images/loading.gif"/>');
        attributeModal.modal({
            remote:url
        });
        
        //加载完成执行
        if(fn)
        {
            attributeModal.on('shown',fn);
        }

      
    }

 
    
    /*
    js 命名习惯：首字母小写 + 其它首字线大写
    */
    /*步骤数据*/
    //var processData = {"total":6,"list":[{"id":"515","flow_id":"119","process_name":"俺的沙发","process_code":"01","process_to":"516","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:305px;top:91px;"},{"id":"516","flow_id":"119","process_name":"asd","process_to":"520","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:1024px;top:181px;"},{"id":"517","flow_id":"119","process_name":"\u65b0\u5efa\u6b65\u9aa4","process_to":"520,521","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:178px;top:442px;"},{"id":"519","flow_id":"119","process_name":"\u65b0\u5efa\u6b65\u9aa4","process_to":"521","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:781px;top:409px;"},{"id":"520","flow_id":"119","process_name":"\u65b0\u5efa\u6b65\u9aa4","process_to":"519","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:459px;top:314px;"},{"id":"521","flow_id":"119","process_name":"\u65b0\u5efa\u6b65\u9aa4","process_to":"","icon":"icon-star","style":"width:120px;height:30px;line-height:30px;color:#0e76a8;left:520px;top:523px;"}]};
    var designJson = ${route.designJson};
    var nodeTotal = ${nodeTotal}; 
    var processData = {"total":nodeTotal,"list":designJson};

    /*创建流程设计器*/
    _canvas = $("#flowdesign_canvas").Flowdesign({
                      "processData":processData
                      /*,mtAfterDrop:function(params)
                      {
                          //alert("连接："+params.sourceId +" -> "+ params.targetId);
                      }*/
                      /*画面右键*/
                      ,canvasMenus:{
                        "cmAdd": function(t) {
                            var mLeft = $("#jqContextMenu").css("left"),mTop = $("#jqContextMenu").css("top");
                        	var style = DEFAULT_STYLE+"left:"+mLeft+";top:"+mTop+";";	
                            var p_obj = {};
                            p_obj['id']=Math.uuid();//这里在js生成uuid
                            p_obj['flow_id']=the_flow_id;
                            p_obj['process_name']="新建工序";
                            p_obj['process_code']="";
                            p_obj['process_to']="";
                            p_obj['icon']=DEFAULT_ICON;
                            p_obj['style']=style;
                            if(!_canvas.addProcess(p_obj))//添加
                            {
                                mAlert("绘制节点异常，添加失败");
                            }else{
                            	arr_addProcess.push(p_obj);
                            	//_canvas.updateProcessName('0','ddd');
                            }
                        },
                        "cmSave": function(t) {
                        	saveFlow(true,false);
                        },
                        "cmGencode": function(t) {
                        	var processJson = _canvas.getProcessJson();//连接信息
                        	//1：检测是否有未连线的节点  2：找到源头节点id(可能多个)  3：依次生成编码
                        	var exi_nolink = false;//是否有未连线的节点
                        	var head_p = new Array();//源头节点数组
                        	$('.process-step').each(function(i){
                                var sourceId = $(this).attr('process_id');
                                var toLen = processJson[sourceId]['process_to'].length;//后续节点长度
                               	var pre_e = false;
                               	$('.process-step').each(function(j){
                               		var sourceId_in = $(this).attr('process_id');
                               		if(sourceId!=sourceId_in && processJson[sourceId_in]['process_to'].indexOf(sourceId)>=0){
                               			pre_e = true; 
                               		}
                               	});
                               	
                               	if(!pre_e){//没有上级节点
                               		if(toLen==0){//后续没有节点时，说明没有连线
                               			exi_nolink = true;
                                   	}
                               		if(toLen>0){//后续有节点，说明是源头节点
                               			head_p.push(sourceId);
                               		}
                               	}
                        	});
                        	if(exi_nolink){mAlert("警告：存在未连接的节点");return;}//存在未连接的节点，中断执行
                        	
                        	//从源头开始，依次生成编码
                        	var subcode_start = 1;
                        	for(var i=0;i<head_p.length;i++){
                        		var code = 1;
                        		var code_str = formatNum(code+"",2);
                        		var subcode_str = "";
                        		if(head_p.length>1){
                        			subcode_str = formatNum((i+1)+"",2);
                        		}
                        		var finalCode = code_str + subcode_str;
                        		process_codeJson[head_p[i]] = finalCode;
                        		_canvas.updateProcessCode(head_p[i],''+finalCode+'');
                        		//迭代，生成后续节点的代码
                        		genProcessCode(processJson[head_p[i]]['process_to'],1+1,subcode_start);
                        		subcode_start = subcode_start + processJson[head_p[i]]['process_to'].length; 
                        		push2updProcess(head_p[i]);//更新了的id
                        	}
                        	function genProcessCode(pro_ids,f_code,start){
                        		var subStart = start;
                            	for(var i=0;i<pro_ids.length;i++){
                            		var code_str = formatNum(f_code+"",2);
                            		var subcode_str = "";
                            		if(pro_ids.length>1){
                            			subcode_str = formatNum((i+subStart)+"",2);
                            		}
                            		var finalCode = code_str + subcode_str;
                            		process_codeJson[pro_ids[i]] = finalCode;
                            		_canvas.updateProcessCode(pro_ids[i],''+finalCode+'');
                            		//迭代，生成后续节点的代码
                            		genProcessCode(processJson[pro_ids[i]]['process_to'],f_code+1,start);
                            		start = start + processJson[pro_ids[i]]['process_to'].length;
                            		push2updProcess(pro_ids[i]);//更新了的id
                            	}
                        	}
                        },
                         //刷新
                        "cmRefresh":function(t){
                        	page_reload();
                        	//location.reload();
                            // _canvas.refresh();
                        },
                        /*"cmPaste": function(t) {
                            var pasteId = _canvas.paste();//右键当前的ID
                            if(pasteId<=0)
                            {
                              alert("你未复制任何步骤");
                              return ;
                            }
                            alert("粘贴:" + pasteId);
                        },*/
                        "cmHelp": function(t) {
                            mAlert('<ul><li><a href="" target="_blank">流程设计器 </a></li></ul>',20000);
                        }
                       
                      }
                      /*步骤右键*/
                      ,processMenus: {
                          
                          "pmBegin":function(t)
                          {
                              var activeId = _canvas.getActiveId();//右键当前的ID
                              var url = "${ctx}/flow/flow_setNodeFirst.emi";
                              $.post(url,{"flow_id":the_flow_id,"process_id":activeId},function(data){
                                  if(data.status==1)
                                  {
                                      //清除步骤
                                      //_canvas.delProcess(activeId);
                                      //清除连接   暂时先保存设计 + 刷新 完成
                                  	  saveFlow(false,true);
                                  }else{
                                  	  mAlert("设置失败");
                                  	  page_reload();
                                  }
                                  
                              },'json');
                          },
                          /*"pmAddson":function(t)//添加子步骤
                          {
                                var activeId = _canvas.getActiveId();//右键当前的ID
                          },
                          "pmCopy":function(t)
                          {
                              //var activeId = _canvas.getActiveId();//右键当前的ID
                              _canvas.copy();//右键当前的ID
                              alert("复制成功");
                          },*/
                          "pmDelete":function(t)
                          {
                              if(confirm("你确定删除工序吗？"))
                              {
                                    var activeId = _canvas.getActiveId();//右键当前的ID
                                    if(_canvas.delProcess(activeId)){
                                    	arr_delProcess.push(activeId);
                                    }
                              }
                          },
                          "pmAttribute":function(t)
                          {
                              var activeId = _canvas.getActiveId();//右键当前的ID
                              var process = "";//工序对象的json字符串
                              if(process_objs[activeId]){
                            	  process = 1;
                              }
                              //由于编码可能自动生成，  所以传
                              var code = process_codeJson[activeId] || ''; 
                              var url = "${ctx}/wms/basepd_attributePage.emi?routecId="+activeId+"&process="+process+"&processCode="+code;
                              ajaxModal(url,function(){
                                	//alert('加载完成执行')
                              });
                          },
                          "pmForm": function(t) {
                                var activeId = _canvas.getActiveId();//右键当前的ID
                                var url = "/Flowdesign/attribute/op/form/id/"+activeId+".html";
                                ajaxModal(url,function(){
                                    //alert('加载完成执行')
                                });
                          },
                          "pmJudge": function(t) {
                                var activeId = _canvas.getActiveId();//右键当前的ID
                                var url = "/Flowdesign/attribute/op/judge/id/"+activeId+".html";
                                ajaxModal(url,function(){
                                    //alert('加载完成执行')
                                });
                          },
                          "pmSetting": function(t) {
                                var activeId = _canvas.getActiveId();//右键当前的ID
                                var url = "/Flowdesign/attribute/op/style/id/"+activeId+".html";
                                ajaxModal(url,function(){
                                    //alert('加载完成执行')
                                });
                          } 
                      }
                      ,fnRepeat:function(){
                        //alert("步骤连接重复1");//可使用 jquery ui 或其它方式提示
                        mAlert("工序连接重复了，请重新连接");
                        
                      }
                      ,fnClick:function(){
                          var activeId = _canvas.getActiveId();
                          //mAlert("查看步骤信息 " + activeId);
                      }
                      ,fnDbClick:function(){
                          //和 pmAttribute 一样
                          var activeId = _canvas.getActiveId();//右键当前的ID
                              var url = "/Flowdesign/attribute/id/"+activeId+".html";
                              ajaxModal(url,function(){
                                //alert('加载完成执行')
                              });
                      }
                  });

    

    
    /*保存*/
    $("#flow_save").bind('click',function(){
    	saveFlow(true,false);
    });
    
    
    /*清除*/
    $("#flow_clear").bind('click',function(){
        if(_canvas.clear())
        {
            //alert("清空连接成功");
            mAlert("清空连接成功，你可以重新连接");
        }else
        {
            //alert("清空连接失败");
            mAlert("清空连接失败");
        }
    });


  
});

/**格式化数字为一个定长的字符串，前面补０ 
*参数: 
* Source 待格式化的字符串 
* Length 需要得到的字符串的长度 
*/ 
function formatNum(Source,Length){ 
	var strTemp=""; 
	for(i=1;i<=Length-Source.length;i++){ 
		strTemp+="0"; 
	} 
	return strTemp+Source; 
} 
//刷新页面
function page_reload()
{
    //location.reload();
    location.href="${ctx}/flow/flow_toDesignPage.emi?flowId=${flow.gid}";
}

//保存流程
function saveFlow(showTip,refresh){
	var processInfo = _canvas.getProcessInfo();//连接信息
    var url = "${ctx}/wms/basepd_saveProcessData.emi";
    $.post(url,{
	    	"flow_id":the_flow_id,
	    	"process_info":processInfo,
	    	"arr_updProcess":JSON.stringify(arr_updProcess),
	    	"arr_addProcess":JSON.stringify(arr_addProcess),
	    	"arr_delProcess":JSON.stringify(arr_delProcess),
	    	"process_objs":JSON.stringify(process_objs),
	    	"process_codeJson":JSON.stringify(process_codeJson),
    	},function(data){
        if(data=='success'){
        	if(showTip){
        		mAlert("保存成功");
        	}
        	if(refresh){
        		page_reload();
        	}
        }else{
        	mAlert("保存失败,请联系相关技术人员");
        }
    	
    },'text');
}

function push2updProcess(routecId){
	 //将有修改的工序id加入数组
   var upd_e = false;//是否已存在
   for(var i=0;i<arr_updProcess.length;i++){
  	 if(arr_updProcess[i] == routecId){
			upd_e = true; 
			break;
  	 }
   }
   if(!upd_e){ arr_updProcess.push(routecId);}
}
function push2addProcess(routecId){
	 //将有修改的工序id加入数组
  var upd_e = false;//是否已存在
  for(var i=0;i<arr_addProcess.length;i++){
 	 if(arr_addProcess[i] == routecId){
			upd_e = true; 
			break;
 	 }
  }
  if(!upd_e){ arr_updProcess.push(routecId);}
}
function push2delProcess(routecId){
	 //将有修改的工序id加入数组
  var upd_e = false;//是否已存在
  for(var i=0;i<arr_delProcess.length;i++){
 	 if(arr_delProcess[i] == routecId){
			upd_e = true; 
			break;
 	 }
  }
  if(!upd_e){ arr_updProcess.push(routecId);}
}
</script>

</body>
</html>