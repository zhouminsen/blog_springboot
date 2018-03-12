<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>-Powered by java1234</title>
    <link rel="stylesheet" href="${cxt}/static//bootstrap3/css/bootstrap.min.css">
    <link rel="stylesheet" href="${cxt}/static/bootstrap3/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${cxt}/static/css/blog.css">
    <link href="http://blog.java1234.com/favicon.ico" rel="SHORTCUT ICON">
    <script src="${cxt}/static/bootstrap3/js/jquery-1.11.2.min.js"></script>
    <script src="${cxt}/static/bootstrap3/js/bootstrap.min.js"></script>

    <script>
        var _hmt = _hmt || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "//hm.baidu.com/hm.js?aa5c701f4f646931bf78b6f40b234ef5";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>

    <style type="text/css">
        body {
            padding-top: 10px;
            padding-bottom: 40px;
        }
    </style>
</head>
<body>
<div class="container">
<#include "../foreground/common/head.ftl"/>

<#include "../foreground/common/menu.ftl"/>

    <div class="row">
        <div class="col-md-9">
            <#include "${displayPage}"></jsp:include>
        </div>

        <div class="col-md-3">
            <div class="data_list">
                <div class="data_list_title">
                    <img src="${cxt}/static/images/user_icon.png"/>
                    博主信息
                </div>
                <div class="user_image">
                    <img src="${blogger.allUrl}"/>
                </div>
                <div class="nickName">${blogger.nickName }</div>
                <div class="userSign">(${blogger.sign })</div>
                <a href="${cxt}/admin/index" target="_blank">进入后台</a>
            </div>

            <div class="data_list">
                <div class="data_list_title">
                    <img src="${cxt}/static/images/byType_icon.png"/>
                    按日志类别
                </div>
                <div class="datas">
                    <ul>
                    <#list typeList as type>
                        <li><span>
							<a href="${cxt}/index?typeId=${type.id }">${type.typeName }(${type.blogCount})</a>
						</span></li>
                    </#list>
                    </ul>
                </div>
            </div>

            <div class="data_list">
                <div class="data_list_title">
                    <img src="${cxt}/static/images/byDate_icon.png"/>
                    按日志日期
                </div>
                <div class="datas">
                    <ul>
                    <#list blogCountList as blogCount>
                        <li><span><a
                                href="${cxt}/index?releaseDate=${blogCount.releaseDate }">${blogCount.releaseDate }
                            (${blogCount.blogCount })</a></span></li>
                    </#list>
                    </ul>
                </div>
            </div>
            <div class="data_list">
                <div class="data_list_title">
                    <img src="${cxt}/static/images/link_icon.png"/>
                    友情链接
                </div>
                <div class="datas">
                    <ul>
                    <#list linkList as link>
                        <li><span><a href="${link.linkUrl }" target="_blank">${link.linkName }</a></span></li>
                    </#list>
                    </ul>
                </div>
            </div>

        </div>


    </div>

<#include "../foreground/common/foot.ftl"/>
</div>
</body>
</html>
