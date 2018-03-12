package org.zjw.blog.File;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.zjw.blog.util.FTPUtil;
import org.zjw.blog.util.file.FileUtil;
import org.zjw.blog.util.file.POIUitl;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileTest  {

    @Resource
    private FTPUtil ftpUtil;

    @Test
    public void mkdirs() throws IOException {
        String filePath =   "E:/log/logLogin";
        File file = new File(filePath, "周家伟" + ".xls");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }

    /**
     * 如果该文件没有父目录的话创建会报错
     *
     * @throws IOException
     */
    @Test
    public void mkdirs2() throws IOException {
        String filePath =  "E:/log/aaa/xx.txt";
        File file = new File(filePath);
        file.createNewFile();
    }

    @Test
    public void delete() {
        //D:\tomcat\apache-tomcat-8.0.26\webapps\Blog\html
        String path = "D:\\tomcat\\apache-tomcat-8.0.26\\webapps\\Blog\\html\\blogDetail41.html";
        FileUtil.deleteFile(path);
    }

    @Test
    public void newFile() throws IOException {
        FileUtil fileUtil = new FileUtil();
        File file = fileUtil.createNewFile("d:/ee", "haha.txt");
        System.out.println(file.getName());
        System.out.println(FileUtil.getNameWithoutExtension(file.getPath()));
    }

    @Test
    public void getFileBytes() {
        byte[] bs = FileUtil.getFileBytes(new File("C:\\Users\\Administrator\\Desktop\\sqlserver.sql"));
        System.out.println(bs.length);
    }

    @Test
    public void downloadImage() throws IOException {
        FTPClient ftp = new FTPClient();
        ftp.connect(ftpUtil.getHost(), ftpUtil.getPort());
        if (!ftp.login(ftpUtil.getUser(), ftpUtil.getPassword())) {
            ftp.disconnect();
            System.err.println("FTP server refused connection.");
            System.exit(1);
        }

        ftp.enterLocalPassiveMode();
        //路径是相对路径
        InputStream is = ftp.retrieveFileStream(new String("/home/ftpuser/image/zjw/20170120172315.jpg".getBytes("UTF-8"), "ISO-8859-1"));
        OutputStream os = new FileOutputStream("C:\\Users\\zhoum\\Desktop\\bb.jpg");
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        os.close();
        ftp.logout();
        if (ftp.isConnected()) {
            ftp.disconnect();
        }
    }

    @Test
    public void  exportExcel() throws Exception {
        OutputStream os = new FileOutputStream("C:\\Users\\zhoum\\Desktop\\bb.xls");
        List<String> titleRow = Arrays.asList("呵呵", "哈哈");
        List<List<String>> bodyRow = new ArrayList<>();
        List<String> contents = Arrays.asList("你说什么","我听不到","呵呵呵","你去啊");
        for (int i = 0; i <4 ; i++) {
            bodyRow.add(contents);
        }
        POIUitl.createExcel(os, "bb", "cc",titleRow, bodyRow);
        os.flush();
        os.close();
//        SortedMap sortedMap
    }

}
