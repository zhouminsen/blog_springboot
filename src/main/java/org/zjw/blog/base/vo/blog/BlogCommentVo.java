package org.zjw.blog.base.vo.blog;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;
import org.zjw.blog.deal.blog.entity.Blog;

import java.util.Date;

/**
 * @author 周家伟
 * @date 2016-7-20
 */
@Data
public class BlogCommentVo extends BaseEntity {

	private static final long serialVersionUID = 4778802922804432807L;

	private Integer id;

	private String userIp;

	private Integer blogId;

	private String content;

	private Date commentDate;

	private Integer state;

	private Blog blog;




}
