
package org.zjw.blog.deal.log.service;

import java.io.IOException;
import java.util.Map;

import org.zjw.blog.base.vo.log.LogOperationVo;
import org.zjw.blog.util.page.Page;



public interface LogOperationService {

	/**
	 * 多条件查询分页对象
	 * @param queryMap
	 * @return
	 */
	Page<LogOperationVo> getPageByCondition(Map<String, Object> queryMap);
	
	/**
	 * 备份excel
	 * @param queryMap
	 * @throws IOException 
	 * @throws Exception 
	 */
	void backup(Map<String, Object> queryMap) throws IOException, Exception;

	/**
	 * 逻辑批删除
	 * @param idArray
	 * @return
	 */
	int deleteLogicBatch(String[] idArray);
	
}
