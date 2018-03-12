package org.zjw.blog.deal.blog.controller;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.zjw.blog.base.common.controller.BaseController;
import org.zjw.blog.base.exception.DataException;
import org.zjw.blog.base.vo.blog.BlogCommentVo;
import org.zjw.blog.base.vo.user.AuthUser;
import org.zjw.blog.deal.blog.entity.BlogComment;
import org.zjw.blog.deal.blog.service.BlogCommentService;
import org.zjw.blog.util.WebUtil;
import org.zjw.blog.util.json.JsonLibUtil;
import org.zjw.blog.util.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("admin/blog/comment")
@Controller
public class AdminBlogCommentController extends BaseController {

	@Resource
	private BlogCommentService blogCommentService;
	
	/**
	 * 跳转到评论管理页面
	 * @return
	 */
	@RequestMapping("index")
	public String index() {
		return "admin/blog/commentManage";
	}

	/**
	 * 查询评论列表
	 * @param request
	 * @param response
	 * @throws DataException
	 */
	@RequestMapping("list")
	public void list(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> queryMap=WebUtil.getParameterMap(request);
		AuthUser authUser=(AuthUser) SecurityUtils.getSubject().getPrincipal();
		queryMap.put("userId", authUser.getId());
		Page<BlogCommentVo> page=blogCommentService.getPageVoByCondition(queryMap);
		JSONObject jsonObject=new JSONObject();
		if (page.getSuccess()<=0) {
			//如果是从评论审核界面来的,没有数据也不报错
			if (!queryMap.containsKey("review")) {
				throw new DataException("加载数据失败");
			}
		}
		jsonObject.put("rows", JsonLibUtil.parseToJson(page.getResultData()));
		jsonObject.put("total", page.getTotalCount());
		WebUtil.write(response, jsonObject);
	}
	
	/**
	 * 跳转到评论审核页面
	 * @return
	 */
	@RequestMapping("review/index")
	public String reviewIndex() {
		return "admin/blog/commentReview";
	}
	
	/**
	 * 评论审核
	 * @param state
	 * @param ids
	 * @param response
	 */
	@RequestMapping("review")
	public void review(Integer state,String ids,HttpServletResponse response) {
		String[] idArray=ids.split(",");
		int line=blogCommentService.review(state,idArray);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", line>=1?true:false);
		WebUtil.write(response, jsonObject);
	}
	
	/**
	 * 删除评论
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public String delete(String ids) {
		AuthUser authUser=(AuthUser) SecurityUtils.getSubject().getPrincipal();
		String[] idArray=ids.split(",");
		int line=blogCommentService.deleteLogicBatch(idArray,authUser.getId());
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("success", line>=1?true:false);
		return jsonObject.toString();
	}
	
	
	/**
	 * 发布评论
	 * @param comment 评论实体
	 * @param imageCode	输入的验证码
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	public void save(BlogComment comment, String imageCode,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sRand = (String) request.getSession().getAttribute("verifyCode");
		JSONObject result = new JSONObject();
//		if (UtilFuns.isEmpty(imageCode)) {
//			result.put("success", false);
//			result.put("errorInfo", "验证码不能为空");
//			WebUtil.write(response, result);
//			return;
//		}
//		if (!imageCode.equals(sRand)) {
//			result.put("success", false);
//			result.put("errorInfo", "验证码不正确");
//			WebUtil.write(response, result);
//			return;
//		}
		//获得用户ip地址
		comment.setUserIp(WebUtil.getIpAddr(request));
		int line=blogCommentService.save(comment);
		if (line>=1) {
			result.put("success", true);
			WebUtil.write(response, result);
		}else {
			result.put("success", false);
			WebUtil.write(response, result);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
}