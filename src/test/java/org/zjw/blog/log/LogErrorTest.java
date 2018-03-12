package org.zjw.blog.log;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.blog.BaseTest;
import org.zjw.blog.deal.log.entity.LogError;
import org.zjw.blog.deal.log.service.LogErrorService;

import java.util.Date;

public class LogErrorTest   extends BaseTest {
	@Autowired
	private LogErrorService logErrorService;
	
	@Test
	public void save() {
		LogError logError=new LogError();
		logError.setClassName("dsdasd");
		logError.setCreateDate(new Date());
		logError.setIpAddress("dsds");
		int line=logErrorService.save(logError);
		System.out.println(line);
	}


}
