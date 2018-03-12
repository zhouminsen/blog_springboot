package org.zjw.blog.deal.log.entity;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;

@Data
public class LogError extends BaseEntity {


    private static final long serialVersionUID = 8871076609570115355L;
    private Integer id;

    private String ipAddress;

    private String className;

    private String methodName;

    private String parameter;

    private String exceptionInfo;

    private String sqlContent;

    private String tableName;

    private Integer userId;


    private String moduleName;

}