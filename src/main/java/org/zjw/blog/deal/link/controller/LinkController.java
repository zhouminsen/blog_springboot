package org.zjw.blog.deal.link.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.zjw.blog.base.common.controller.BaseController;
import org.zjw.blog.deal.link.entity.Link;
import org.zjw.blog.deal.link.service.LinkService;
import org.zjw.blog.util.WebUtil;
import org.zjw.blog.util.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin/link")
public class LinkController extends BaseController {

	@Resource
	private LinkService linkService;

	@RequestMapping("index")
	public String index() {
		return "admin/blog/linkManage";
	}

	/**
	 * 拿大链接列表
	 * <br>设置 produces = "text/html;charset=UTF-8" 解决中文乱码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String list(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		try {
			Map<String, Object> queryMap = WebUtil.getParameterMap(request);
			Page<Link> page = linkService.getPage(queryMap);
			jsonObject.put("rows", page.getResultData());
			jsonObject.put("total", page.getTotalCount());
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("rows", "数据有误");
			jsonObject.put("total", 0);
		}
		return jsonObject.toString();
	}

	@RequestMapping("save")
	public void save(Link link, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		try {
			int line = linkService.save(link);
			jsonObject.put("success", line >= 1 ? true : false);
		} catch (Exception e) {
			jsonObject.put("success", false);
		}
		WebUtil.write(response, jsonObject);
	}

	@RequestMapping("modify")
	public void modify(Link link, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		try {
			int line = linkService.modify(link);
			if (line >= 1) {
				jsonObject.put("success", true);
			} else {
				jsonObject.put("success", false);
			}
		} catch (Exception e) {
			jsonObject.put("success", false);
		}
		WebUtil.write(response, jsonObject);
	}

	@RequestMapping("delete")
	@ResponseBody
	public String delete(String ids) {
		String[] arrayId = ids.split(",");
		int line = linkService.deleteLogic(arrayId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", line>=1?true:false);
		return jsonObject.toString();
	}

}