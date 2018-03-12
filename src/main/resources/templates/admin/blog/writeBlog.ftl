<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>写博客页面</title>
<link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" charset="gbk" src="${cxt}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="${cxt}/static/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="gbk" src="${cxt}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	
	function submitData(){
		$("#blogTypeId").val($("#blogTypeId").combobox("getValue"));
		$("#content").val(UE.getEditor('editor').getContent());
		$("#contentNoTag").val(UE.getEditor('editor').getContentTxt());
		$("#summary").val(UE.getEditor('editor').getContentTxt().substr(0,155));
		if(title==null || title==''){
			alert("请输入标题！");
		}else if(blogTypeId==null || blogTypeId==''){
			alert("请选择博客类别！");
		}else if(content==null || content==''){
			alert("请输入内容！");
		}else{
			$.post($("#form1").attr("action"),$("#form1").serialize(),function(result){
				if(result){
					alert("博客发布成功！");
					resetValue();
				}else{
					alert("博客发布失败！");
				}
			},"json").error(function(result){
				$.messager.alert(result.responseText);
			});
		}
	}
	
	// 重置数据
	function resetValue(){
		$("#title").val("");
		$("#blogTypeId").combobox("setValue","");
		UE.getEditor('editor').setContent("");
		$("#keyWord").val("");
	}
	
</script>
</head>
<body style="margin: 10px">
<form action="${cxt}/admin/blog/write" method="post" id="form1">
<div id="p" class="easyui-panel" title="编写博客" style="padding: 10px">
 	<table cellspacing="20px">
   		<tr>
   			<td width="80px">博客标题：</td>
   			<td><input type="text" id="title" name="title" style="width: 400px;"/>
   				<input type="hidden" id="userId" name="userId" value="${currentUser.id}"/>
   				<input type="hidden" id="content" name="content"/>
   				<input type="hidden" id="contentNoTag" name="contentNoTag"/>
   				<input type="hidden" id="summary" name="summary"/>
   			</td>
   		</tr>
   		<tr>
   			<td>所属类别：</td>
   			<td>
   				<select class="easyui-combobox" style="width: 154px" id="blogTypeId" name="typeId" editable="false" panelHeight="auto"  value="">
					<option value="">请选择博客类别...</option>
				<#list typeList as blogType>
                    <option value="${blogType.id }">${blogType.typeName }</option>
				</#list>
                </select>
   			</td>
   		</tr>
   		<tr>
   			<td valign="top">博客内容：</td>
   			<td>
				   <script id="editor" type="text/plain" style="width:100%;height:500px;"></script>
   			</td>
   		</tr>
   		<tr>
   			<td>关键字：</td>
   			<td><input type="text" id="keyWord" name="keyWord" style="width: 400px;"/>&nbsp;(多个关键字中间用空格隔开)</td>
   		</tr>
   		<tr>
   			<td></td>
   			<td>
   				<a href="javascript:submitData()" class="easyui-linkbutton" data-options="iconCls:'icon-submit'">发布博客</a>
   			</td>
   		</tr>
   	</table>
 </div>
 </form>
 
 <script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');


</script>
</body>
</html>