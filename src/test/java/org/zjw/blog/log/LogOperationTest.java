package org.zjw.blog.log;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.blog.BaseTest;
import org.zjw.blog.base.vo.log.LogOperationVo;
import org.zjw.blog.deal.log.service.LogOperationService;
import org.zjw.blog.util.page.Page;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogOperationTest  extends BaseTest {
	@Autowired
	private LogOperationService logOperationService;
	
	/**
	 * 多条查询分页对象
	 */
	@Test
	public void getPageVoByCondition() {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		Page<LogOperationVo> page =logOperationService.getPageByCondition(queryMap);
		System.out.println(page.getTotalCount());
		for (LogOperationVo item : page.getResultData()) {
			System.out.println(item);
		}
	}
	
	@Test
	public void deleteLogicBatch() {
		String[] ids={"389","388"};
		System.out.println(logOperationService.deleteLogicBatch(ids));
	}
	
	/**
	 * 备份excel
	 * @throws Exception 
	 * @throws IOException 
	 */
	@Test
	public void backup() throws IOException, Exception {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		logOperationService.backup(queryMap);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
