package org.zjw.blog.deal.permission.controller;

import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.zjw.blog.base.common.controller.BaseController;
import org.zjw.blog.base.vo.user.AuthUser;
import org.zjw.blog.deal.permission.entity.Menu;
import org.zjw.blog.deal.permission.entity.Role;
import org.zjw.blog.deal.permission.service.MenuService;
import org.zjw.blog.deal.permission.service.RoleService;
import org.zjw.blog.util.WebUtil;
import org.zjw.blog.util.json.JsonLibUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RequestMapping("/admin/menu")
@Controller
public class MenuController extends BaseController {

	@Resource
	private MenuService menuService;
	
	@Resource
	private RoleService roleService;
	
	
	@RequestMapping("index")
	public String index() {
		return "admin/permission/menu"; 
	}
	
	/**
	 *菜单列表
	 * @param parentId
	 * @return
	 */
	@RequestMapping("list")
	public void list(Integer parentId,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", parentId);
//		String treeGridStr=menuService.getTreeGrid(queryMap);
		//上面的方法是一次性加载所有的节点,下面的方法则为一步一步的加载,当用户点击某个节点时才会触发
		String treeGridStr  = menuService.getTreeGridByParentId(parentId);
		WebUtil.write(response, treeGridStr);
	}
	
	/**
	 * 保存菜单
	 * @param menu
	 * @param response
	 */
	@RequestMapping("save")
	public void save(Menu menu, HttpServletResponse response) {
		menu.setCreateDate(new Date());
		int line=menuService.save(menu);
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if (line>=1) {
			resultMap.put("success", true);
		}else {
			resultMap.put("success", false);
			resultMap.put("errorMsg", "添加数据失败");
		}
		WebUtil.write(response, JsonLibUtil.parseToJson(resultMap));
	}
	
	/**
	 * 修改菜单
	 * @param menu
	 * @param response
	 */
	@RequestMapping("modify")
	public void modify(Menu menu,HttpServletResponse response) {
		int line=menuService.modify(menu);
		JSONObject jsonObject=new JSONObject();
		if (line>=1) {
			jsonObject.put("success", true);
		}else {
			jsonObject.put("success", false);
			jsonObject.put("errorMsg", "修改数据失败");
		}
		WebUtil.write(response, jsonObject);
	}
	
	/**
	 * 删除菜单
	 * @param response
	 * @param menuId
	 * @param parentId
	 */
	@RequestMapping("delete")
	public void delete(HttpServletResponse response,Integer menuId,Integer parentId) {
		int line=menuService.delete(menuId,parentId);
		JSONObject jsonObject=new JSONObject();
		if (line>=1) {
			jsonObject.put("success", true);
		}else {
			jsonObject.put("success", false);
			jsonObject.put("errorMsg", "删除数据失败");
		}
		WebUtil.write(response, jsonObject);
	}

	/**
	 * 后台加载时获得树形菜单
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("getMenuTree")
	public void getMenuTree(HttpServletResponse response,HttpSession session) throws Exception {
		System.out.println("我进来了加载树形菜单啦-----------");
		AuthUser authUser = (AuthUser) SecurityUtils.getSubject().getPrincipal();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userId", authUser.getId());
		List<Role> roleList=roleService.getByUserId(queryMap);
		queryMap.put("menuIds", roleList.get(0).getMenuIds().split(","));
		queryMap.put("parentId", -1);
		String treeStr = menuService.getTree(queryMap);
		WebUtil.write(response, treeStr);
	}
	
	/**
	 * 获得属性菜单
	 * @param response
	 * @param parentId
	 * @param roleId
	 * @throws Exception
	 */
	@RequestMapping("chooseMenu")
	public void chooseMenu(HttpServletResponse response,Integer parentId,Integer roleId) throws Exception {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", parentId);
		Role role=roleService.getById(roleId);
		String[] ids=null;
		if (role.getMenuIds()!=null) {
			ids=role.getMenuIds().split(",");
		}
		queryMap.put("menuIds", ids);
		String[] ids2=null;
		if (role.getOperationIds()!=null) {
			ids2=role.getOperationIds().split(",");
		}
		queryMap.put("operationIds", ids2);
		String treeStr = menuService.getTreeToModify(queryMap);
		WebUtil.write(response, treeStr);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}