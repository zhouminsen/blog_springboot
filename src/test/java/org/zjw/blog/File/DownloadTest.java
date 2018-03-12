package org.zjw.blog.File;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.blog.BaseTest;
import org.zjw.blog.base.system.UserConstant;
import org.zjw.blog.deal.download.dao.FileRecordMapper;
import org.zjw.blog.deal.download.entity.FileRecord;
import org.zjw.blog.deal.download.service.DownloadService;
import org.zjw.blog.util.FTPUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoum on 2017/1/22.
 */
public class DownloadTest   extends BaseTest {
    @Autowired
    private DownloadService downloadService;

    @Autowired
    private FileRecordMapper fileRecordMapper;
    @Autowired
    private FTPUtil ftpUtil;

    @Test
    public void add() {
        String[] imageArr = {"20170120154059.jpg", "20170120163800.jpg", "20170120164423.jpg", "20170120172315.jpg", "20170120175007.gif"};
        for (int i = 0; i < imageArr.length; i++) {
            String s = imageArr[i];
            FileRecord f = new FileRecord();
            f.setCreateDate(new Date());
            f.setCreateUserId(1);
            f.setDeleteFlag(0);
            f.setFileDir(UserConstant.USER_IMAGE_PATH);
            f.setFileAllUrl(UserConstant.USER_IMAGE_ADDR+"/zjw/"+s);
            f.setFileName(s);
            fileRecordMapper.insertSelective(f);
        }
    }

    @Test
    public void list() {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        List<FileRecord> list = downloadService.list(queryMap);
        for (FileRecord fileRecord : list) {
            System.out.println(fileRecord);
        }
    }

    @Test
    public void downloadFile() {
        ftpUtil.downloadFile("haha", "/opt");
    }
}
