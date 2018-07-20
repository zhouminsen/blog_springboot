package org.zjw.blog.deal.log.entity;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;

import java.util.Date;

@Data
public class LogOperation extends BaseEntity{


    private static final long serialVersionUID = -9043179055524185457L;

    private Integer id;

    /**
     * 用户ip
     */
    private String ipAddress;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数
     */
    private String parameter;

    /**
     * 是否成功
     */
    private Integer success;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * sql语句
     */
    private String sqlContent;

    /**
     * 数据表名
     */
    private String tableName;

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 操作内容
     */
    private String operationContent;

    private Date createDate;
    private Integer deleteFlag;


}