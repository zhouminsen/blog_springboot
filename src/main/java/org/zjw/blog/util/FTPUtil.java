package org.zjw.blog.util;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author zhoujiawei
 */
@Data
@Component
@ConfigurationProperties(prefix = "ftp")
public class FTPUtil {
    private static transient Log logger = LogFactory.getLog(FTPUtil.class);
    //ftp主机ip
    private String host;
    //ftp端口号
    private Integer port;
    //ftp用户名
    private String user;
    //ftp密码
    private String password;
    //ftp根目录
    private String pathname;

    /**
     * 上传文件
     *
     * @param pathname    FTP服务器保存目录ps:绝对路径
     * @param fileName    上传到FTP服务器后的文件名称
     * @param inputStream 输入文件流
     * @return
     */
    public boolean uploadFile(String pathname, String fileName, InputStream inputStream) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        try {
            //连接FTP服务器
            ftpClient.connect(host, port);
            //登录FTP服务器
//            ftpClient.login(user, password);
            //是否成功登录FTP服务器
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
            flag = ftpClient.storeFile(fileName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    inputStream.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


    /**
     * 上传文件（可对文件进行重命名）
     *
     * @param pathname       FTP服务器保存目录
     * @param filename       上传到FTP服务器后的文件名称
     * @param originfilename 待上传文件的名称（绝对地址）
     * @return
     */
    public boolean uploadFileFromProduction(String pathname, String filename, String originfilename) {
        boolean flag = false;
        try {
            InputStream inputStream = new FileInputStream(new File(originfilename));
            flag = uploadFile(pathname, filename, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除文件
     *
     * @param pathname FTP服务器保存目录
     * @param filename 要删除的文件名称
     * @return
     */
    public boolean deleteFile(String pathname,
                              String filename) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        try {
            //连接FTP服务器
            ftpClient.connect(host, port);
            //登录FTP服务器
            ftpClient.login(user, password);
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                } catch (IOException e) {

                }
            }
        }
        return flag;
    }

    /**
     * 下载文件
     *
     * @param filename  文件名称
     * @param localpath 下载后的文件路径
     * @return
     */
    public File downloadFile(String filename, String localpath) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        File file = null;
        try {
            //连接FTP服务器
            ftpClient.connect(host, port);
            //登录FTP服务器
            ftpClient.login(user, password);
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return null;
            }
            logger.error("连接FTP成功");
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile ftpFile : ftpFiles) {
                if (filename.endsWith("ACOMA")) {
                    Date time = ftpFile.getTimestamp().getTime();
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                    String format = sd.format(time);
                    String format2 = sd.format(new Date());
                    if (Objects.equals(format, format2)) {
                        file = File.createTempFile(ftpFile.getName(), "ACOMA");
                        OutputStream os = new FileOutputStream(file);
                        ftpClient.retrieveFile(ftpFile.getName(), os);

//                    ftpClient.retrieveFileStream()

                        os.close();
                        logger.info("下载完毕----");
                        flag = true;
                    }
                }
            }
            ftpClient.logout();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public boolean downloadFile(String filename, String localpath, HttpServletResponse response) throws UnsupportedEncodingException {
        response.reset();
        //设置响应类型	PDF文件为"application/pdf"，WORD文件为："application/msword"， EXCEL文件为："application/vnd.ms-excel"。
        response.setContentType("application/octet-stream;charset=utf-8");

        //设置响应的文件名称,并转换成中文编码
        //returnName = URLEncoder.encode(returnName,"UTF-8");
        filename = response.encodeURL(new String(filename.getBytes(), "iso8859-1"));    //保存的文件名,必须和页面编码一致,否则乱码

        //attachment作为附件下载；inline客户端机器有安装匹配程序，则直接打开；注意改变配置，清除缓存，否则可能不能看到效果
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);

//        return downloadFile(filename, localpath);
        return false;
    }
}
