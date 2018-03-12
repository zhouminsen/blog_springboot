package org.zjw.blog.deal.log.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LogUtil {
	/**
	 * log4j的日志文件地址
	 */
	public static String logPath;
	
	static{
		Properties properties=new Properties();
		//得到log4j.properties的输入流
		InputStream in=LogUtil.class.getResourceAsStream("/properties/log4j.properties");
		try {
			//properties加载该流
			properties.load(in);
			logPath=properties.getProperty("log4j.appender.file.File");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
