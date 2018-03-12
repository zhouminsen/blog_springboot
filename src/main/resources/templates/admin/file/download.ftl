<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>文件下载管理页面</title>
    <link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${cxt}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${cxt}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

    <script type="text/javascript">
        //datetimebox的扩展
        $.extend($.fn.datagrid.defaults.editors, {
            datetimebox: {// datetimebox就是你要自定义editor的名称
                init: function (container, options) {
                    var input = $('<input class="easyuidatetimebox">').appendTo(container);
                    return input.datetimebox({
                        formatter: function (date) {
                            return new Date(date).format("yyyy-MM-dd hh:mm:ss");
                        }
                    });
                },
                getValue: function (target) {
                    return $(target).parent().find('input.combo-value').val();
                },
                setValue: function (target, value) {
                    $(target).datetimebox("setValue", value);
                },
                resize: function (target, width) {
                    var input = $(target);
                    if ($.boxModel == true) {
                        input.width(width - (input.outerWidth() - input.width()));
                    } else {
                        input.width(width);
                    }
                }
            }
        });
        // 时间格式化
        Date.prototype.format = function (format) {
            /*
             * eg:format="yyyy-MM-dd hh:mm:ss";
             */
            if (!format) {
                format = "yyyy-MM-dd hh:mm:ss";
            }

            var o = {
                "M+": this.getMonth() + 1, // month
                "d+": this.getDate(), // day
                "h+": this.getHours(), // hour
                "m+": this.getMinutes(), // minute
                "s+": this.getSeconds(), // second
                "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
                "S": this.getMilliseconds()
                // millisecond
            };

            if (/(y+)/.test(format)) {
                format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            }

            for (var k in o) {
                if (new RegExp("(" + k + ")").test(format)) {
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
                }
            }
            return format;
        };
        //validatebox增加对time、date、datetime的验证
        $.extend($.fn.validatebox.defaults.rules, {
            /*13:04:06*/
            time: {
                validator: function (value) {
                    var a = value.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
                    if (a == null) {
                        return false;
                    } else if (a[1] > 24 || a[3] > 60 || a[4] > 60) {
                        return false;
                    }
                },
                message: '时间格式不正确，请重新输入。'
            },
            /*2014-01-01*/
            date: {
                validator: function (value) {
                    var r = value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
                    if (r == null) {
                        return false;
                    }
                    var d = new Date(r[1], r[3] - 1, r[4]);
                    return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]);
                },
                message: '时间格式不正确，请重新输入。'
            },
            /*2014-01-01 13:04:06*/
            datetime: {
                validator: function (value) {
                    var r = value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
                    if (r == null) return false;
                    var d = new Date(r[1], r[3] - 1, r[4], r[5], r[6], r[7]);
                    return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4] && d.getHours() == r[5] && d.getMinutes() == r[6] && d.getSeconds() == r[7]);
                },
                message: '时间格式不正确，请重新输入。'
            }
        });
        $(function () {
            var flag = true;//该变量用于判断，是否可以新增行，为true可以新增，false则不允许
            var index;//用于保存编辑行的下标
            var method;//用于保存，做的是什么操作
            $("#dg").datagrid({
                url: "${cxt}/admin/download/list?v=" + Math.random(),
                //数据表格的数据
                title: "文件下载",
                fitColumns: true,//占满父容器的宽度
                striped: true, //显示条纹，奇偶行背景色不同
                rownumbers: true,//显示每一行的行号
                pagination: true,//显示分页栏
                pageList: [1, 2, 3, 5, 10, 20, 50],//每页数据size选项
                pageSize: 20,//默认每页数据显示size
                loadMsg: "正在加载中........",//数据加载时的消息
                idField: "id",//标识字段
                rowStyler: function (index, row) {//行级样式
                    if (row.blogCount >= 10) {
                        return "background:orange";
                    }
                },
                toolbar: [
                    {
                        text: "新增", iconCls: "icon-add", handler: function () {
                        if (flag) {
                            method = "save";
                            //1、把dg渲染成datagrid，并且调用它的appendRow方法，在表格的最后一行，追加一个新行
                            $("#dg").datagrid("appendRow", {});

                            //2、得到新增的行的下标 ------getRows可以得到所有行的数组
                            index = $("#dg").datagrid("getRows").length - 1;

                            //3、让指定行处于编辑状态，用户就可 以输入数据
                            $("#dg").datagrid("beginEdit", index);

                            //4、改变flag,防止没有保存，就添加
                            flag = false;
                        } else {
                            $.messager.alert("提示", "需要先保存数据，才能再次添加数据", "info");
                        }
                    }
                    },
                    {
                        text: "删除", iconCls: "icon-remove", handler: function () {
                        if (flag) {
                            var rows = $("#dg").datagrid("getSelections");
                            if (rows.length <= 0) {
                                $.messager.alert("提示", "请选择要删除的数据", "info");
                                return;
                            }
                            $.messager.confirm("删除", "你确定要删除吗?", function (t) {
                                if (t) {
                                    var ids = "";
                                    $(rows).each(function (i, e) {
                                        ids += e.id + ",";
                                    });
                                    $.post("${cxt}/admin/download/delete?v=" + Math.random(), {"ids": ids}, function (result) {
                                        if (result) {
                                            $.messager.alert("提示", "删除数据成功", "info");
                                            //删除成功后将选择的行取消选中,不然该行依然被选中状态(bug)
                                            //$("#dg").datagrid("uncheckAll").datagrid("reload");
                                            $("#dg").datagrid("unselectAll").datagrid("reload");
                                        } else {
                                            $.messager.alert("提示", "删除数据失败", "info");
                                        }
                                    }).error(function (result) {
                                        $.messager.alert("提示", result.responseText, "info");
                                    });
                                }
                            });
                        } else {
                            $.messager.alert("提示", "请先保存在操作", "info");
                        }
                    }
                    },
                    {
                        text: "修改", iconCls: "icon-edit", handler: function () {
                        if (flag) {
                            //1、获得用户选中的所有的行，修改时，只允许一行被选中----------------返回数组
                            var rows = $("#dg").datagrid("getSelections");
                            //2、判断有几条被中
                            if (rows.length == 0) {
                                $.messager.alert("提示", "请选择你要修改的记录!", "info");
                            } else if (rows.length > 1) {
                                $.messager.alert("提示", "只能选择一条记录进行修改!", "info");
                            } else {
                                method = "modify";
                                //只选中了一行
                                var row = rows[0];
                                //根据当前选中的行，得到它所对应的下标
                                index = $("#dg").datagrid("getRowIndex", row);
                                //让这一行处于编辑状态
                                $("#dg").datagrid("beginEdit", index);
                                flag = false;//变成false,在没有保存之前，不能再次更改其他数据
                            }
                        } else {
                            $.messager.alert("提示", "请先保存之前的修改!", "info");
                        }
                    }
                    },
                    {
                        text: "查询", iconCls: "icon-search", handler: function () {
                        //展开查询面板
                        $("#searchDiv").panel("expand");
                        //如果点击面板中的查询按钮，应该取出条件，到后台重新加载数据
                        $("#searchBtn").click(function () {
                            var startDate = $("#startDate").datebox("getValue");
                            var endDate = $("#endDate").datebox("getValue");
                            var typeName = $("#typeName").val();
                            var deleteFlag = $("#deleteFlag").val();
                            $("#dg").datagrid("load", {
                                "startDate": startDate,
                                "endDate": endDate,
                                "typeName": typeName,
                                "deleteFlag": deleteFlag
                            });
                        });
                        $("#cancelBtn").click(function () {
                            $(":input", "#form1")
                                    .not(':button, :submit, :reset, :hidden')
                                    .val('')
                                    .removeAttr('checked')
                                    .removeAttr('selected');
                        });
                    }
                    },
                    {
                        text: "保存", iconCls: "icon-save", handler: function () {
                        //结束编辑前，判断，用于在控件输入的数据是否能够通过验证，通过返回true,不通过返回false
                        var result = $("#dg").datagrid("validateRow", index);
                        if (result) {
                            //结束对一行的编辑
                            $("#dg").datagrid("endEdit", index);
                            flag = true;//改为ture,就可以再次新增
                        } else {
                            $.messager.alert("提示", "你输入的数据不完整，请检查!", "info");
                        }
                    }
                    },
                    {
                        text: "取消", iconCls: "icon-cancel", handler: function () {
                        //让数据回滚
                        $("#dg").datagrid("rejectChanges");
                        flag = true;
                    }
                    }
                ],
                //row表示，结束编辑的这一行数据
                onAfterEdit: function (index, row) {
                    //直接把数据更新到服务器
                    if (method == "save") {
                        $.post("${cxt}/admin/download/save?v=" + Math.random(), row, function (result) {
                            var result = eval("(" + result + ")");
                            if (result.success) {
                                $.messager.alert("系统提示", "添加数据成功");
                                $("#dg").datagrid("reload");
                            } else {
                                $.messager.alert("系统提示", "添加数据失败");
                            }
                        }).error(function (result) {
                            $.messager.alert("系统提示", result.responseText);
                        });
                    } else {
                        $.ajax({
                            url: "${cxt}/admin/download/modify?v=" + Math.random(),
                            data: row,
                            dataType: "json",
                            type: "post",
                            success: function (result) {
                                if (result.success) {
                                    $.messager.alert("系统提示", "数据修改成功！");
                                    $("#dg").datagrid("reload");
                                } else {
                                    $.messager.alert("系统提示", "数据修改失败!");
                                }
                            }, error: function (result) {
                                $.messager.alert(result.responseText);
                            }
                        });
                    }
                },
                columns: [[
                    {field: "id", title: "编号", width: 100, align: "center", checkbox: true},//列的属性
                    {
                        field: "fileName", title: "文件名称", width: 100, align: "center",
                        formatter: function (val, row, index) {
                            return "<a href='${cxt}/admin/download/download?fileName="+val+"'>"+val+"</a>";
                        },
                        editor: {
                            type: "validatebox",
                            options: {
                                required: true,
                                missingMessage: "请输入姓名"
                            }
                        }
                    },
                    {
                        field: "fileAllUrl", title: "预览", width: 100, align: "center",
                        editor: {
                            type: "validatebox",
                            options: {
                                required: true,
                                missingMessage: "请输入姓名"
                            }
                        }
                    },
                    {
                        field: "createDate", title: "创建日期", width: 100, align: "center", sortable: true,
                        editor: {
                            type: "datetimebox",
                            options: {
                                required: true,
                                missingMessage: "请选择正确时间"
                            }
                        }
                    },
                    {
                        field: "deleteFlag", title: "删除标记", width: 100, align: "center",
                        editor: {
                            type: "combobox",
                            options: {
                                data: [
                                    {key: "1", text: "未删除"},
                                    {key: "0", text: "已删除"}
                                ],
                                valueField: "key",
                                textField: "text",
                                required: true,
                                editable: false,
                                missingMessage: "请选择"
                            }
                        }
                    }
                ]],
                onLoadError: function (data) { //加载数据失败后
                    alert(data.responseText);
                }
            });
        });

    </script>
</head>
<body style="margin: 1px">
<form method="post" id="form1">
    <div class="easyui-panel" title="查询数据" collapsible="true" collapsed="true" id="searchDiv">
        <table>
            <Tr>
                <td>文件</td>
                <td><input type="text" id="typeName" name="typeName"/></td>
                <td>创建日期</td>
                <td><input type="text" id="startDate" name="startDate" class="easyui-datebox"/>至</td>
                <td><input type="text" id="endDate" name="endDate" class="easyui-datebox"/></td>
                <td>删除标志</td>
                <td>
                    <select id="deleteFlag" name="deleteFlag">
                        <option value="">选择全部</option>
                        <option value="1">未删除</option>
                        <option value="0">已删除</option>
                    </select>
                    &nbsp;&nbsp;
                </td>
                <Td>
                    <a class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>
                </Td>
                <Td>
                    <a class="easyui-linkbutton" iconCls="icon-cancel" id="cancelBtn">清空</a>
                </Td>
            </Tr>
        </table>
    </div>
</form>
<table id="dg">
</table>
</body>
</html>