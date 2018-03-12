package org.zjw.blog.deal.permission.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Operation implements Serializable{


    private static final long serialVersionUID = 3039127693066538454L;
    private Integer operationId;

    private String operationName;

    private Integer menuId;

    private String menuName;

    private Date createDate;

    private Integer deleteFlag;


}