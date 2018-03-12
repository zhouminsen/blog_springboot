package org.zjw.blog.deal.download.entity;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;

import java.util.Date;

@Data
public class FileRecord extends BaseEntity {

    private static final long serialVersionUID = -237907798422149143L;
    private Integer id;

    private String fileName;

    private Date createDate;

    private Date updateDate;

    private Integer createUserId;

    private Integer updateUserId;

    private String fileAllUrl;

    private String fileDir;

    private Integer deleteFlag;


}