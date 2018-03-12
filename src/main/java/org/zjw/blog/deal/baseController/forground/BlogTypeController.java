package org.zjw.blog.deal.baseController.forground;

import javax.annotation.Resource;

import org.zjw.blog.base.common.controller.BaseController;
import org.zjw.blog.deal.blog.service.BlogTypeService;
import org.springframework.stereotype.Controller;

@Controller
public class BlogTypeController extends BaseController {
	
	@Resource
	private BlogTypeService blogTypeService;
	
}