package org.zjw.blog.log;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.blog.BaseTest;
import org.zjw.blog.deal.log.dao.LogLoginJpa;
import org.zjw.blog.deal.log.dao.LogLoginMapper;
import org.zjw.blog.deal.log.entity.LogLogin;
import org.zjw.blog.deal.log.entity.LogLogin2;
import org.zjw.blog.deal.log.service.LogLoginService;
import org.zjw.blog.util.page.Page;

import java.util.HashMap;
import java.util.Map;

public class LogLoginTest  extends BaseTest {
	@Autowired
	private LogLoginService logLoginService;

	@Autowired
	private LogLoginMapper logLoginMapper;

	@Autowired
	private LogLoginJpa logLoginJpa;

	@Test
	public void getPageByCondition(){
		Map<String, Object> queryMap=new HashMap<String, Object>();
		Page<LogLogin> page=logLoginService.getPageByCondition(queryMap);
		System.out.println(page.getTotalCount());
		for (LogLogin item : page.getResultData()) {
			System.out.println(item);
		}
	}
	
	@Test
	public void delete() {
		System.out.println(logLoginService.deleteLogicBatch(new String[]{"54","55"}));
	}

	@Test
	public void getById() {
		LogLogin2 one = logLoginJpa.findOne(54);
		System.out.println(one);
	}
}
