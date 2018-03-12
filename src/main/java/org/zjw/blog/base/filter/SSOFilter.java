package org.zjw.blog.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SSOFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String service = request.getParameter("service");
        String ticket = request.getParameter("ticket");
        Cookie[] cookies = request.getCookies();
        String username = "";
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if ("sso".equals(cookie.getName())) {
                    username = cookie.getValue();
                    break;
                }
            }
        }

        if (null == service && null != ticket) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (null != username && !"".equals(username)) {
            long time = System.currentTimeMillis();
            String timeString = username + time;
            JVMCache.map.put(timeString, username);
            StringBuilder url = new StringBuilder();
            url.append(service);
            if (0 <= service.indexOf("?")) {
                url.append("&");
            } else {
                url.append("?");
            }
            url.append("ticket=").append(timeString);
            response.sendRedirect(url.toString());
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
