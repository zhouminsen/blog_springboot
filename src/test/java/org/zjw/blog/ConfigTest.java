package org.zjw.blog;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.blog.util.FTPUtil;
import org.zjw.blog.util.ZJW;

/**
 * Created by zhoum on 2018/3/11.
 */

public class ConfigTest extends BaseTest {

    @Autowired
    private FTPUtil ftpUtil;

    @Autowired
    private ZJW zjw;

    @Test
    public void bb() {

        System.out.println(ftpUtil);

        System.out.println(zjw);
    }
}