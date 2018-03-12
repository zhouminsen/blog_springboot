package org.zjw.blog.util.file;

import java.io.*;

public class DownloadUtil {

    /**
     * @param filePath   要下载的文件路径
     * @param returnName 返回的文件名
     * @param os   HttpServletResponse
     * @param delFlag    是否删除文件
     */
    protected void download(String filePath, String returnName, OutputStream os, boolean delFlag) {
        this.prototypeDownload(new File(filePath), returnName, os, delFlag);
    }


    /**
     * @param file       要下载的文件
     * @param returnName 返回的文件名
     * @param os   HttpServletResponse
     * @param delFlag    是否删除文件
     */
    protected void download(File file, String returnName, OutputStream os, boolean delFlag) {
        this.prototypeDownload(file, returnName, os, delFlag);
    }

    /**
     * @param file       要下载的文件
     * @param returnName 返回的文件名
     * @param os   HttpServletResponse
     * @param delFlag    是否删除源文件
     */
    public void prototypeDownload(File file, String returnName, OutputStream  os, boolean delFlag) {
        // 下载文件
        FileInputStream inputStream = null;
        if (!file.exists()) return;
        //将文件读入响应流
        try {
            inputStream = new FileInputStream(file);
            prototypeDownload(inputStream, returnName, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (delFlag) {
            file.delete();
        }
    }

    /**
     * @param returnName 返回的文件名
     * @param os   HttpServletResponse
     */
    public void prototypeDownload(InputStream is, String returnName, OutputStream  os) {
        try {
            int length = 1024;
            int readLength = 0;
            byte buf[] = new byte[1024];
            readLength = is.read(buf, 0, length);
            while (readLength != -1) {
                os.write(buf, 0, readLength);
                readLength = is.read(buf, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * by tony 2013-10-17
     *
     * @param byteArrayOutputStream 将文件内容写入ByteArrayOutputStream
     * @param os              HttpServletResponse	写入response
     * @param returnName            返回的文件名
     */
    public void download(ByteArrayOutputStream byteArrayOutputStream, OutputStream os, String returnName) throws IOException {
        byteArrayOutputStream.writeTo(os);                    //写到输出流
        byteArrayOutputStream.close();                                    //关闭
        os.flush();                                            //刷数据
    }
}
