<!DOCTYPE html>
<!-- 仅作为模板使用,服务端不要直接返回 -->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Java开源博客系统后台管理页面-Powered by java1234</title>
    <link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/icon.css"/>
    <script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">

        var url;

        function haha() {
            alert("我是父窗体吗");
        }

        $(function () {
            // 加载左树菜单
            $("#tree").tree({
                lines: true,
                url: "${cxt}/admin/menu/list?parentId=-1",
/*                url : '${cxt}/admin/menu/getMenuTree?parentId=-1',
                 onLoadSuccess : function() {
                 //全部展开
                 $("#tree").tree('expandAll');
                 },*/
                onBeforeExpand: function (node) {
                    $('#tree').tree('options').url = "${cxt}/admin/menu/list?parentId=" + node.id;
                },
                onLoadError: function (result) {
                    alert(result.responseText);
                },
                onClick: function (node) {
                    if (node.id == 16) {
                        logout();
                    } else if (node.id == 15) {
                        openPasswordModifyDialog();
                    } else if (node.attributes.url) {
                        openTab(node);
                    }
                }
            });
            bindTabMenuEvent();
        });

        /**左边的菜单点击显示
         如果存在（即已经打开了），选中他
         如果不存在则打开他
         */
        function openTab(node) {
            if ($("#tabs").tabs("exists", node.text)) {
                $("#tabs").tabs("select", node.text);
            } else {
                url = "${cxt}/" + node.attributes.url;
                var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' " +
                    "src=" + url + "></iframe>";
                $("#tabs").tabs("add", {
                    title: node.text,
                    iconCls: node.iconCls,
                    closable: true,
                    content: content
                });
            }
            bindTabEvent();
        }

        bindTabEvent();

        function bindTabEvent() {
            $(".tabs-inner").dblclick(function () {
                var subtitle = $(this).children(".tabs-title").text();
                $('#tabs').tabs('close', subtitle);
            });
            $(".tabs-inner").bind('contextmenu', function (e) {
                $('#mm').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
                var subtitle = $(this).children(".tabs-title").text();
                $('#mm').data("currtab", subtitle);
                return false;
            });
        }

        function bindTabMenuEvent() {
            var temp = $('#tabs');
            $("#mm-tabrefresh").click(function () {
                var currtab_title = $('#mm').data("currtab");
                var frame = temp.tabs('getTab', currtab_title).find('iframe');
                frame.attr('src', frame.attr('src'));
            });
            //关闭当前
            $("#mm-tabclose").click(function () {
                var currtab_title = $('#mm').data("currtab");
                $('#tabs').tabs('close', currtab_title);
            });
            //全部关闭
            $('#mm-tabcloseall').click(function () {
                $('.tabs-inner span').each(function (i, n) {
                    var t = $(n).text();
                    $('#tabs').tabs('close', t);
                });
            });
            //关闭除当前之外的TAB
            $('#mm-tabcloseother').click(function () {
                var currtab_title = $('#mm').data("currtab");
                $('.tabs-inner span').each(function (i, n) {
                    var t = $(n).text();
                    if (t != currtab_title)
                        $('#tabs').tabs('close', t);
                });
            });
            //关闭当前右侧的TAB
            $('#mm-tabcloseright').click(function () {
                var nextall = $('.tabs-selected').nextAll();
                if (nextall.length == 0) {
                    //msgShow('系统提示','后边没有啦~~','error');
                    alert('后边没有啦~~');
                    return false;
                }
                nextall.each(function (i, n) {
                    var t = $('a:eq(0) span', $(n)).text();
                    $('#tabs').tabs('close', t);
                });
                return false;
            });
            //关闭当前左侧的TAB
            $('#mm-tabcloseleft').click(function () {
                var prevall = $('.tabs-selected').prevAll();
                if (prevall.length == 0) {
                    alert('到头了，前边没有啦~~');
                    return false;
                }
                prevall.each(function (i, n) {
                    var t = $('a:eq(0) span', $(n)).text();
                    $('#tabs').tabs('close', t);
                });
                return false;
            });

        }

        function openPasswordModifyDialog() {
            $("#dlg").dialog("open").dialog("setTitle", "修改密码");
            url = "${cxt}/admin/user/modifyPassword?id=${currentUser.id}";
        }

        function modifyPassword() {
            $("#fm").form("submit", {
                url: url,
                onSubmit: function () {
                    var newPassword = $("#newPassword").val();
                    var newPassword2 = $("#newPassword2").val();
                    if (!$(this).form("validate")) {
                        return false;
                    }
                    if (newPassword != newPassword2) {
                        $.messager.alert("系统提示", "确认密码输入错误！");
                        return false;
                    }
                    return true;
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统提示", "密码修改成功，请重新登录!");
                        resetValue();
                        $("#dlg").dialog("close");
                    } else {
                        $.messager.alert("系统提示", "密码修改失败！");
                        return;
                    }
                }
            });
        }

        //关闭修改密码窗口
        function closePasswordModifyDialog() {
            resetValue();
            $("#dlg").dialog("close");
        }

        //重置修改密码
        function resetValue() {
            $("#oldPassword").val("");
            $("#newPassword").val("");
            $("#newPassword2").val("");
        }

        //退出登录
        function logout() {
            $.messager.confirm("系统提示", "您确定要退出系统吗？", function (r) {
                if (r) {
                    window.location.href = '${cxt}/admin/logout';
                }
            });
        }

        function refreshSystem() {
            $.post("${cxt}/admin/system/refreshSystem", {}, function (result) {
                if (result.success) {
                    $.messager.alert("系统提示", "已成功刷新系统缓存！");
                } else {
                    $.messager.alert("系统提示", "刷新系统缓存失败！");
                }
            }, "json");
        }
    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height: 78px;background-color: #E0ECFF">
    <table style="padding: 5px" width="100%">
        <tr>
            <td width="50%">
                <img alt="logo" src="${cxt}/static/images/logo.png">
            </td>
            <td valign="bottom" align="right" width="50%">
                <font size="3">&nbsp;&nbsp;<strong>欢迎：</strong>${currentUser.username }</font>
            </td>
        </tr>
    </table>
</div>
<div region="center">
    <div class="easyui-tabs" fit="true" border="false" id="tabs">
        <div title="首页" data-options="iconCls:'icon-home'">
            <div align="center" style="padding-top: 100px"><font color="red" size="10">欢迎使用</font></div>
        </div>
    </div>
</div>
<!-- 加载左边菜单栏tree -->
<div region="west" style="width: 160px;padding: 5px;" title="导航菜单" split="true">
    <ul id="tree" class="easyui-tree"></ul>
</div>

<div region="south" style="height: 25px;padding: 5px" align="center">
    Copyright © 2012-2016 Java知识分享网 版权所有
</div>


<div id="dlg" class="easyui-dialog" style="width:400px;height:200px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>用户名：</td>
                <td><input type="text" id="userName" name="username" readonly="readonly"
                           value="${currentUser.username }" style="width: 200px"/></td>
            </tr>
            <tr>
                <td>新密码：</td>
                <td><input type="password" id="newPassword" name="newPassword" class="easyui-validatebox"
                           required="true" style="width: 200px"/></td>
            </tr>
            <tr>
                <td>确认新密码：</td>
                <td><input type="password" id="newPassword2" name="newPassword2" class="easyui-validatebox"
                           required="true" style="width: 200px"/></td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons" style="text-align:center">
    <a href="javascript:modifyPassword()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closePasswordModifyDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
<!-- 右键菜单 -->
<div id="mm" class="easyui-menu" style="width:150px;">
    <div id="mm-tabrefresh">刷新</div>
    <div class="menu-sep"></div>
    <div id="mm-tabclose">关闭</div>
    <div id="mm-tabcloseall">全部关闭</div>
    <div id="mm-tabcloseother">除此之外全部关闭</div>
    <div class="menu-sep"></div>
    <div id="mm-tabcloseright">当前页右侧全部关闭</div>
    <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
</div>

</body>
</html>