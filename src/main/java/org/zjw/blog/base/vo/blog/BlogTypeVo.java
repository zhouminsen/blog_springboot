package org.zjw.blog.base.vo.blog;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;

import java.util.Date;

/**
 * 博客vo
 * 
 * @author 周家伟
 * @date 2016-8-2
 */
@Data
public class BlogTypeVo extends BaseEntity {

	private static final long serialVersionUID = 551559605876153123L;
	private Integer id;

	private String typeName;

	private Integer orderNo;

	private Date createDate;

	private Integer deleteFlag;

	private Integer BlogCount;

}
