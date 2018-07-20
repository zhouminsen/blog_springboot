package org.zjw.blog.deal.permission.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {


    private static final long serialVersionUID = -174781208654764070L;
    private Integer roleId;

    private String roleName;

    private String menuIds;

    private String operationIds;

    private String description;

    private String createDate;
    private String deleteFlag;
}