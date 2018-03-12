package org.zjw.blog.deal.permission.service.impl;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.zjw.blog.deal.permission.dao.RoleMapper;
import org.zjw.blog.deal.permission.entity.Operation;
import org.zjw.blog.deal.permission.entity.Role;
import org.zjw.blog.deal.permission.service.MenuService;
import org.zjw.blog.deal.permission.service.OperationService;
import org.zjw.blog.deal.permission.service.RoleService;
import org.zjw.blog.util.page.Page;
import org.zjw.blog.util.page.PageUtil;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 周家伟
 * @date 2016-7-19
 *
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleMapper roleMapper;
	
	@Resource
	private MenuService menuService; 
	
	@Resource
	private OperationService operationService;

	public List<Role> getByUserId(Map<String, Object> queryMap) {
		return roleMapper.selectByUserId(queryMap);
	}

	public Page<Role> getPageByCondition(Map<String, Object> queryMap) {
		PageUtil.isEmptySetValue(queryMap);
		Page<Role> page = new Page<Role>(
				(Integer)queryMap.get("currentPage"),
				(Integer)queryMap.get("rows"));
		queryMap.put("start", page.getStart());
		List<Role> roleList =roleMapper.selectByCondition(queryMap);
		int totalCount = roleMapper.selectCountByCondition(queryMap);
		page.setTotalCount(totalCount);
		page.setResultData(roleList);
		return page;
	}

	public Set<String> getPermissionsByUserId(Integer userId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userId", userId);
		// 获得当前用户的角色,拿到所用的菜单和权限的ids
		List<Role> roleList = this.getByUserId(queryMap);
		String[] operationIds = roleList.get(0).getOperationIds().split(",");
		Set<String> permissionList = new HashSet<String>();
		for (String id : operationIds) {
			//添加权限和权限名
			Operation operation=operationService.getById(Integer.parseInt(id));
			//拼接权限字符串 (链接管理:删除)
			String perminssion=operation.getMenuName()+":"+operation.getOperationName();
			permissionList.add(perminssion);
			System.out.println(perminssion);
		}
		return permissionList;
	}

	public int save(Role role) {
		return roleMapper.insertSelective(role);
	}

	public int modify(Role role) {
		return roleMapper.updateByPrimaryKeySelective(role);
	}

	public Role getById(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	public int modifyRoleMenu(Role role) {
		return roleMapper.updateByPrimaryKeySelective(role);
	}

	public int deleteLogicBatch(String[] idArray) {
		Integer[] ids=new Integer[idArray.length];
		for (int i = 0; i < ids.length; i++) {
			ids[i]=Integer.parseInt(idArray[i]);
		}
		return roleMapper.deleteLogicBatch(ids);
	}
	
	
}
