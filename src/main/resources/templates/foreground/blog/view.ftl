<!DOCTYPE html>
<!-- 仅作为模板使用,服务端不要直接返回 -->
<html xmlns:th="http://www.thymeleaf.org">
<script type="text/javascript" src="${cxt}/static/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<link rel="stylesheet" href="${cxt}/static/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
<script type="text/javascript">
    SyntaxHighlighter.all();
</script>
<script type="text/javascript">
    function loadimage() {
        document.getElementById("randImage").src = "${cxt}/foreground/blogCommentVerifyCode.jsp?" + Math.random();
    }

    function submitData() {
        var content = $("#content").val();
        var imageCode = $("#imageCode").val();
        if (content == null || content == '') {
            alert("请输入评论内容！");
        } else if (imageCode == null || imageCode == '') {
            alert("请填写验证码！");
        } else {
            $.post("${cxt}/comment/save", {
                'content': content,
                'imageCode': imageCode,
                'blogId': '${blog.id}'
            }, function (result) {
                if (result.success) {
                    window.location.reload();
                    alert("评论已提交成功，审核通过后显示！");
                } else {
                    alert(result.errorInfo);
                }
            }, "json");
        }
    }

    function showOtherComment() {
        $('.otherComment').show();
    }
</script>
<div class="data_list">
    <div class="data_list_title">
        <img src="${cxt}/static/images/blog_show_icon.png"/>
        博客信息
    </div>
    <div>
        <div class="blog_title"><h3><strong>${blog.title }</strong></h3></div>
        <div style="padding-left: 330px;padding-bottom: 20px;padding-top: 10px">
            <div class="bshare-custom"><a title="分享到QQ空间" class="bshare-qzone"></a><a title="分享到新浪微博"
                                                                                      class="bshare-sinaminiblog"></a><a
                    title="分享到人人网" class="bshare-renren"></a><a title="分享到腾讯微博" class="bshare-qqmb"></a><a
                    title="分享到网易微博" class="bshare-neteasemb"></a><a title="更多平台"
                                                                    class="bshare-more bshare-more-icon more-style-addthis"></a><span
                    class="BSHARE_COUNT bshare-share-count">0</span></div>
            <script type="text/javascript" charset="utf-8"
                    src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=1&amp;lang=zh"></script>
            <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
        </div>
        <div class="blog_info">
            发布时间：『
        ${blog.releaseDate?string('yyyy-MM-dd HH:mm') }』
            &nbsp;&nbsp;博客类别：${blog.blogType.typeName}&nbsp;&nbsp;阅读(${blog.clickHit}) 评论(${blog.replyHit})
        </div>
        <div class="blog_content">
        ${blog.content }
        </div>
        <div class="blog_keyWord">
            <font><strong>关键字：</strong></font>
        <#if !keyWords??>
            &nbsp;&nbsp;无
        <#else>
            <#list keyWords as keyWord>
                &nbsp;&nbsp;<a href="${cxt}/blog/q?q=${keyWord}" target="_blank">${keyWord }</a>&nbsp;&nbsp;
            </#list>
        </#if>
        </div>
        <div class="blog_lastAndNextPage">
        ${pageCode! }
        </div>
    </div>
</div>
<div class="data_list">
    <div class="data_list_title">
        <img src="${cxt}/static/images/comment_icon.png"/>
        评论信息
    <#if (commentList?size >10)>
        <a href="javascript:showOtherComment()" style="float: right;padding-right: 40px;">显示所有评论</a>
    </#if>
    </div>
    <div class="commentDatas">
    <#if (commentList?size==0)>
        暂无评论
    <#else >
        <#list commentList as comment>
            <#if comment_index<10 >
                <div class="comment">
                                <span><font>${comment_index+1 }
                                    楼&nbsp;&nbsp;&nbsp;&nbsp;${comment.userIp }：</font>${comment.content }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;
                                ${comment.commentDate?string("yyyy-MM-dd HH:mm") }&nbsp;]</span>
                </div>
            <#else>
                <div class="otherComment">
                    <div class="comment">
                                    <span><font>${comment_index.index+1 }
                                        楼&nbsp;&nbsp;&nbsp;&nbsp;${comment.userIp }：</font>${comment.content }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;
                                    ${comment.commentDate?string("yyyy-MM-dd HH:mm") }&nbsp;]</span>
                    </div>
                </div>
            </#if>
        </#list>
    </#if>
    </div>
</div>

<div class="data_list">
    <div class="data_list_title">
        <img src="${cxt}/static/images/publish_comment_icon.png"/>
        发表评论
    </div>
    <div class="publish_comment">
        <div>
            <textarea style="width: 100%" rows="3" id="content" name="content" placeholder="来说两句吧..."></textarea>
        </div>
        <div class="verCode">
            验证码：<input type="text" value="${imageCode! }" name="imageCode" id="imageCode" size="10"
                       onkeydown="if(event.keyCode==13)form1.submit()"/>&nbsp;
            <img onclick="javascript:loadimage();" title="换一张试试" name="randImage" id="randImage"
                 src="" width="60" height="20" border="1" align="absmiddle">
        </div>
        <div class="publishButton">
            <button class="btn btn-primary" type="button" onclick="submitData()">发表评论</button>
        </div>
        </form>
    </div>
</div>