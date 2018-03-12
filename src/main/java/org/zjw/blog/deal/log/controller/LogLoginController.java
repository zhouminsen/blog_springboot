package org.zjw.blog.deal.log.controller;

import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zjw.blog.base.common.controller.BaseController;
import org.zjw.blog.deal.log.entity.LogLogin;
import org.zjw.blog.deal.log.service.LogLoginService;
import org.zjw.blog.deal.log.util.LogUtil;
import org.zjw.blog.util.FTPUtil;
import org.zjw.blog.util.WebUtil;
import org.zjw.blog.util.file.DownloadUtil;
import org.zjw.blog.util.json.JsonLibUtil;
import org.zjw.blog.util.json.JsonUtil;
import org.zjw.blog.util.page.Page;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("admin/log/login")
@Controller
public class LogLoginController extends BaseController {

    @Resource
    private LogLoginService logLoginService;

    @Resource
    private FTPUtil ftpUtil;

    @RequestMapping("index")
    public String index() {
        return "admin/log/logLogin";
    }

    @RequestMapping("list")
    public void list(HttpServletResponse response, HttpServletRequest request) {
        Map<String, Object> queryMap = WebUtil.getParameterMap(request);
        Page<LogLogin> page = logLoginService.getPageByCondition(queryMap);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rows", JsonLibUtil.parseToJson(page.getResultData()));
        jsonObject.put("total", page.getTotalCount());
        WebUtil.write(response, jsonObject);
    }

    @RequestMapping("delete")
    public void delete(String ids, HttpServletResponse response) {
        String[] idArray = ids.split(",");
        int line = logLoginService.deleteLogicBatch(idArray);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (line >= 1) {
            resultMap.put("success", true);
            resultMap.put("delNums", idArray.length);
        } else {
            resultMap.put("success", false);
            resultMap.put("errorMsg", "删除数据失败");
        }
        WebUtil.write(response, JsonUtil.parseToJson(resultMap));
    }


    /**
     * 后台日志下载
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("downloadLog")
    public ResponseEntity<byte[]> downloadLog(HttpServletResponse response) throws Exception {
        //日志文件路径
        File file = new File(LogUtil.logPath);
        HttpHeaders headers = new HttpHeaders();
        String filename = new String("log.log".getBytes("UTF-8"), "iso-8859-1");
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]
                >(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    /**
     * poi操作excel
     * 备份excel
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("backup")
    public void backup(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, Object> queryMap = WebUtil.getParameterMap(request);
        logLoginService.backup(queryMap);
        //logLoginService.exportExcel(queryMap, response);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        WebUtil.write(response, jsonObject);
    }

    /**
     * 下载备份
     *
     * @param response
     * @param fileName
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("downloadBackup")
    public void downloadBackup(HttpServletResponse response, String fileName) throws IOException {
        fileName = URLDecoder.decode(fileName, "utf-8");
        String filePath =  "E:/log/login/" + fileName + ".xls";
        File file = new File(filePath);
        DownloadUtil downloadUtil = new DownloadUtil();
        downloadUtil.prototypeDownload(file, fileName + ".xls", response.getOutputStream(), false);

    }


    /**
     * 后台日志下载
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("downImage")
    public void downloadImage(HttpServletResponse response,String returnName) throws Exception {
        FTPClient ftp = new FTPClient();
        ftp.connect(ftpUtil.getHost(), ftpUtil.getPort());
        if (!ftp.login(ftpUtil.getUser(), ftpUtil.getPassword())) {
            ftp.disconnect();
            System.err.println("FTP server refused connection.");
            System.exit(1);
        }
        response.reset();
        //设置响应类型	PDF文件为"application/pdf"，WORD文件为："application/msword"， EXCEL文件为："application/vnd.ms-excel"。
        response.setContentType("application/octet-stream;charset=utf-8");

        //设置响应的文件名称,并转换成中文编码
        //returnName = URLEncoder.encode(returnName,"UTF-8");
        returnName = response.encodeURL(new String("bb".getBytes(),"iso8859-1"));	//保存的文件名,必须和页面编码一致,否则乱码

        //attachment作为附件下载；inline客户端机器有安装匹配程序，则直接打开；注意改变配置，清除缓存，否则可能不能看到效果
        response.addHeader("Content-Disposition",   "attachment;filename="+returnName+".jpg");

        ftp.enterLocalPassiveMode();
        //路径是相对路径
        InputStream is = ftp.retrieveFileStream(new String("image/zjw/20170120172315.jpg".getBytes("UTF-8"),
                "ISO-8859-1"));
        //日志文件路径
        OutputStream os = response.getOutputStream();
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


}
