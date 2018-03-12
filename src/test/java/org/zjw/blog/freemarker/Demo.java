package org.zjw.blog.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Demo {
	public static void main(String[] args) throws Exception {
		FreeMarkerConfigurer freeMarkerConfigurer=new FreeMarkerConfigurer();
		//获得configuration
		Configuration configuration = freeMarkerConfigurer.createConfiguration();
		//设置默认编码
		configuration.setDefaultEncoding("UTF-8");
		File file=new File("D:/WorkSpace/MyEclipse2014_workSpace/Blog/src/main/webapp/file/flt/");
		if (!file.exists()) {
			file.mkdirs();
		}
		//设置模板的目录
		configuration.setDirectoryForTemplateLoading(file);
		//获得模板
		Template template = configuration.getTemplate("MyHtml.html");
		Map<String, Object> rootMap=new HashMap<String, Object>();
		rootMap.put("bb", "周家伟");
		Writer out=new OutputStreamWriter(new FileOutputStream(new File("D:/WorkSpace/MyEclipse2014_workSpace/Blog/src/main/webapp/","MyHtml1.html")), "utf-8");
		//写入
		template.process(rootMap, out);
		out.close();
	}
}
