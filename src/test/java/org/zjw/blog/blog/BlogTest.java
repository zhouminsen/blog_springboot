package org.zjw.blog.blog;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zjw.blog.BaseTest;
import org.zjw.blog.base.vo.blog.BlogVo;
import org.zjw.blog.deal.blog.entity.Blog;
import org.zjw.blog.deal.blog.service.BlogService;
import org.zjw.blog.util.page.Page;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试blog
 * @author 周家伟
 * @date 2016-7-29
 */
public class BlogTest  extends BaseTest{
	
	@Autowired
	private BlogService blogService;
	
	/**
	 * 复原批数据
	 */
	@Test
	public void restoreBatch() {
		String[] ids={};
		int line=blogService.restoreBatch(ids,1);
		System.out.println(line);
	}
	
	/**
	 * 查询博文给后台用
	 */
	@Test
	public void getPageByConditionToBack() {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("order", "ddd");
		if (queryMap.get("order")!=null) {
			queryMap.put("sort", "releaseDate");
			queryMap.put("order", "asc");
		}
		Page<BlogVo> page=blogService.getPageByConditionToBack(queryMap);
		System.out.println(page.getTotalCount());
		for (BlogVo item : page.getResultData()) {
			System.out.println(item.getReleaseDateStr());
			System.out.println(item.getBlogType());
		}
	}
	
	/**
	 * 插入数据
	 */
	@Test
	public void save() {
		Blog blog=new Blog();
		blog.setKeyWord("haha");
		blog.setReleaseDate(new Date());
		blog.setSummary("dsdsd");
		blog.setContent("dsadasdasd");
		int line=blogService.save(blog);
		System.out.println(blog);
	}

}
