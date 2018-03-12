package org.zjw.blog.deal.blog.entity;


import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;

/**
 * 
 * @author 周家伟
 * @date 2016-7-16
 */

@Data
public class BlogType extends BaseEntity {
	private Integer id;

	private String typeName;

	private Integer orderNo;



}