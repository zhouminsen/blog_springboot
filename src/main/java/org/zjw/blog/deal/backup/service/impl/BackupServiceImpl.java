package org.zjw.blog.deal.backup.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.zjw.blog.deal.backup.dao.BackupMapper;
import org.zjw.blog.deal.backup.entity.Backup;
import org.zjw.blog.deal.backup.service.BackupService;
import org.zjw.blog.util.page.Page;
import org.zjw.blog.util.page.PageUtil;
import org.springframework.stereotype.Service;

@Service("backupService")
public class BackupServiceImpl implements BackupService {

	@Resource
	private BackupMapper backupMapper;

	public Page<Backup> getPageByCondition(Map<String, Object> queryMap) {
		PageUtil.isEmptySetValue(queryMap);
		Page<Backup> page=new Page<Backup>((Integer)queryMap.get("currentPage"), (Integer)queryMap.get("rows"));
		List<Backup> backupList=backupMapper.selectByCondition(queryMap);
		int totalCount=backupMapper.selectCountByCondition(queryMap);
		page.setResultData(backupList);
		page.setTotalCount(totalCount);
		return page;
	}
}
