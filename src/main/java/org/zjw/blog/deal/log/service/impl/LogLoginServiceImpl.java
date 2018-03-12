package org.zjw.blog.deal.log.service.impl;


import org.springframework.stereotype.Service;
import org.zjw.blog.deal.backup.dao.BackupMapper;
import org.zjw.blog.deal.backup.entity.Backup;
import org.zjw.blog.deal.log.dao.LogLoginMapper;
import org.zjw.blog.deal.log.entity.LogLogin;
import org.zjw.blog.deal.log.service.LogLoginService;
import org.zjw.blog.util.DateUtil;
import org.zjw.blog.util.file.FileUtil;
import org.zjw.blog.util.file.POIUitl;
import org.zjw.blog.util.page.Page;
import org.zjw.blog.util.page.PageUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Service("logLoginService")
public class LogLoginServiceImpl implements LogLoginService {

	@Resource
	private LogLoginMapper logLoginMapper;
	
	@Resource
	private BackupMapper backupMapper;

	public int save(LogLogin logLogin) {
		return logLoginMapper.insertSelective(logLogin);
	}

	public List<LogLogin> getByIp(String ip) {
		return logLoginMapper.selectByIp(ip);
	}

	/**
	 * <br>获得错误次数
	 * <br>连续错误3次则需要输入验证码
	 * <br>但是由于插入到数据库的顺序迟一步于生成验证码,所以在此需要maxErrorCount-1
	 * <br>如果第4次登陆ok了退出后再次登陆重新计算,但是数据的记录不删除
	 */
	public int getCountError(List<LogLogin> logLoginList,int maxErrorCount) {
		int errorCount=0;
		for (LogLogin logLogin : logLoginList) {
			if (logLogin.getStatus()==0) {
				errorCount++;
			}else {
				if (errorCount!=maxErrorCount-1) {
					errorCount--;
				}
			}
		}
		return errorCount+1;
	}

	public Page<LogLogin> getPageByCondition(Map<String, Object> queryMap) {
		PageUtil.isEmptySetValue(queryMap);
		Page<LogLogin> page = new Page<LogLogin>(
				(Integer)queryMap.get("currentPage"),
				(Integer)queryMap.get("rows"));
		queryMap.put("start", page.getStart());
		List<LogLogin> logLoginList =this.getByCondition(queryMap);
		int totalCount = logLoginMapper.selectCountByCondition(queryMap);
		page.setTotalCount(totalCount);
		page.setResultData(logLoginList);
		return page;
	}

	public List<LogLogin> getByCondition(Map<String, Object> queryMap) {
		return logLoginMapper.selectByCondition(queryMap);
	}

	public void exportExcel(Map<String, Object> queryMap,HttpServletResponse response) throws Exception {
	}

	public void backup(Map<String, Object> queryMap) throws IOException {
		PageUtil.isEmptySetValue(queryMap);
		Backup backup = new Backup();
		String fileName = "登录日志" + DateUtil.getCurDateStr("yyyyMMddHHmmss");
		backup.setBackupName(fileName);
		backup.setBackupPath("/backup/log/login/" + fileName + ".xls");
		backup.setBackupType("登录日志");
		backup.setCreateDate(new Date());
		backupMapper.insertSelective(backup);
		// 设置总标题
		String brow = "登录日志";
		// 每列的标题栏
		List<String> titleRow = Arrays.asList("登录名", "登录密码", "登录时间", "登录状态", "ip地址", "日志详情");
		List<List<String>> bodyRow = new ArrayList<>();
		List<LogLogin> logLoginList = this.getByCondition(queryMap);
		for (LogLogin logLogin : logLoginList) {
			// 设置每行的显示文字
			List<String> row = Arrays.asList(
					logLogin.getUsername(),
					logLogin.getPassword(),
					DateUtil.dateToStr(logLogin.getCreateDate(), DateUtil.NORMALPATTERN),
					logLogin.getStatus().toString(),
					logLogin.getIpAddress(),
					logLogin.getDescription());
			bodyRow.add(row);
		}
		File file = FileUtil.createNewFile("E:/log/login", fileName + ".xls");
		OutputStream os = new FileOutputStream(file);
		POIUitl.createExcel(os, fileName, brow, titleRow, bodyRow);
	}

	public int deleteLogicBatch(String[] idArray) {
		Integer[] ids = new Integer[idArray.length];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = Integer.parseInt(idArray[i]);
		}
		return logLoginMapper.deleteLogicBatch(ids);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
