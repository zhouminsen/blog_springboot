package org.zjw.blog.deal.log.controller;

import javax.annotation.Resource;

import org.zjw.blog.base.common.controller.BaseController;
import org.zjw.blog.deal.log.service.LogErrorService;
import org.springframework.stereotype.Controller;

@Controller
public class LogErrorController extends BaseController {
	
	@Resource
	private LogErrorService logErrorService;
	
}