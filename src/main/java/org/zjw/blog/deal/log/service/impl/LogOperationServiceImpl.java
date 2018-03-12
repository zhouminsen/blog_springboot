
package org.zjw.blog.deal.log.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.zjw.blog.base.vo.log.LogOperationVo;
import org.zjw.blog.deal.backup.dao.BackupMapper;
import org.zjw.blog.deal.backup.entity.Backup;
import org.zjw.blog.deal.log.dao.LogOperationMapper;
import org.zjw.blog.deal.log.service.LogOperationService;
import org.zjw.blog.util.DateUtil;
import org.zjw.blog.util.file.FileUtil;
import org.zjw.blog.util.file.POIUitl;
import org.zjw.blog.util.page.Page;
import org.zjw.blog.util.page.PageUtil;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

@Service("logOperationService")
public class LogOperationServiceImpl implements LogOperationService {

	@Resource
	private LogOperationMapper logOperationMapper;
	
	@Resource
	private BackupMapper backupMapper;
	

	public Page<LogOperationVo> getPageByCondition(Map<String, Object> queryMap) {
		PageUtil.isEmptySetValue(queryMap);
		Page<LogOperationVo> page = new Page<LogOperationVo>(
				(Integer)queryMap.get("currentPage"),
				(Integer)queryMap.get("rows"));
		queryMap.put("start", page.getStart());
		PageHelper.startPage(page.getCurrentPage(), page.getRows());
		List<LogOperationVo> logOperationList =logOperationMapper.selectVoByCondition(queryMap);
		PageInfo pageInfo = new PageInfo(logOperationList);
		page.setTotalCount(Math.toIntExact(pageInfo.getTotal()));
		page.setResultData(logOperationList);
		return page;
	}

	public void backup(Map<String, Object> queryMap) throws Exception {
		PageUtil.isEmptySetValue(queryMap);
		Backup backup = new Backup();
		//当前模块名加上日期
		String fileName = "操作日志" + DateUtil.getCurDateStr("yyyyMMddHHmmss");
		backup.setBackupName(fileName);
		backup.setBackupPath("/backup/log/operation/" + fileName + ".xls");
		backup.setBackupType("操作日志");
		backup.setCreateDate(new Date());
		backupMapper.insertSelective(backup);
		// 设置总标题
		String brow = "登录日志";
		// 每列的标题栏
		List<String> titleRow = Arrays.asList("操作人", "ip地址", "操作类", "方法名称", "方法参数", "操作类型",
				"操作时间", "操作模块");
		List<List<String>> bodyRow = new ArrayList<>();
		List<LogOperationVo> voList = logOperationMapper.selectVoByCondition(queryMap);
		for (LogOperationVo item : voList) {
			// 设置每行的显示文字
			List<String> row = Arrays.asList(
					item.getUsername(),
					item.getIpAddress(),
					item.getClassName(),
					item.getMethodName(),
					item.getParameter(),
					item.getOperationType(),
					DateUtil.dateToStr(item.getCreateDate(), DateUtil.NORMALPATTERN),
					item.getModuleName());
			bodyRow.add(row);
		}
		String filePath = "\"/blog/static/userImages/\"/log/operation";
		File file = FileUtil.createNewFile(filePath, fileName + ".xls");
		OutputStream out = new FileOutputStream(file);
		POIUitl.createExcel(out, brow, titleRow, bodyRow);
	}

	public int deleteLogicBatch(String[] idArray) {
		List<Integer> ids=new ArrayList<Integer>();
		for (String id : idArray) {
			ids.add(Integer.parseInt(id));
		}
		return logOperationMapper.deleteLogicBatch(ids);
	}
	
}
