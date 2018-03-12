<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评论管理页面</title>
<link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
/**
 * 以下属性可以下载方法里也可以卸载html标签中
 */
$(function(){
	$("#dg").datagrid({
		url:"${cxt}/admin/blog/comment/list",//加载数据的路径
		fitColumns:true,//占满父容器的宽度
		striped:true, //显示条纹，奇偶行背景色不同
		rownumbers:true,//显示每一行的行号
		loadMsg:"正在加载中........",//加载数据时显示的消息
		idField:"id",//标识字段
		onLoadSuccess:function(data){//加载数据成功后
			/*对方传入的为key:value形式的 i为key item:value
			    对方传入的为对象形式,i:下标  item:当前对象*/ 
			$.each(data,function(i,item){ 
					//alert(i);
					//alert(item);
			});
		},
        onLoadError: function (data) { //加载数据失败后
 			alert(data.responseText);
        },
	});
});
	

	
	function deleteComment(){
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
					$.post("${cxt}/admin/blog/comment/delete",{ids:ids},function(result){
						if(result.success){
							 $.messager.alert("系统提示","数据已成功删除！");
							 $("#dg").datagrid("reload");
						}else{
							$.messager.alert("系统提示","数据删除失败！");
						}
					},"json").error(function(result){
						$.messager.alert("系统提示",result.responseText);
					});
				} 
	   });
	}
	
	function formatBlogTitle(val,row){
		if(val==null){
			return "<font color='red'>该博客已被删除！</font>";
		}else{
			return "<a target='_blank' href='${cxt}/blog/detail/"+val.id+"'>"+val.title+"</a>";
		}
	}
	
	function formatState(val,row){
		if(val==0){
			return "待审核";
		}else if(val==1){
			return "审核通过";
		}else if(val==2){
			return "审核未通过";
		}
	}
	
</script>
</head>
<body style="margin: 1px">
<table id="dg" title="评论管理" toolbar="#tb" pagination="true" pageList="[1,2,3,5,10,20,50]" pageSize="10">
   <thead>
   	<tr>
   		<th field="cb" checkbox="true" align="center"></th>
   		<th field="id" width="20" align="center">编号</th>
   		<th field="blog" width="200" align="center" formatter="formatBlogTitle">博客标题</th>
   		<th field="userIp" width="100" align="center">用户IP</th>
   		<th field="content" width="200" align="center">评论内容</th>
   		<th field="commentDate" width="50" align="center">评论日期</th>
   		<th field="state" width="50" align="center" formatter="formatState">评论状态</th>
   	</tr>
   </thead>
 </table>
 <div id="tb">
 	<div>
 		<a href="javascript:deleteComment()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
 	</div>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
 </div>
 
 
</body>
</html>