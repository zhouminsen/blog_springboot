<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客管理页面</title>
<link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	
	function formatBlogType(val,row){//row:该集合中的某一组,val:该组中的某一个属性
		if(val==null){
			return "";
		}
		return val.typeName;
	}
	
	function formatState(val,row){
		if (val==0) {
			return "<a href='javascript:releaseOrBackout("+row.id+",1)'>"+"发布"+"</a>";
		}else {
			return "<a href='javascript:releaseOrBackout("+row.id+",0)'>"+"撤除"+"</a>";
		}
	}
	
	function formatTitle(val,row){
		return "<a target='_blank' href='${cxt}/blog/detail/"+row.id+"'>"+val+"</a>";
	}
	
	function releaseOrBackout(id,state){
		$.post("${cxt}/admin/blog/releaseOrBackout",{id:id,state:state},function(result){
			if (result.success) {
				alert(result.message);
				$("#dg").datagrid("reload");
			}else {
				alert(result.message);
			}
		},"json").error(function(result){
			alert(result.responseText);
		});
	}
	
	function searchBlog(){
		$("#dg").datagrid('load',{
			"title":$("#s_title").val() 
		});
	}
	
	function deleteBlog(){
		var selectedRows=$("#dg").datagrid("getSelections");
		if(selectedRows.length==0){
			 $.messager.alert("系统提示","请选择要删除的数据！");
			 return;
		 }
		 var strIds=[];
		 for(var i=0;i<selectedRows.length;i++){
			 strIds.push(selectedRows[i].id);
		 }
		 var ids=strIds.join(",");
		 $.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
				if(r){
					//普通http请求
					<#--location.href="${cxt}/admin/blog/delete?ids="+ids;-->
					
		 		/****************post 有error方法**********************/
				 /* $.post("${cxt}/admin/blog/delete",{ids:ids},function(result){
					 //json字符串格式化可用js识别的json
					if (result.succsess) {
						$.messager.alert("系统提示", "数据已成功删除！");
						$("#dg").datagrid("reload");
					}else {
						$.messager.alert("系统提示", "数据删除失败!");
					}
				}, "json").error(function(result) {
					$.messager.alert("系统提示", result.responseText);
				}); */
					
				/**********ajax方式*************************************/
				$.ajax({
					type : "post",//指定请求方式
					url : "${cxt}/admin/blog/delete", //请求路径
					data : {ids:ids},	//请求参数
					contentType: "application/x-www-form-urlencoded; charset=utf-8", //防止传入中文乱码
					dataType : "json",//指定返回字符串以json格式返回,若不指定则以字符串方式返回(js识别不了的json)
					success : function(result) {//进入success里,jquery已经把服务端传来的字符串解析到result里的 所以这里不需要加上result.responseText
						//var result=eval('('+result+')');//不指定dataType:json的话需要加这段代码即转换为js识别的json
						//alert(result);
						//alert(result.success);
						if (result.success) {
							$.messager.alert("系统提示", "数据已成功删除！");
							$("#dg").datagrid("reload");
						} else {
							$.messager.alert("系统提示", "数据删除失败!");
						}
					},
					error : function(result){//response.status=500进入error方法里 参数:result代表是整个response对象
						alert(result.responseText);//拿到response的传输的文本
					}
				});
			}
		});
	}

	function restore(){
		var ids;
		var selectedRows=$("#dg").datagrid("getSelections");
		if(selectedRows.length==0){
			 $.messager.alert("系统提示","没有选择表示要复原全部数据哦!");
		 }else{
		 var strIds=[];
		 for(var i=0;i<selectedRows.length;i++){
			 strIds.push(selectedRows[i].id);
		  }
		 	ids=strIds.join(",");	
	 	}
		
		$.post("${cxt}/admin/blog/restore",{ids:ids},function(result){
				if (result) {
					$.messager.alert("系统提示", "数据已成功恢复！");
					$("#dg").datagrid("reload");
				}
		},"json").error(function(result){
			alert(result.responseText);
		});
	}
	
	function openBlogModifyTab() {
		var selectedRows = $("#dg").datagrid("getSelections");
		if (selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一个要修改的博客！");
			return;
		}
		var row = selectedRows[0];
		var node={
				text:"修改博客",
				attributes:{
							 "url":"/admin/blog/modifyUI?id="+ row.id+""
							},
				iconCls:"icon-writeblog"};
		window.parent.openTab(node);
	}
	
</script>
</head>
<body style="margin: 1px">
<table id="dg" title="博客管理" class="easyui-datagrid"
   fitColumns="true" pagination="true" rownumbers="true"
   url="${cxt}/admin/blog/list" fit="true" toolbar="#tb">
   <thead>
   	<tr>
   		<th field="cb" checkbox="true" align="center"></th>
   		<th field="id" width="20" align="center">编号</th>
   		<!-- formatter代表需要格式化,value是个function的方法 -->
   		<th field="title" width="200" align="center" formatter="formatTitle">标题</th>
   		<th field="releaseDateStr" width="50" align="center" sortable="true">发布日期</th>
   		<th field="blogType" width="50" align="center" formatter="formatBlogType">博客类别</th>
   		<th field="state" width="50" align="center" formatter="formatState">发布</th>
   	</tr>
   </thead>
 </table>
 <div id="tb">
 	<div>
 		<a href="javascript:openBlogModifyTab()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
 		<a href="javascript:deleteBlog()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
 		<a href="javascript:restore()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">一键还原博文</a>
 	</div>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
 	<div>
 		&nbsp;标题：&nbsp;<input type="text" id="s_title" size="20" onkeydown="if(event.keyCode==13) searchBlog()"/>
 		<a href="javascript:searchBlog()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
 	</div>
 </div>
</body>
</html>