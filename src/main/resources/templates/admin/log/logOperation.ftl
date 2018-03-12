<link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<!DOCTYPE html>
<html>
  <head>

	<style type="text/css">
		a:link {
			text-decoration: none;
			color:black;
		}
		a:visited {
			text-decoration: none;
			color:black;
		}
		a:hover {
			text-decoration: none;
			color:black;
		}
		a:active {
			text-decoration: none;
			color:black;
		}
	</style>
		<title>日志主页</title>
    
    <script type="text/javascript">
		
	
	// 条件搜索日志信息
	function searchLog() {
		$('#dg').datagrid('load', {
			userName : $("#s_userName").val(),
			module : $("#s_module").val(),
			operation : $("#s_operation").val(),
			start : $('#s_start').datetimebox('getValue'),
			end : $('#s_end').datetimebox('getValue')
		});
	}


	
	// 日志删除
	function deleteLog() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert('系统提示', '请选择要删除的数据！');
			return;
		}
		var strIds = [];
		for ( var i = 0; i < selectedRows.length; i++) {
			strIds.push(selectedRows[i].id);
		}
		var ids = strIds.join(",");
		$.messager.confirm("系统提示", "您确认要删除这<font color=red>" + selectedRows.length
				+ "</font>条数据吗？", function(r) {
			if (r) {
				$.post("${cxt}/admin/log/operation/delete", {ids : ids}, function(result) {
					result=eval("("+result+")");
					if (result.success) {
						$.messager.alert('系统提示', "您已成功删除<font color=red>"
								+ result.delNums + "</font>条数据！");
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert('系统提示', result.errorMsg);
					}
				}).error(function(result){
					$.messager.alert('系统提示', result.responseText);
				});
			}
		});
	}



	// 手动备份
	function manualBackup(){
		$.messager.confirm("系统提示", "备份之后会将原来的日志删除。确定吗？", function(r) {
			if (r) {
				$.post("${cxt}/admin/log/operation/backup", function(result) {
					if (result.success) {
						$.messager.alert("系统提示","<a href='javascript:downloadLogBus();'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备份成功点击查看备份</a>");
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert('系统提示', result.errorMsg);
					}
				}, "json");
			}
		});
	}

	// 查看所有的备份列表
	function downloadLogBus(){
		$("#backupLog").dialog("open").dialog("setTitle","日志下载");
		$('#backupTable').datagrid({       
		        nowrap : false,//设置为true，当数据长度超出列宽时将会自动截取  
		        striped : true,//设置为true将交替显示行背景。  
		        collapsible : true,//显示可折叠按钮 
		    	url:"${cxt}/admin/backup/list?backupType=操作日志",//url调用Action方法
		        singleSelect:false,//为true时只能选择单行  
		        fitColumns:true,//允许表格自动缩放，以适应父容器  
		        remoteSort : false, 
		        pagination : true,//分页  
		        rownumbers : true//行数  
		 });  
	}


	
	// 下载Log4j文件
	function downloadLog(){
		location.href="${cxt}/admin/log/operation/downloadLog";
	}
	
	function formatParameter(val,row){
		var str="{";
		$.each(val,function(i,e){
			$.each(val[i],function(k,v){
				 str+=k+":"+v+",";
			});
		});
		var i=str.lastIndexOf(",");
		str=str.substring(0, i-1)+"}";
		return str;
	}
</script>
    </head>
 
 
<body style="margin:1px">

<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" fitColumns=""  nowrap="false"
   	    pagination="true" rownumbers="true" url="${cxt}/admin/log/operation/list" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
            		<th data-options="field:'id'">id<th>
                	<th data-options="field:'username'" align="center">操作人</th>
                	<th field="ipAddress"  align="center">ip地址</th>
                	<th field="className"  align="center">操作类</th>
                	<th field="methodName" align="center">方法名称</th>
                	<th field="parameter"  align="center"  formatter="formatParameter">方法参数</th>
                	<th field="operationType"  align="center">操作类型</th>
					<th field="createDate"  align="center"  >操作时间</th>
					<th field="moduleName"  align="center">操作模块</th>
					<th field="operationContent"  align="center">操作内容</th>
				</tr>
        </thead>
</table>

	
<!-- 表格上方菜单 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<a  clazz="easyui-linkbutton" onClick="deleteLog()"     class="easyui-linkbutton"        iconCls="icon-remove" >删除</a>
		<a  clazz="easyui-linkbutton" onClick="downloadLog()"   class="easyui-linkbutton"      iconCls="icon-edit" >后台日志下载</a>
		<a  clazz="easyui-linkbutton" onClick="manualBackup()"    class="easyui-linkbutton"      iconCls="icon-remove" >手动备份</a>
		<a  clazz="easyui-linkbutton" onClick="downloadLogBus()"  class="easyui-linkbutton"      iconCls="icon-edit" >备份下载</a>
	</div>
	<div class="updownInterval"> </div>
	<div>
		&nbsp;操作人：     &nbsp;<input type="text" name="s_userName" id="s_userName" size="20" onkeydown="if(event.keyCode==13) searchLog()"/>
		&nbsp;操作类型：&nbsp;<input type="text" name="s_operation" id="s_operation" size="20" onkeydown="if(event.keyCode==13) searchLog()"/>
		&nbsp;所属模块：&nbsp;<input type="text" name="s_module" id="s_module" size="20" onkeydown="if(event.keyCode==13) searchLog()"/>
		&nbsp;开始时间：&nbsp;<input class="easyui-datetimebox" name="s_start" id="s_start" />
		&nbsp;结束时间：&nbsp;<input class="easyui-datetimebox" id="s_end" name="s_end"  />
		<div class="updownInterval"> </div>
		<a href="javascript:searchLog()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	<div class="updownInterval"> </div>
</div>




<!-- 备份下载展示列表 -->
<div id="backupLog" class="easyui-dialog" style="width: 650px;height: 400px;padding: 10px 20px" closed="true" >
<table  class="easyui-datagrid" id="backupTable" >
    <thead>
    	<tr>
    		<th field="id"   width="30"  align="center" data-options="hidden:true"></th>
    		<th field="backupName" width="100" align="center" data-options="formatter:formatDownload">文件名称</th>
    		<th field="backupPath" width="100" align="center" >日志路径</th>
    		<th field="createDate" width="100" align="center" >备份时间</th>
    		<th field="deleteFlag" width="100" align="center" >删除标记</th>
    	</tr>
    </thead>
</table>
</div>

	<!-- 格式化备份表行，变成下载a标签 -->
	<script type="text/javascript">
    		function formatDownload(val,row){
				return "<a href='${cxt}/admin/log/operation/downloadBackup?fileName="+val+"'>"+val+"</a>";
        	}
	</script>


</body>
</html>
