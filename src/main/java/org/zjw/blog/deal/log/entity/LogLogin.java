package org.zjw.blog.deal.log.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LogLogin implements Serializable {


    private static final long serialVersionUID = -5070557768507949375L;

    private Integer id;

    private String username;

    private String password;

    private Integer status;

    private Integer type;

    private String description;

    private String ipAddress;

    private Date createDate;
    private String deleteFlag;


}