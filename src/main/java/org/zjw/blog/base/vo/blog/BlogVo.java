package org.zjw.blog.base.vo.blog;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;
import org.zjw.blog.deal.blog.entity.BlogComment;
import org.zjw.blog.deal.blog.entity.BlogType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 周家伟
 * @date 2016-7-20
 */
@Data
public class BlogVo extends BaseEntity {

	private static final long serialVersionUID = -7401811145982072853L;
	private Integer id;

	private String title;

	private String summary;

	private Date releaseDate;

	private String releaseDateStr;

	private Integer clickHit;

	private Integer replyHit;

	private Integer typeId;

	private String keyWord;

	private String content;

	private BlogType blogType;

	private Integer userId;

	private Integer deleteFlag;
	
	private Integer state;

	private List<BlogComment> commentList = new ArrayList<BlogComment>();



}