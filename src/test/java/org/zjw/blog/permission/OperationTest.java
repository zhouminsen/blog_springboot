package org.zjw.blog.permission;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.blog.BaseTest;
import org.zjw.blog.deal.permission.entity.Operation;
import org.zjw.blog.deal.permission.service.OperationService;
import org.zjw.blog.util.page.Page;

/**
 * 权限测试
 *
 * @author Administrator
 * @date 2016-7-29
 */
public class OperationTest extends BaseTest {
	@Autowired
	private OperationService operationService;

	/**
	 * 根据id获得权限
	 */
	@Test
	public void getById() {
		System.out.println(operationService.getById(10000));
	}

	@Test
	public void getPageByMenuId() {
		Page<Operation> page = operationService.getPageByMenuId(5);
		System.out.println(page.getTotalCount());
		for (Operation item : page.getResultData()) {
			System.out.println(item);
		}
	}
}
