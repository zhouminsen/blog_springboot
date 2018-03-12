package org.zjw.blog.deal.permission.entity;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;
@Data
public class Role extends BaseEntity {


    private static final long serialVersionUID = -174781208654764070L;
    private Integer roleId;

    private String roleName;

    private String menuIds;

    private String operationIds;

    private String description;

}