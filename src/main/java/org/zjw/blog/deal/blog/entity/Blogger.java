package org.zjw.blog.deal.blog.entity;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;
import org.zjw.blog.base.system.UserConstant;

/**
 * @author 周家伟
 * @date 2016-7-16
 */
@Data
public class Blogger extends BaseEntity {
    private Integer id;

    private String username;

    private String password;

    private String nickName;

    private String sign;

    private String imageName;

    private String profile;

    private String subImageName;


    /**
     * 获得头像图片全路径
     *
     * @return
     */
    public String getAllUrl() {
        return "/blog/static/userImages/" + imageName;
    }

    /**
     * 获得副头像全路径
     *
     * @return
     */
    public String getSubAllUrl() {
        return UserConstant.USER_IMAGE_ADDR + "/" + username + "/" + subImageName;
    }

}