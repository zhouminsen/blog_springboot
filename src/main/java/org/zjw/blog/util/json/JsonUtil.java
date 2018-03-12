package org.zjw.blog.util.json;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 重写 新增方法
 * @author 周家伟
 */
public class JsonUtil {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final ObjectMapper mapper;

	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		mapper = new ObjectMapper();
		mapper.setDateFormat(dateFormat);
	}

	/**
	 * (json转为Object)
	 * 
	 * @param json
	 * @param toClass
	 * @return
	 */
	public static <T> T parseToObject(String json, Class<T> toClass) {
		try {
			return (T) mapper.readValue(json, toClass);
		} catch (Exception e) {
			throw new RuntimeException("将json字符转换为对象时失败!");
		}
	}

	/**
	 * (Object转为json)
	 * 
	 * @param obj
	 * @return
	 */
	public static String parseToJson(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("转换json字符失败!");
		}
	}
}
