package org.zjw.blog.freemarker;

import org.junit.Test;
import org.zjw.blog.util.FreemarkerUitl;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


public class FreemarkerTest {
	
	@Resource
	private FreemarkerUitl freemarkerUitl;
	
	@Test
	public void create() throws Exception {
		Map<String, Object> dataMap=new HashMap<String, Object>();
		dataMap.put("bb", "周家伟");
		freemarkerUitl.createTemp("D:/WorkSpace/MyEclipse2014_workSpace/Blog/src/main/webapp", dataMap, 1+"");
		System.out.println("ok");
	}
}
