package org.zjw.blog.base.vo.blog;

import java.util.ArrayList;
import java.util.List;

import org.zjw.blog.base.common.entity.BaseEntity;

/**
 * lucene搜索结果实体类
 * @author 周家伟
 * @date 2016-7-20
 */
public class BlogLuceneVo extends BaseEntity {
	private String id;
	private String title;
	private String content;
	private String releaseDate;
	private String summary;
	private String clickHit;
	private String replyHit;
	private List<String> imgList = new ArrayList<String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getClickHit() {
		return clickHit;
	}

	public void setClickHit(String clickHit) {
		this.clickHit = clickHit;
	}

	public String getReplyHit() {
		return replyHit;
	}

	public void setReplyHit(String replyHit) {
		this.replyHit = replyHit;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "TBlogLuceneVo [id=" + id + ", title=" + title + ", content="
				+ content + ", imgList=" + imgList + "]";
	}

}
