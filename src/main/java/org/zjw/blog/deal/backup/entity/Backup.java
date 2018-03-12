package org.zjw.blog.deal.backup.entity;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;

import java.util.Date;
@Data
public class Backup extends BaseEntity {


    private static final long serialVersionUID = 2851849546659047096L;

    private Integer id;

    private String backupName;

    private String backupType;

    private String backupPath;

    private Date createDate;

    private Integer deleteFlag;

}