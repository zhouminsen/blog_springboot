package org.zjw.blog.blog;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.blog.BaseTest;
import org.zjw.blog.base.vo.blog.BlogCommentVo;
import org.zjw.blog.deal.blog.service.BlogCommentService;
import org.zjw.blog.util.page.Page;

import java.util.HashMap;
import java.util.Map;

public class BlogCommentTest  extends BaseTest {

	@Autowired
	private BlogCommentService blogCommentService;


	@Test
	public void getVoByCondition() {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("userId", 1);
		Page<BlogCommentVo> page=blogCommentService.getPageVoByCondition(queryMap);
		System.out.println(page.getTotalCount());
		for (BlogCommentVo item : page.getResultData()) {
			System.out.println(item);
		}
	}

}
