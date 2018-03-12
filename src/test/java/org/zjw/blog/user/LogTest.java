package org.zjw.blog.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.blog.BaseTest;
import org.zjw.blog.deal.log.entity.LogLogin;
import org.zjw.blog.deal.log.service.LogLoginService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志测试
 * @author Administrator
 *
 */
public class LogTest  extends BaseTest {
	@Autowired
	private LogLoginService logLoginService;
	/**
	 * 添加登录日志
	 */
	@Test
	public void addLogLogin() {
		LogLogin logLogin=new LogLogin();
		logLogin.setCreateDate(new Date());
		logLogin.setIpAddress("12345");
		//1代表普通登录
		logLogin.setType(1);
		//0代表登录失败
		logLogin.setStatus(0);
		logLogin.setUsername("zjw");
		logLogin.setPassword("123");
		logLogin.setDescription("登录失败");
		logLoginService.save(logLogin);
	}
	/**
	 * 查询登录日志
	 */
	@Test
	public void searchLogLogin() {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		System.out.println(logLoginService.getByIp("172.28.128.117").size());
	}
}
