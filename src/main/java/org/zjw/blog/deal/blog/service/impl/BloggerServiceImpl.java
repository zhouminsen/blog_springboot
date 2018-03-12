package org.zjw.blog.deal.blog.service.impl;


import javax.annotation.Resource;

import org.zjw.blog.base.system.UserConstant;
import org.zjw.blog.base.vo.user.AuthUser;
import org.zjw.blog.deal.blog.dao.BloggerMapper;
import org.zjw.blog.deal.blog.entity.Blogger;
import org.zjw.blog.deal.blog.service.BloggerService;
import org.zjw.blog.deal.download.dao.FileRecordMapper;
import org.zjw.blog.deal.download.entity.FileRecord;
import org.zjw.blog.util.CryptographyUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService {

    @Resource
    private BloggerMapper bloggerMapper;

    @Resource
    private FileRecordMapper fileRecordMapper;

    public Blogger getById(Integer id) {
        return bloggerMapper.selectByPrimaryKey(id);
    }

    public int modifyPassword(String newPassword, Blogger blogger) {
        //md5加盐 加密
        newPassword = CryptographyUtil.md5(newPassword, blogger.getUsername());
        blogger.setPassword(newPassword);
        return bloggerMapper.updateByPrimaryKeySelective(blogger);
    }

    public int modify(Blogger blogger, Integer isChangePic) {
        int line = bloggerMapper.updateByPrimaryKeySelective(blogger);
        if (isChangePic == 1) {
            FileRecord f = new FileRecord();
            f.setFileName(blogger.getSubImageName());
            f.setFileAllUrl(UserConstant.USER_IMAGE_ADDR + "/" + blogger.getUsername() + "/" + blogger.getSubImageName());
            f.setFileDir(UserConstant.USER_IMAGE_PATH + "/" + blogger.getUsername());
            f.setCreateDate(new Date());
            f.setCreateUserId(blogger.getId());
            line = fileRecordMapper.insertSelective(f);
        }
        return line;
    }


}
