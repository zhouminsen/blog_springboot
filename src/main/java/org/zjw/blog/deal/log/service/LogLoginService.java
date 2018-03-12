
package org.zjw.blog.deal.log.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.zjw.blog.deal.log.entity.LogLogin;
import org.zjw.blog.util.page.Page;

/**
 * 
 * @author 周家伟
 * @date 2016-7-18
 * @description 登录日志service
 */
public interface LogLoginService {

	int save(LogLogin logLogin);
	
	/**
	 * 根据ip地址查询login日志
	 * @param ip
	 * @return
	 */
	List<LogLogin> getByIp(String ip);

	/**
	 * 获得错误次数
	 * @param logLoginList 登陆日志
	 * @param maxErrorCount 从配置文件中读取的错误最大次数
	 * @return
	 */
	int getCountError(List<LogLogin> logLoginList, int maxErrorCount);

	/**
	 * 多条件查询分页对象
	 * @param queryMap
	 * @return
	 */
	Page<LogLogin> getPageByCondition(Map<String, Object> queryMap);

	/**
	 * 多条将查旭
	 * @param queryMap
	 * @return
	 */
	List<LogLogin> getByCondition(Map<String, Object> queryMap);
	
	/**
	 * 导出excel
	 * @param queryMap 查旭条件
	 * @param response 
	 * @throws Exception
	 */
	void exportExcel(Map<String, Object> queryMap, HttpServletResponse response)throws Exception;
	
	/**
	 * 备份登录日志
	 * @param queryMap 查询条件
	 * @throws IOException 
	 */
	void backup(Map<String, Object> queryMap) throws IOException;
	
	/**
	 * 逻辑批删除
	 * @param idArray
	 * @return
	 */
	int deleteLogicBatch(String[] idArray);
	
}
