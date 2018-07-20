package org.zjw.blog.deal.log.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LogError implements Serializable {


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

    private Date createDate;

    private Integer deleteFlag;


    private String moduleName;

}