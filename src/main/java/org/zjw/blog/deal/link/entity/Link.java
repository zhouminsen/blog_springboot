package org.zjw.blog.deal.link.entity;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;

import java.util.Date;

/**
 * 
 * @author 周家伟
 * @date 2016-7-16
 */
@Data
public class Link extends BaseEntity {

    private static final long serialVersionUID = -4658362516367090087L;
    private Integer id;

    private String linkName;

    private String linkUrl;

    private Integer orderNo;
    
    private Date createDate;
    
    private Integer deleteFlag;



	
}