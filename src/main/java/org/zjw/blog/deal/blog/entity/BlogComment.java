package org.zjw.blog.deal.blog.entity;
/**
 * 
 * @author 周家伟
 * @date 2016-7-16
 */
import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;

import javax.persistence.Id;
import java.util.Date;
@Data
public class BlogComment extends BaseEntity{

    private static final long serialVersionUID = 8625008929263829853L;
    @Id
    private Integer id;

    private String userIp;

    private Integer blogId;

    private String content;

    private Date commentDate;

    private Integer state;



	
    
}