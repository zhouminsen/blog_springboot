package org.zjw.blog.blog;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zjw.blog.deal.link.entity.Link;
import org.zjw.blog.deal.link.service.LinkService;
import org.zjw.blog.util.page.Page;

import java.util.HashMap;
import java.util.Map;

public class LinkTest {
	ApplicationContext app=new ClassPathXmlApplicationContext("applicationContext.xml");
	LinkService linkService=(LinkService) app.getBean("linkService");
	
	/**
	 * 查询分页对象链接
	 */
	@Test
	public void getPage() {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		Page<Link> page=linkService.getPage(queryMap);
		System.out.println(page.getTotalCount());
		for (Link link : page.getResultData()) {
			System.out.println(link);
		}
	}
}		
