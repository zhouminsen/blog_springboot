package org.zjw.blog.backup;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.blog.BaseTest;
import org.zjw.blog.deal.backup.entity.Backup;
import org.zjw.blog.deal.backup.service.BackupService;
import org.zjw.blog.util.page.Page;

import java.util.HashMap;
import java.util.Map;

public class BackupTest  extends BaseTest {

	@Autowired
	private BackupService backupService;
	/**
	 * 多条件查询分页对象
	 */
	@Test
	public void getPageByCondition() {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		Page<Backup> page=backupService.getPageByCondition(queryMap);
		System.out.println(page.getTotalCount());
		for (Backup item : page.getResultData()) {
			System.out.println(item);
		}
	}
}
