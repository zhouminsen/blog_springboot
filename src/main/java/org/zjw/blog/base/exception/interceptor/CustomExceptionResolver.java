package org.zjw.blog.base.exception.interceptor;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.zjw.blog.base.vo.user.AuthUser;
import org.zjw.blog.deal.log.dao.LogErrorMapper;
import org.zjw.blog.deal.log.entity.LogError;
import org.zjw.blog.util.UtilFuns;
import org.zjw.blog.util.WebUtil;
import org.zjw.blog.util.json.JsonUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
/**
 * springmvc的异常拦截器
 * 该bean没@Component注解时配置在spring-mvc.xml
 * <br>因为spring对ajax请求无法给出完美的解决方案,所以实现HandlerExceptionResolver接口,对ajax请求进行拓展
 * @author 周家伟
 * @date 2016-7-10
 */
//@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {
	
	@Resource
	private LogErrorMapper logErrorMapper;
	
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		request.getParameter("username");
		// 打印异常
		ex.printStackTrace();
		String message = null;
		String viewName=null;
		response.setStatus(500);
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(ex.toString());
			// 获取打印出来的异常信息
			sb.append(Arrays.toString(ex.getStackTrace()));
			String exceptionInfo = sb.toString().substring(0, 3000);
			//将错误信息写入到数据库中
			LogError logError=new LogError();
			HandlerMethod  handlerMethod=(HandlerMethod) handler;
			logError.setMethodName(handlerMethod.getMethod().getName());
			logError.setClassName(handlerMethod.getBeanType().getName());
			logError.setCreateDate(new Date());
			logError.setExceptionInfo(exceptionInfo);
			logError.setIpAddress(WebUtil.getIpAddr(request));
			AuthUser authUser=(AuthUser)SecurityUtils.getSubject().getPrincipal();
			Map<String, Object> resultMap=WebUtil.getParameterMap(request);
			String jsonMap=JsonUtil.parseToJson(resultMap);
			logError.setParameter(jsonMap);
			if (authUser!=null) {
				logError.setUserId(authUser.getId());
			}
			logErrorMapper.insertSelective(logError);
			if (UtilFuns.isNotEmpty(ex.getMessage())) {
				message=ex.getMessage();
			}else {
				message=ex.toString();
			}
			request.setAttribute("errorInfo", message);
			//如果不是异步请求
			if (!WebUtil.isAjaxRequest(request)) {
				//如果是shiro身份认证异常,一律打回登录页面
				if (ex instanceof AuthenticationException) {
					request.getRequestDispatcher("/admin/login.ftl").forward(
							request, response);
				} else if (ex instanceof AuthorizationException) {
					// 如果抛出的是shiro授权的异常时,一律跳转到unauthorized.jsp页面
					request.getRequestDispatcher("/unauthorized.html").forward(request,response);
				} else {
					//未知异常,一律跳转到错误页面
					viewName = "error";
				}
			}else {
				//异步请求 ajax调用服务器端,服务端代码出错了,必须将response.setStatus(500),ajax.error方法才能捕获到
				if (UtilFuns.isNotEmpty(ex.getMessage())) {
					response.setStatus(500);
					WebUtil.write(response, ex.getMessage());
				}else {
					response.setStatus(500);
					WebUtil.write(response, ex);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return new ModelAndView(viewName);
	}
	
	/**
	 * 获得当前操作人对那个模块进行了具体操作
	 * @param object 参数名称
	 * @param typeName 操作类型
	 * @return
	 */
	public String getOperationContent(Class<?> object,String typeName) {
		StringBuilder sb=new StringBuilder();
		//获得当参数全路径类名
		String className=object.getName();
		//截取字符串获得类名
		className=className.substring(className.lastIndexOf(".")+1);
		sb.append(className+" 属性名和值 {");
		Method[] methods=object.getDeclaredMethods();
		for (Method method : methods) {
			Object reflectVal=null;
			//只调用get的方法
			if (method.getName().indexOf("get")>=0) {
				try {
					//调用get方法得到该方法的返回值
					reflectVal=method.invoke(object);
					if (reflectVal==null) {
						//如果没有返回值则继续循环
						continue;
					}
				} catch (Exception e) {
					continue;
				}
				//得到方法名称并去掉get在转换为小写
				String methodName=method.getName().substring(3).toLowerCase();
				//将值追加到字符串中
				sb.append(" "+methodName+":"+reflectVal+",");
			}
		}
		return UtilFuns.cutLastStrPre(sb.toString(), ",")+"}";
	}
}
