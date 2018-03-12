<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改博客页面</title>
<link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<%-- <script type="text/javascript" src="${cxt}/static/json/json.js"></script> --%>
<script type="text/javascript" charset="gbk" src="${cxt}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="${cxt}/static/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="gbk" src="${cxt}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	
	
	
	function submitData(){
		var title=$("#title").val();
		var blogTypeId=$("#blogTypeId").combobox("getValue");//直接获得选中下拉框的值(只对easyUI有用)
		var content=UE.getEditor('editor').getContent();
		var keyWord=$("#keyWord").val();
		var releaseDate=$("#releaseDate").val();
		if(title==null || title==''){
			alert("请输入标题！");
		}else if(blogTypeId==null || blogTypeId==''){
			alert("请选择博客类别！");
		}else if(content==null || content==''){
			alert("请输入内容！");
		}else{
			var j={'id':'${param.id}','title':title,'typeId':blogTypeId,'content':content,'releaseDate':releaseDate,
					   'contentNoTag':UE.getEditor('editor').getContentTxt(),//拿到没有标签的纯text
					   'summary':UE.getEditor('editor').getContentTxt().substr(0,155),//拿到没有标签的纯text并截取到0-155个字
					   'keyWord':keyWord};
			$.ajax({
				type : "post",
				url : "${cxt}/admin/blog/modify",
				data : j,	//将json字符串传入后台,若不指定JSON.stringify会被解析成a=b&c=d
				//contentType: "application/json; charset=utf-8",   //指定以json格式传入
				contentType: "application/x-www-form-urlencoded; charset=utf-8", //普通传入 防止传入中文乱码
				dataType : "json",
				success : function(result) {
					if (result.success) {
						$.messager.alert("系统提示", "博客修改成功！");
					} else {
						$.messager.alert("系统提示", "博客修改失败！");
					}
				},
				error : function(result){//response.status=500进入error方法里 参数:result代表是整个response对象
					alert(result.responseText);//拿到response的传输的文本
				}
			});
		/* 	$.post("${cxt}/admin/blog/modify",
					{'id':'${param.id}','title':title,'typeId':blogTypeId,'content':content,
					'contentNoTag':UE.getEditor('editor').getContentTxt(),//拿到没有标签的纯text
					'summary':UE.getEditor('editor').getContentTxt().substr(0,155),//拿到没有标签的纯text并截取到0-155个字
					'keyWord':keyWord},
					function(result){
				if(result.success){
					alert("博客修改成功！");
				}else{
					alert("博客修改失败！");
				}
			},"json").error(function(result){
				alert(result.responseText);
			}); */
		}
	}
	

	

</script>
</head>
<body style="margin: 10px">
<div id="p" class="easyui-panel" title="修改博客" style="padding: 10px">
 	<table cellspacing="20px">
   		<tr>
   			<td width="80px">博客标题：</td>
   			<td><input type="text" id="title" name="title" style="width: 400px;"/>
   				<input type="hidden" name="releaseDate" id="releaseDate">
   			</td>
   		</tr>
   		<tr>
   			<td>所属类别：</td>
   			<td>
   				<select class="easyui-combobox" style="width: 154px" id="blogTypeId" name="typeId" editable="false" panelHeight="auto"  value="">
					<option value="">请选择博客类别...</option>
					<#list typeList as blogType >
                        <option value="${blogType.id }">${blogType.typeName }</option>
					</#list>
                </select>
   			</td>
   		</tr>
   		<tr>
   			<td valign="top">博客内容：</td>
   			<td>
				   <script id="editor" type="text/plain" style="width:100%;height:500px;"></script>
				    <input type="hidden" id="content" name="content" />
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
 
 <script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');

    ue.addListener("ready",function(){
        //通过ajax请求数据
        UE.ajax.request("${cxt}/admin/blog/${param.id}/getById",
            {
                method:"post",
                async : false,  
               // data:{"id":"${param.id}"},
                onsuccess:function(result){
                	result = eval("(" + result.responseText + ")");
                	$("#title").val(result.title);
                	$("#keyWord").val(result.keyWord);
       				$("#blogTypeId").combobox("setValue",result.typeId);//直接对应下拉框的值让其选中(只对easyUI有用)
       				$("#releaseDate").val(result.releaseDate);
       				UE.getEditor('editor').setContent(result.content);
                }
            }
        );
    });
</script>
</body>
</html>

