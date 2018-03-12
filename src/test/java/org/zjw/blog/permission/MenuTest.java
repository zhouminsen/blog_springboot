package org.zjw.blog.permission;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.blog.BaseTest;
import org.zjw.blog.base.vo.user.AuthUser;
import org.zjw.blog.deal.permission.entity.Role;
import org.zjw.blog.deal.permission.service.MenuService;
import org.zjw.blog.deal.permission.service.RoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuTest   extends BaseTest {
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 获得菜单树
	 * @throws Exception
	 */
	@Test
	public void getTree() throws Exception {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("userId", 1);
		List<Role> roleList=roleService.getByUserId(queryMap);
		queryMap.put("menuIds", roleList.get(0).getMenuIds().split(","));
		queryMap.put("userId", 1);
		queryMap.put("parentId", -1);
		String str=menuService.getTree(queryMap);
		System.out.println(str);
	}
	
	@Test
	public void getTreeToModify() {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("parentId", -1);
		Role role=roleService.getById(4);
		queryMap.put("menuIds", role.getMenuIds().split(","));
		String treeStr=menuService.getTreeToModify(queryMap);
		System.out.println(treeStr);
	}
	
	@Test
	public void getTreeGrid() {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("parentId", 41);
		String treeGridStr = menuService.getTreeGrid(queryMap);
		System.out.println(treeGridStr);
	}
	
	@Test
	public void delete() {
		System.out.println(menuService.delete(43, 41));
	}
	
	
	@Test
	public void  test(){
		AuthUser authUser = new AuthUser();
		Role role = new Role();
		role.setRoleName("haha");
		authUser.setRole(role);
		Role role2 = authUser.getRole();
		System.out.println(role.getRoleName());
		role2.setRoleName("xixi");
		System.out.println(role.getRoleName());

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
