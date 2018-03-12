package org.zjw.blog.deal.permission.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.zjw.blog.base.common.controller.BaseController;
import org.zjw.blog.deal.permission.entity.Role;
import org.zjw.blog.deal.permission.service.RoleService;
import org.zjw.blog.util.WebUtil;
import org.zjw.blog.util.json.JsonLibUtil;
import org.zjw.blog.util.json.JsonUtil;
import org.zjw.blog.util.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("admin/role")
@Controller
public class RoleController extends BaseController {
	
	@Resource
	private RoleService roleService;
	
	/**
	 * 跳转到角色管理页面
	 * @return
	 */
	@RequestMapping("index")
	public String index() {
		return "admin/permission/role";
	}
	
	/**
	 * 查询列表
	 * @param response
	 * @param request
	 */
	@RequestMapping("list")
	public void list(HttpServletResponse response,HttpServletRequest request) {
		Map<String, Object> queryMap= WebUtil.getParameterMap(request);
		Page<Role> page=roleService.getPageByCondition(queryMap);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("rows", JsonLibUtil.parseToJson(page.getResultData()));
		jsonObject.put("total", page.getTotalCount());
		WebUtil.write(response, jsonObject);
	}
	
	/**
	 * 添加
	 * @param role
	 * @param response
	 */
	@RequestMapping("save")
	public void save(Role role, HttpServletResponse response) {
		int line=roleService.save(role);
		JSONObject jsonObject=new JSONObject();
		if (line>=1) {
			jsonObject.put("success", true);
		}else {
			jsonObject.put("success", false);
			jsonObject.put("errorMsg", "添加角色失败");
		}
		WebUtil.write(response, jsonObject);
	}
	
	/**
	 * 修改
	 * @param role
	 * @param response
	 */
	@RequestMapping("modify")
	public void modify(Role role,HttpServletResponse response) {
		int line=roleService.modify(role);
		JSONObject jsonObject=new JSONObject();
		if (line>=1) {
			jsonObject.put("success", true);
		}else {
			jsonObject.put("success", false);
			jsonObject.put("errorMsg", "添加角色失败");
		}
		WebUtil.write(response, jsonObject);
	}
	
	@RequestMapping(value="delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delete(String ids) {
		String[] idArray=ids.split(",");
		int line=roleService.deleteLogicBatch(idArray);
		JSONObject jsonObject=new JSONObject();
		if (line>=1) {
			jsonObject.put("delNums", idArray.length);
			jsonObject.put("success", true);
		}else {
			jsonObject.put("errorMsg", "删除失败");
			jsonObject.put("success", false);
		}
		return jsonObject.toString();
	}
	
	/**
	 * 修改角色对应的菜单和权限
	 * @param response
	 * @param menuIds
	 * @param roleId
	 */
	@RequestMapping("modifyRoleMenu")
	public void modifyRoleMenu(HttpServletResponse response,String menuIds,Integer roleId) {
		Role role=new Role();
		role.setRoleId(roleId);
		String[] ids=menuIds.split(",");
		StringBuilder menuId=new StringBuilder();
		StringBuilder operationId=new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			if (Integer.parseInt(ids[i])>=1000) {
				operationId.append(ids[i]+",");
			}else {
				menuId.append(ids[i]+",");
			}
		}
		if (menuId.length()>0) {
			role.setMenuIds(menuId.deleteCharAt(menuId.lastIndexOf(",")).toString());
		}
		if (operationId.length()>0) {
			role.setOperationIds(operationId.deleteCharAt(operationId.lastIndexOf(",")).toString());
		}
		int line=roleService.modifyRoleMenu(role);
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if (line>=1) {
			resultMap.put("success", true);
		}else {
			resultMap.put("success", false);
			resultMap.put("errorMsg", "授权失败");
		}
		WebUtil.write(response, JsonUtil.parseToJson(resultMap));
	}
	
	
	@RequestMapping(value="getPermissionByUserId")
	public void getPermissionByUserId(Integer userId,HttpServletResponse response) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		//queryMap.put("userId", userId);
		Set<String> set = roleService.getPermissionsByUserId(userId);
		WebUtil.write(response, JsonUtil.parseToJson(set));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}