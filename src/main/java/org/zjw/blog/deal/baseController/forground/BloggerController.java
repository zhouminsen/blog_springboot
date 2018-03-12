package org.zjw.blog.deal.baseController.forground;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.zjw.blog.base.common.controller.BaseController;
import org.zjw.blog.deal.blog.entity.Blogger;
import org.zjw.blog.deal.blog.service.BloggerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blogger")
public class BloggerController extends BaseController {
	
	@Resource
	private BloggerService bloggerService;
	
	@RequestMapping("/aboutMe")
	public String aboutMe(HttpServletRequest request,Integer id) {
		Blogger blogger=bloggerService.getById(1);
		request.setAttribute("blogger", blogger);
		request.setAttribute("displayPage", "/foreground/blogger/info.html");
		request.setAttribute("title", "关于博主");
		return "foreground/main";
	}
	
}