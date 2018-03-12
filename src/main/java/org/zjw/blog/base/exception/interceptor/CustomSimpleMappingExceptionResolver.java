package org.zjw.blog.base.exception.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zjw.blog.util.WebUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 因为spring对ajax请求无法给出完美的解决方案,所以重写SimpleMappingExceptionResolver,对ajax请求进行拓展
 * 
 * @author 周家伟
 * @date 2016-7-11
 *
 */
public class CustomSimpleMappingExceptionResolver extends
		SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		//打印异常
		ex.printStackTrace();
		// 匹配错误页面,如果springmvc.xml中没有匹配该异常,则自动匹配默认的错误页面
		String viewName = super.determineViewName(ex, request);
		if (viewName != null) {// JSP格式返回
			if (!WebUtil.isAjaxRequest(request)) {
				// 如果不是异步请求
				// Apply HTTP status code for error views, if specified.
				// Only apply it if we're processing a top-level request.
				//拿到当前响应状态码
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					//设置当前响应状态吗
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else {//ajax请求 JSON格式返回
				//拿到当前响应状态码
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					//设置当前响应状态吗
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				WebUtil.write(response, ex);
				return null;
			}
		} else {
			return null;
		}
	}

}
