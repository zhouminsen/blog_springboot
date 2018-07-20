package org.zjw.blog.deal.blog.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 周家伟
 * @date 2016-7-16
 */

@Data
public class BlogType implements Serializable {


	private static final long serialVersionUID = 2775486934139501586L;

	private Integer id;

	private String typeName;

	private Integer orderNo;

	private Date createDate;
	private String deleteFlag;


}