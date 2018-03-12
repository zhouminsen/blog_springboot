package org.zjw.blog.deal.backup.controller;

import org.zjw.blog.base.common.controller.BaseController;
import org.zjw.blog.deal.backup.entity.Backup;
import org.zjw.blog.deal.backup.service.BackupService;
import org.zjw.blog.util.WebUtil;
import org.zjw.blog.util.json.JsonUtil;
import org.zjw.blog.util.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
@RequestMapping("admin/backup")
@Controller
public class BackupController extends BaseController{
	
	@Resource
	private BackupService backupService;
	
	@RequestMapping(value="list",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String list(HttpServletRequest request) {
		Map<String, Object> queryMap=WebUtil.getParameterMap(request);
		Page<Backup> page=backupService.getPageByCondition(queryMap);
		Map<String, Object> dataMap=new HashMap<String, Object>();
		dataMap.put("total", page.getTotalCount());
		dataMap.put("rows", page.getResultData());

		System.out.println(JsonUtil.parseToJson(dataMap));
		return JsonUtil.parseToJson(dataMap);
	}
	
}