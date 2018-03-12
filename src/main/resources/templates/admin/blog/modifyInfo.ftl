<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>修改个人信息页面</title>
    <link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.form.js"></script>
    <script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

    <script type="text/javascript" charset="gbk" src="${cxt}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="gbk" src="${cxt}/static/ueditor/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="gbk" src="${cxt}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">

        function submitData() {
            var nickName = $("#nickName").val();
            var sign = $("#sign").val();
            var profile = UE.getEditor('profile').getContent();

            if (nickName == null || nickName == '') {
                alert("请输入昵称！");
            } else if (sign == null || sign == '') {
                alert("请输入个性签名！");
            } else if (profile == null || profile == '') {
                alert("请输入个性简介！");
            } else {
                $("#pF").val(profile);
                $.ajax({
                    type: "post",
                    url: $("#form1").attr("action"),
                    data: $("#form1").serialize(),
                    dataType: "json",//指定返回字符串以json格式返回,若不指定则以字符串方式返回(js识别不了的json)
                    success: function (result) {
                        //var result=eval('('+result+')');//不指定dataType:json的话需要加这段代码即可以json方式访问
                        //alert(result);
                        //alert(result.success);
                        if (result.success) {
                            $.messager.alert("提示信息", result.message);
                        } else {
                            $.messager.alert("提示信息", result.message);
                        }
                    },
                    error: function (result) {
                        alert(result.responseText);
                    }
                });
            }
        }

        //上传图片
        function uploadPic(val) {
            if (val == "") {
                return;
            }
            //定义参数
            var options = {
                url: "${cxt}/admin/user/uploadPic",
                dataType: "json",
                type: "post",
                success: function (data) {
                    $("#picture").attr("src", data.allUrl);
                    $("#imageName").val(data.dataUrl);
                },
                error: function (data) {
                    alert(data.responseText);
                }
            };
            //jquery.form使用方式
            $("#form1").ajaxSubmit(options);
        }


        function uploadPicJersey(val) {
            if (val == "") {
                return;
            }
            //定义参数
            var options = {
                url: "${cxt}/admin/user/uploadPicJersey",
                dataType: "json",
                type: "post",
                success: function (data) {
                    if (!data.success) {
                        alert("上传图片失败");
                    }
                    $("#picture2").attr("src", data.allUrl);
                    $("#fileAllUrl").val(data.allUrl);
                    $("#subImageName").val(data.subImageName);
                    $("#isChangePic").val(1);
                },
                error: function (data) {
                    alert(data.responseText);
                }
            };
            //jquery.form使用方式
            $("#form1").ajaxSubmit(options);
        }


    </script>
</head>
<body style="margin: 10px">
<div id="p" class="easyui-panel" title="修改个人信息" style="padding: 10px">
    <form id="form1" action="${cxt}/admin/user/modify" method="post" enctype="multipart/form-data">
        <table cellspacing="20px">
            <tr>
                <td width="80px">用户名：</td>
                <td>
                    <input type="hidden" id="id" name="id" value="${currentUser.id }"/>
                    <input type="text" id="username" name="username" style="width: 200px;" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>昵称：</td>
                <td><input type="text" id="nickName" name="nickName" style="width: 200px;"/></td>
            </tr>
            <tr>
                <td>个性签名：</td>
                <td><input type="text" id="sign" name="sign" style="width: 400px;"/></td>
            </tr>
            <tr>
                <td>个人头像：</td>
                <td><img id="picture" width="200px" height="250px"/>
                    <input type="file" id="pic" name="pic" style="width: 400px;" onchange="uploadPic(this.value)"/>
                    <input type="hidden" name="imageName" id="imageName"/>
                </td>
            </tr>
            <tr>
                <td>副头像：</td>
                <td><img id="picture2" width="200px" height="250px"/>
                    <input type="file" id="pic2" name="pic2" style="width: 400px;"
                           onchange="uploadPicJersey(this.value)"/>
                    <input type="hidden" name="subImageName" id="subImageName"/>
                    <input type="hidden" name="fileAllUrl" id="fileAllUrl"/>
                    <input type="hidden" name="isChangePic" id="isChangePic" value="0"/>
                </td>
            </tr>
            <tr>
                <td valign="top">个人简介：</td>
                <td>
                    <script id="profile" type="text/plain" style="width:100%;height:500px;"></script>
                    <input type="hidden" id="pF" name="profile"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <a href="javascript:submitData()" class="easyui-linkbutton"
                       data-options="iconCls:'icon-submit'">提交</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('profile');

    ue.addListener("ready", function () {
        //通过ajax请求数据
        UE.ajax.request("${cxt}/admin/user/${currentUser.id}/getById",
                {
                    method: "post",
                    async: false,
                    data: {},
                    onsuccess: function (result) {
                        //alert(result.responseText.username);拿到是的字符串(js不识别的json字符串,需加上eval则识别)
                        //alert(result);xmlHTTPREQUEST
                        result = eval("(" + result.responseText + ")");
                        $("#username").val(result.username);
                        $("#nickName").val(result.nickName);
                        $("#sign").val(result.sign);
                        $("#nickName").val(result.nickName);
                        $("#picture").attr("src", result.allUrl);
                        $("#picture2").attr("src", result.subAllUrl);
                        $("#imageName").attr("value", result.imageName);
                        $("#subImageName").val(result.subImageName);
                        UE.getEditor('profile').setContent(result.profile);
                    }
                }
        );
    });

</script>
</body>
</html>