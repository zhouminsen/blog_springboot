package org.zjw.blog.deal.log.entity;


import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "log_login")
public class LogLogin extends BaseEntity {


    private static final long serialVersionUID = -5070557768507949375L;
    @Id
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;

    @Column(name = "type")
    private Integer type;

    @Column(name = "description")
    private String description;

    @Column(name = "ipAddress")
    private String ipAddress;

//    @Column(name = "deleteFlag")
//    private Integer deleteFlag;


}