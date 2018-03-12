<!DOCTYPE html>

<div class="data_list">
    <div class="data_list_title">
        <img src="${cxt}/static/images/list_icon.png"/>
        最新博客
    </div>
    <div class="datas">
        <ul>
        <#list blogList as blog>
            <li style="margin-bottom: 30px">
				  	<span class="date"><a href="${cxt}/blog/detail/${blog.id}">
				  		${blog.releaseDate?string("yyyy年MM月dd日")}"</a></span>
                <span class="title"><a href="${cxt}/blog/detail/${blog.id}">${blog.title }</a></span>
                <span class="summary">摘要: ${blog.summary }...</span>
                <span class="img">
                    <#list blog.imgList as image>
                        <a href="${cxt}/blog/detail/${blog.id}">${image }</a>
					  		&nbsp;&nbsp;
                    </#list>
				  	</span>
                <span class="info">发表于 ${blog.releaseDate?string("yyyy-MM-dd HH:mm") } 阅读(${blog.clickHit}) 评论(${blog.replyHit}) </span>
            </li>
            <hr style="height:5px;border:none;border-top:1px dashed gray;padding-bottom:  10px;"/>
        </#list>
        </ul>
    </div>
</div>

<div>
    <nav>
        <ul class="pagination pagination-sm">
        ${page.url}
        </ul>
    </nav>
</div>