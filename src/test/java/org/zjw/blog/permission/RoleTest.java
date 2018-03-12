package org.zjw.blog.permission;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.blog.BaseTest;
import org.zjw.blog.deal.permission.entity.Role;
import org.zjw.blog.deal.permission.service.RoleService;
import org.zjw.blog.util.page.Page;

import java.util.HashMap;
import java.util.Map;

public class RoleTest   extends BaseTest {
	@Autowired
	private RoleService roleService;
	
	/**
	 * 多条查询分页对象
	 */
	@Test
	public void getPageVoByCondition() {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		Page<Role> page =roleService.getPageByCondition(queryMap);
		System.out.println(page.getTotalCount());
		for (Role item : page.getResultData()) {
			System.out.println(item);
		}
	}
	
}
