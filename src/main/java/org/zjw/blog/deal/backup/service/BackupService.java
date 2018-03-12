
package org.zjw.blog.deal.backup.service;

import java.util.Map;

import org.zjw.blog.deal.backup.entity.Backup;
import org.zjw.blog.util.page.Page;


public interface BackupService {
	
	/**
	 * 多条件查询分页对象
	 * @param queryMap
	 * @return
	 */
	Page<Backup> getPageByCondition(Map<String, Object> queryMap);
	
}
