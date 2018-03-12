package org.zjw.blog.base.system.support;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.servlet.HandlerInterceptor;


/**
 * 方式1:实现ServletRequestListener接口可以获得request,需要在web.xml配置listener配置该类的全路径
 * 方式2:实现filter
 * @author 周家伟
 * @date 2016-8-5
 *
 */
public class ServletAPI implements ServletContextListener{
	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal=new ThreadLocal<HttpServletResponse>();
	private static ServletContext servletContext;
	
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) requestLocal.get();
	}

	public static void setRequest(HttpServletRequest request) {
		System.out.println("我初始化request了"+request);
		requestLocal.set(request);
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) responseLocal.get();
	}

	public static void setResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}

	public static HttpSession getSession() {
		try {
			return ((HttpServletRequest) requestLocal.get()).getSession();
		} catch (Exception e) {
			return null;
		} 
	}

	public static ServletContext getApp() {
		servletContext.setAttribute("init", 1);
		return servletContext;
	}
	
	public void contextInitialized(ServletContextEvent sce) {

		servletContext=sce.getServletContext();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public void requestDestroyed(ServletRequestEvent sre) {
	}

	public void requestInitialized(ServletRequestEvent sre) {
		setRequest((HttpServletRequest) sre.getServletRequest());
	}

}
