package org.zjw.blog.deal.permission.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Menu implements Serializable{

    private static final long serialVersionUID = 3183625941690548114L;
    private Integer menuId;

    private String menuName;

    private String menuUrl;

    private Integer parentId;

    private String description;

    private String state;

    private String iconCls;

    private Integer seq;

    private Date createDate;

    private Integer deleteFlag;


}