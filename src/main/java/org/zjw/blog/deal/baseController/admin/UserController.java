package org.zjw.blog.deal.baseController.admin;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.zjw.blog.base.system.UserConstant;
import org.zjw.blog.base.vo.user.AuthUser;
import org.zjw.blog.deal.blog.entity.Blogger;
import org.zjw.blog.deal.blog.service.BloggerService;
import org.zjw.blog.util.FTPUtil;
import org.zjw.blog.util.UtilFuns;
import org.zjw.blog.util.WebUtil;
import org.zjw.blog.util.file.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.zjw.blog.util.DateUtil;

/**
 * @author 周家伟
 * @date 2016-7-21
 * 用户管理controller
 */
@RequestMapping("admin/user")
@Controller
public class UserController {
    @Resource
    private BloggerService bloggerService;

    @Resource
    private FTPUtil fTPUtil;

    /**
     * 跳转到用户修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request) {
        return "admin/blog/modifyInfo";
    }

    /**
     * 根据id获得用户信息
     * <br> 原路径:/admin/user/1/getById
     * <br> {id}:代表1 那么@PathVariable("id") Integer id=1
     *
     * @param id
     * @param response
     */
    @RequestMapping("{id}/getById")
    public void getById(@PathVariable("id") Integer id, HttpServletResponse response) {
        Blogger blogger = bloggerService.getById(id);
        WebUtil.write(response, JSON.toJSONString(blogger));
    }

    /**
     * 修改用户信息
     *
     * @param blogger
     * @param response
     */
    @RequestMapping("modify")
    public void modify(Blogger blogger, HttpServletResponse response, Integer isChangePic) {
        JSONObject jsonObject = new JSONObject();
        int line = bloggerService.modify(blogger, isChangePic);
        if (line >= 1) {
            jsonObject.put("success", true);
            jsonObject.put("message", "修改成功");
        } else {
            jsonObject.put("success", false);
            jsonObject.put("message", "修改失败");
        }
        WebUtil.write(response, jsonObject);
    }

    /**
     * 修改密码
     *
     * @param newPassword 新密码
     * @param id          当前登录人id
     * @param response
     */
    @RequestMapping("modifyPassword")
    public void modifyPassword(String newPassword, Integer id, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            AuthUser authUser = (AuthUser) SecurityUtils.getSubject().getPrincipal();
            authUser.getBlogger().setId(id);
            int line = bloggerService.modifyPassword(newPassword, authUser.getBlogger());
            if (line >= 1) {
                jsonObject.put("success", true);
                jsonObject.put("message", "修改成功");
            } else {
                jsonObject.put("success", false);
                jsonObject.put("message", "修改失败");
            }
            WebUtil.write(response, jsonObject);
        } catch (Exception e) {
            jsonObject.put("success", false);
        }
        System.out.println(jsonObject);
    }

    /**
     * spring上传图片到本地服务器中
     *
     * @param pic      对应 页面表单中的pic标签
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("uploadPic")
    public void uploadPic(@RequestParam(required = false) MultipartFile pic,
                          HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 得到文件后缀
        String suffix = UtilFuns.cutLastStrLater(pic.getOriginalFilename(), ".");
        //拼接文件名
        String fileName = DateUtil.getCurDateStr("yyyyMMddHHssmm") + "." + suffix;
        String filePath = request.getServletContext().getRealPath("/");
        //得到要上传的地址
        filePath += "/static/userImages";
        File file = FileUtil.createNewFile(filePath, fileName);
        //开始上传
        pic.transferTo(file);
        JSONObject jsonObject = new JSONObject();
        //dataUrl存入数据库的 allUrl该图片的服务器的路径
        jsonObject.put("dataUrl", fileName);
        jsonObject.put("allUrl", "/blog/static/userImages/" + fileName);
        WebUtil.write(response, jsonObject);
    }

    /**
     * Jersey上传图片到外部服务器中
     *
     * @param pic2     对应 页面表单中的pic2标签
     * @param response
     * @throws Exception
     * @throws IOException
     */
    @RequestMapping("uploadPicJersey")
    public void uploadPicJersey(@RequestParam(required = false) MultipartFile pic2, HttpServletResponse response) throws Exception {
        AuthUser authUser = (AuthUser) SecurityUtils.getSubject().getPrincipal();
        // 得到文件后缀
        String suffix = UtilFuns.cutLastStrLater(pic2.getOriginalFilename(), ".");
        // 拼接文件名
        String fileName = DateUtil.getCurDateStr("yyyyMMddHHssmm") + "." + suffix;
        boolean flag = fTPUtil.uploadFile(UserConstant.USER_IMAGE_PATH + "/" + authUser.getUsername(), fileName, pic2.getInputStream());
        String allUrl = UserConstant.USER_IMAGE_ADDR + "/" + authUser.getUsername() + "/" + fileName;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("subImageName", fileName);
        jsonObject.put("allUrl", allUrl);
        jsonObject.put("success", flag);
        WebUtil.write(response, jsonObject);
    }


}
