package org.zjw.blog.base.system.support.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.zjw.blog.base.system.support.ServletAPI;

public class ServletAPIFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
		String name=filterConfig.getInitParameter("name");
		System.out.println(name);
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//ServletAPI.setRequest((HttpServletRequest)request);
		ServletAPI.setResponse((HttpServletResponse)response);
		chain.doFilter(request, response);
	}
	public void destroy() {
		
	}
}
