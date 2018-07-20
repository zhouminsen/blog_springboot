package org.zjw.blog.deal.blog.entity;
/**
 * 
 * @author 周家伟
 * @date 2016-7-16
 */

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Data
public class BlogComment implements Serializable {

    private static final long serialVersionUID = 8625008929263829853L;
    @Id
    private Integer id;

    private String userIp;

    private Integer blogId;

    private String content;

    private Date commentDate;

    private Integer state;



	
    
}