package org.zjw.blog.deal.permission.service.impl;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.zjw.blog.base.vo.user.AuthUser;
import org.zjw.blog.deal.permission.dao.MenuMapper;
import org.zjw.blog.deal.permission.entity.Menu;
import org.zjw.blog.deal.permission.entity.Operation;
import org.zjw.blog.deal.permission.entity.Role;
import org.zjw.blog.deal.permission.service.MenuService;
import org.zjw.blog.deal.permission.service.OperationService;
import org.zjw.blog.deal.permission.service.RoleService;
import org.zjw.blog.util.UtilFuns;
import org.zjw.blog.util.json.JsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author 周家伟
 * @date 2016-7-19
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

	@Resource
	private MenuMapper menuMapper;
	
	@Resource
	private OperationService operationService;
	
	@Resource
	private RoleService roleService;

	/****************************main加载的树菜单*************************************************/
	public String getTree(Map<String, Object> queryMap) throws Exception {
		JSONArray jsonArray=new JSONArray();
		//根据userId查询得到角色,再获得所属角色所拥有的菜单id
		jsonArray=getMenuListByParentIds(queryMap);
		return jsonArray.toString() ;
	}
	public String getTree2(Map<String, Object> queryMap) throws Exception {
		JSONArray jsonArray=new JSONArray();
		//根据userId查询得到角色,再获得所属角色所拥有的菜单id
		jsonArray=getMenuListByParentIds(queryMap);
		return jsonArray.toString();
	}

	private JSONArray getMenuListByParentIds(Map<String, Object> queryMap)throws Exception {
		JSONArray jsonArray=getMenuByParentIds(queryMap);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if (jsonObject.getString("state").equals("open")) {
				continue;
			}else {
				queryMap.put("parentId", Integer.parseInt(jsonObject.getString("id")));
				jsonObject.put("children",getMenuListByParentIds(queryMap));
			}
		}
		return jsonArray;
	}

	/**
	 * 得到数菜单,并以easyUI-Tree的json格式存入
	 * @param queryMap
	 * @return
	 * @throws Exception
	 */
	private JSONArray getMenuByParentIds(Map<String, Object> queryMap)throws Exception {
		JSONArray jsonArray=new JSONArray();
		List<Menu> menuList=menuMapper.selectByMenuIds(queryMap);
		for (Menu menu : menuList) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", menu.getMenuId());
			jsonObject.put("text", menu.getMenuName());
			jsonObject.put("iconCls", menu.getIconCls());
			JSONObject attributeObject=new JSONObject();
			attributeObject.put("url", menu.getMenuUrl());
			queryMap.put("parentId", menu.getMenuId());
			if (hasChildren(queryMap)) {
				jsonObject.put("state",menu.getState());
			}else {
				jsonObject.put("state", "open");
			}
			jsonObject.put("attributes", attributeObject);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	/**
	 * 判断是否有子菜单
	 * @param queryMap
	 * @return
	 */
	private boolean hasChildren(Map<String, Object> queryMap){
		List<Menu> menuList=menuMapper.selectByMenuIds(queryMap);
		if (menuList.isEmpty()) {
			return false;
		}
		return true;
	}

	
	/*****************************授权权限的树菜单***************************************************/
	public String getTreeToModify(Map<String, Object> queryMap) {
		JSONArray jsonArray=new JSONArray();
		jsonArray=createTree(queryMap);
		return jsonArray.toString();
	}

	private JSONArray createTree(Map<String, Object> queryMap) {
		JSONArray jsonArray=buildTreeStruct(queryMap);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if (!jsonObject.getBoolean("isChildren")) {
				continue;
			}else {
				queryMap.put("parentId", Integer.parseInt(jsonObject.getString("id")));
				jsonObject.put("children",createTree(queryMap));
			}
		}
		return jsonArray;
	}

	private JSONArray buildTreeStruct(Map<String, Object> queryMap) {
		JSONArray jsonArray=new JSONArray();
		List<Menu> menuList=menuMapper.selectChildrenByParentId(queryMap.get("parentId"));
		for (Menu menu : menuList) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", menu.getMenuId());
			jsonObject.put("text", menu.getMenuName());
			jsonObject.put("iconCls", menu.getIconCls());
			queryMap.put("parentId", menu.getMenuId());
			if (hasChildrenToModify(queryMap)) {
				jsonObject.put("state","closed");
				jsonObject.put("isChildren", true);
			}else {
				jsonObject.put("state", "open");
				jsonObject.put("isChildren", false);
			}
			String[] ids=(String[]) queryMap.get("menuIds");
			if (ids!=null&&UtilFuns.existElements(menu.getMenuId().toString(), ids)>=0){
				jsonObject.put("checked", true);
			}
			//拿到操作权限
			List<Operation> operationList=operationService.getByMenuId(menu.getMenuId());
			if (!operationList.isEmpty()) {
				JSONArray childrenArray=new JSONArray();
				JSONObject children=new JSONObject();
				jsonObject.put("state", "closed");
				for (Operation operation : operationList) {
					children.put("id", operation.getOperationId());
					String operationStr=operation.getMenuName()+":"+operation.getOperationName();
					children.put("text", operationStr);
					String[] ids2=(String[]) queryMap.get("operationIds");
					if (ids!=null&&UtilFuns.existElements(operation.getOperationId().toString(), ids2)>=0) {
						children.put("checked", true);
					}
					childrenArray.add(children);
				}
				jsonObject.put("children", childrenArray);
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	/**
	 * 判断是否有子菜单
	 * @param queryMap
	 * @return
	 */
	private boolean hasChildrenToModify(Map<String, Object> queryMap){
		List<Menu> menuList=menuMapper.selectChildrenByParentId(queryMap);
		if (menuList.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public List<Menu> getChildrenByParentId(Integer id) {
		List<Menu> menuList=menuMapper.selectChildrenByParentId(id);
		return  menuList;
	}

	public Menu getById(int id) {
		return menuMapper.selectByPrimaryKey(id);
	}

	/*****************************加载TreeGrid********************************************/
	public String getTreeGrid(Map<String, Object> queryMap) {
		List<Map<String, Object>> resultList=createTreeGrid(queryMap);
		return JsonUtil.parseToJson(resultList);
	}
	
	/**创建树形菜单
	 * <br>递归加载树形菜单
	 * @param queryMap
	 * @return
	 */
	private List<Map<String, Object>> createTreeGrid(Map<String, Object> queryMap) {
		List<Map<String, Object>> resultList=buildTreeGridStruct(queryMap);
		for (int i = 0; i < resultList.size(); i++) {
			Map<String, Object> map=resultList.get(i);
			if (map.get("state").equals("closed")) {
				//表示拥有子菜单,则继续进行递归查询
				queryMap.put("parentId", (map.get("id")));
				map.put("children",createTreeGrid(queryMap));
			}
		}
		return resultList;
	}
	
	/**
	 * 构建树形菜单结构
	 * @param queryMap
	 * @return
	 */
	private List<Map<String, Object>> buildTreeGridStruct(Map<String, Object> queryMap) {
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		List<Menu> menuList=menuMapper.selectChildrenByParentId(queryMap.get("parentId"));
		for (Menu menu : menuList) {
			Map<String, Object> resultMap=new HashMap<String, Object>();
			resultMap.put("id", menu.getMenuId());
			resultMap.put("text", menu.getMenuName());
			resultMap.put("iconCls", menu.getIconCls());
			resultMap.put("state", menu.getState());
			resultMap.put("seq", menu.getSeq());
			resultMap.put("menuUrl", menu.getMenuUrl());
			resultMap.put("description", menu.getDescription());
			queryMap.put("parentId", menu.getMenuId());
			if (hasChildrenToModify(queryMap)) {
				resultMap.put("state","closed");
			}else {
				resultMap.put("state", "open");
			}
			Map<String, Object> attributeMap = new HashMap<String, Object>();
			attributeMap.put("url", menu.getMenuUrl());
			resultMap.put("attributes", attributeMap);
			List<Operation> operationList=operationService.getByMenuId(menu.getMenuId());
			StringBuilder sb=new StringBuilder();
			for (Operation operation : operationList) {
				sb.append(operation.getOperationName()+",");
			}
			if (UtilFuns.isNotEmpty(sb)) {
				resultMap.put("operationNames", sb.deleteCharAt(sb.lastIndexOf(",")).toString());
			}else {
				resultMap.put("operationNames", "");
			}
			resultList.add(resultMap);
		}
		return resultList;
	}

	
	public int save(Menu menu) {
		//添加菜单前要先修改该菜单的父菜单的状态
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("updateState", "closed");
		paramMap.put("menuId", menu.getParentId());
		paramMap.put("whereState", "open");
		int line=menuMapper.updateState(paramMap);
		line=menuMapper.insertSelective(menu);
		//不知为何改变不了authUser里面的变量,明明就是引用对象,结果下次跑来还是原始的值
		AuthUser authUser= (AuthUser) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		Role role = authUser.getRole();
		//判断当前用户是否为超级管理员 如果是则及时刷新到当前角色中
		if (role.getRoleId()==1) {
			Role role2 = roleService.getById(role.getRoleId());
			role2.setMenuIds(role2.getMenuIds() + "," + menu.getMenuId());
			roleService.modify(role2);
		}
			return line;
	}

	public int modify(Menu menu) {
		return menuMapper.updateByPrimaryKeySelective(menu);
	}

	public int delete(Integer menuId,Integer parentId) {
		return delChildren(menuId);
		/*Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("updateState", "open");
		paramMap.put("menuId", parentId);
		paramMap.put("whereState", "closed");
		return menuMapper.updateState(paramMap);*/
	}
	
	/**
	 * 递归删除
	 * @param menuId
	 * @return
	 */
	private int delChildren(Integer menuId) {
		int line=0;
		//查旭子菜单
		List<Menu> menuList=menuMapper.selectChildrenByParentId(menuId);
		//如果存在子弹则递归删除
		if (!menuList.isEmpty()) {
			for (Menu menu : menuList) {
				delChildren(menu.getMenuId());
			}
//			line=menuMapper.deleteLogicBatch(new Integer[]{menuId});
			line = menuMapper.deleteBatch(new Integer[]{menuId});
		}else {
//			line= menuMapper.deleteLogicBatch(new Integer[]{menuId});
			line = menuMapper.deleteBatch(new Integer[]{menuId});
		}
		if (line<=0) {
			new RuntimeException("删除数据失败");
		}
		return line;
	}

	public String getTreeGridByParentId(Integer parentId) {
		List<Menu> menuList = getChildrenByParentId(parentId);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		for (Menu menu : menuList) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", menu.getMenuId());
			resultMap.put("text", menu.getMenuName());
			resultMap.put("iconCls", menu.getIconCls());
			resultMap.put("state", menu.getState());
			resultMap.put("seq", menu.getSeq());
			resultMap.put("menuUrl", menu.getMenuUrl());
			resultMap.put("description", menu.getDescription());
			queryMap.put("parentId", menu.getMenuId());
			if (hasChildrenToModify(queryMap)) {
				resultMap.put("state","closed");
			}else {
				resultMap.put("state", "open");
			}
			Map<String, Object> attributeMap = new HashMap<String, Object>();
			attributeMap.put("url", menu.getMenuUrl());
			resultMap.put("attributes", attributeMap);
			List<Operation> operationList=operationService.getByMenuId(menu.getMenuId());
			StringBuilder sb=new StringBuilder();
			for (Operation operation : operationList) {
				sb.append(operation.getOperationName()+",");
			}
			if (UtilFuns.isNotEmpty(sb)) {
				resultMap.put("operationNames", sb.deleteCharAt(sb.lastIndexOf(",")).toString());
			}else {
				resultMap.put("operationNames", "");
			}
			resultList.add(resultMap);
		}
		return JsonUtil.parseToJson(resultList);
	}

}
