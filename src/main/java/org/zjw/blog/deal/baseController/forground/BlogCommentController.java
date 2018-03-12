package org.zjw.blog.deal.baseController.forground;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.zjw.blog.base.common.controller.BaseController;
import org.zjw.blog.deal.blog.entity.BlogComment;
import org.zjw.blog.deal.blog.service.BlogCommentService;
import org.zjw.blog.util.UtilFuns;
import org.zjw.blog.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/comment")
@Controller
public class BlogCommentController extends BaseController {

	@Resource
	private BlogCommentService blogCommentService;

	/**
	 * 发布评论
	 * @param comment 评论实体
	 * @param imageCode	输入的验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	public void save(BlogComment comment, String imageCode,
                     HttpServletRequest request, HttpServletResponse response) {
		String sRand = (String) request.getSession().getAttribute("verifyCode");
		JSONObject result = new JSONObject();
		if (UtilFuns.isEmpty(imageCode)) {
			result.put("success", false);
			result.put("errorInfo", "验证码不能为空");
			WebUtil.write(response, result);
			return;
		}
		if (!imageCode.equals(sRand)) {
			result.put("success", false);
			result.put("errorInfo", "验证码不正确");
			WebUtil.write(response, result);
			return;
		}
		//获得用户ip地址
		comment.setUserIp(WebUtil.getIpAddr(request));
		try {
			blogCommentService.save(comment);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("errorInfo", "发布评论失败");
			WebUtil.write(response, result);
			return;
		}
		result.put("success", true);
		WebUtil.write(response, result);
		return;
	}

	
	
}