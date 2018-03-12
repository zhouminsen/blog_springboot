package org.zjw.blog.lucene;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.junit.Test;
import org.zjw.blog.BaseTest;
import org.zjw.blog.base.lucene.BlogLucene;
import org.zjw.blog.base.vo.blog.BlogLuceneVo;
import org.zjw.blog.base.vo.blog.BlogVo;
import org.zjw.blog.deal.blog.entity.Blog;
import org.zjw.blog.deal.blog.entity.BlogComment;
import org.zjw.blog.deal.blog.service.BlogService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

public class BlogLuceneTest  extends BaseTest {
	
	@Resource
	private BlogService blogService;
	
	@Resource
	private BlogLucene blogLucene;
	/**
	 * 添加索引
	 * @throws IOException
	 */
	@Test
	public void addIndex() throws IOException {
		List<Blog> blogList = blogService.getAll();
		System.out.println(blogList.size());
		for (Blog tBlog : blogList) {
			blogLucene.addIndex(tBlog);
		}
		System.out.println("添加索引ok");
	}

	/**
	 * 删除索引
	 * @throws IOException
	 */
	@Test
	public void deleteIndex() throws IOException {
		List<Blog> tBlogs = blogService.getAll();
		for (Blog tBlog : tBlogs) {
			blogLucene.deleteIndex(tBlog);
		}
		blogLucene.deleteIndex(new Blog(92));
		System.out.println("ok");
	}

	/**
	 * 查询索引
	 * @throws IOException
	 * @throws ParseException
	 * @throws InvalidTokenOffsetsException
	 */
	@Test
	public void searchIndex() throws IOException, ParseException,
			InvalidTokenOffsetsException {
		List<BlogLuceneVo> LuceneVoList = blogLucene.getIndexSearcher("周", 0, 10);
		System.out.println(LuceneVoList.size());
		int totalCount = blogLucene.getCountByCondition("周");
		for (BlogLuceneVo tBlogLuceneVo : LuceneVoList) {
			System.out.println(tBlogLuceneVo);
		}
		System.out.println(totalCount);
	}

	/**
	 * 根据id获得blogVo的评论
	 */
	@Test
	public void getVoById() {
		BlogVo blogVo = blogService.getVoById(39);
		List<BlogComment> Comments = blogVo.getCommentList();
		System.out.println(Comments.size());
		for (BlogComment tComment : Comments) {
			System.out.println(tComment.toString());
		}
	}
}
