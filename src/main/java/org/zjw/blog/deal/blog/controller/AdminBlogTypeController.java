package org.zjw.blog.deal.blog.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.zjw.blog.base.common.controller.BaseController;
import org.zjw.blog.base.vo.blog.BlogTypeVo;
import org.zjw.blog.deal.blog.entity.BlogType;
import org.zjw.blog.deal.blog.service.BlogTypeService;
import org.zjw.blog.util.WebUtil;
import org.zjw.blog.util.json.JsonUtil;
import org.zjw.blog.util.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 博客类型
 * @author 周家伟
 * @date 2016-7-19
 */
@Controller
@RequestMapping("admin/blog/type")
public class AdminBlogTypeController extends BaseController {
	
	@Resource
	private BlogTypeService blogTypeService;
	
	/**
	 * 跳转到博文类型首页
	 * @return
	 */
	@RequestMapping("index")
	public String index() {
		return "admin/blog/blogTypeManage";
	}
	
	/**
	 * 加载博文类型列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String list(HttpServletRequest request) {
		JSONObject jsonObject=new JSONObject();
		Map<String, Object> queryMap=WebUtil.getParameterMap(request);
		Page<BlogTypeVo> page=blogTypeService.getPageVoByCondition(queryMap);
		jsonObject.put("rows", JsonUtil.parseToJson(page.getResultData()));
		jsonObject.put("total", page.getTotalCount());
		return jsonObject.toString();
	}
	
	/**
	 * 添加
	 * @param blogType
	 * @param response
	 */
	@RequestMapping("save")
	public void save(BlogType blogType,HttpServletResponse response) {
		blogType.setCreateDate(new Date());
		int line=blogTypeService.save(blogType);
		String jsonStr="{'success':"+(line>=1?true:false)+"}";
		WebUtil.write(response, jsonStr);
	}
	
	/**
	 * 修改
	 * @param blogType
	 * @return
	 */
	@RequestMapping("modify")
	@ResponseBody
	public String modify(BlogType blogType) {
		int line=blogTypeService.modify(blogType);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", line>=1?true:false);
		return jsonObject.toString();
	}
	
	/**
	 * 删除数据
	 * @param ids
	 */
	@RequestMapping("delete")
	public void delete(String ids,HttpServletResponse response) {
		String[] idArray=ids.split(",");
		int line=blogTypeService.deleteLogic(idArray);
		WebUtil.write(response, line>=1?true:false);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}