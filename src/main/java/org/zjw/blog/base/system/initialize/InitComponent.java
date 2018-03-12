package org.zjw.blog.base.system.initialize;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.zjw.blog.base.vo.blog.BlogTypeVo;
import org.zjw.blog.deal.blog.entity.Blogger;
import org.zjw.blog.deal.blog.service.BlogService;
import org.zjw.blog.deal.blog.service.BlogTypeService;
import org.zjw.blog.deal.blog.service.BloggerService;
import org.zjw.blog.deal.link.entity.Link;
import org.zjw.blog.deal.link.service.LinkService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;

/**
 * 注册ServletContextListener(web.xml中配置)监听器,便于tomcat启动时就加载该类
 * 注册ApplicationContextAware,便于获取spring容器,注:如果想要获得spring容器,必须先在spring的配置
 * 文件中申明该类或是在该类上面打个注解符号
 * 
 * 初始化前端显示页面所需的数据
 * @author Administrator
 */
@Component
public class InitComponent implements ServletContextListener,
		ApplicationContextAware {

	private static ApplicationContext app;
	private static ServletContext appContext;

	// 注入spring容器
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		app = applicationContext;
	}

	// 获得servlet容器并初始化数据
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("什么时候穿件我的啊");
		 appContext = sce.getServletContext();
		 appContext.setAttribute("init", 1);
		BlogService blogService = (BlogService) app.getBean("blogService");
		// 查询日期下当前博客数量
		List<Map<String, Object>> result = blogService.getDateCountByReleaseDate();
		appContext.setAttribute("blogCountList", result);
		BlogTypeService typeService = (BlogTypeService) app.getBean("blogTypeService");
		// 查询博客类型以及数量
		List<BlogTypeVo> blogTypeVoList = typeService.getBlogCountByType();
		appContext.setAttribute("typeList", blogTypeVoList);
		BloggerService bloggerService = (BloggerService) app.getBean("bloggerService");
		// 查询博主信息
		Blogger blogger = bloggerService.getById(1);
		appContext.setAttribute("blogger", blogger);
		// 读取配置的上下文路径并设置到application中
		appContext.setAttribute("cxt", "/blog");
		LinkService linkService = (LinkService) app.getBean("linkService");
		// 查询连接
		List<Link> linkList = linkService.getAll();
		appContext.setAttribute("linkList", linkList);
	}

	public static ServletContext getAppServletContext() {
		return appContext;
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO AutoLoginServlet-generated method stub
	}

}
