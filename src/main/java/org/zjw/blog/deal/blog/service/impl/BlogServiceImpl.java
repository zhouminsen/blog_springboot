package org.zjw.blog.deal.blog.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.zjw.blog.base.lucene.BlogLucene;
import org.zjw.blog.base.vo.blog.BlogLuceneVo;
import org.zjw.blog.base.vo.blog.BlogVo;
import org.zjw.blog.deal.blog.dao.BlogCommentMapper;
import org.zjw.blog.deal.blog.dao.BlogMapper;
import org.zjw.blog.deal.blog.dao.BlogTypeMapper;
import org.zjw.blog.deal.blog.entity.Blog;
import org.zjw.blog.deal.blog.service.BlogService;
import org.zjw.blog.util.UtilFuns;
import org.zjw.blog.util.page.Page;
import org.zjw.blog.util.page.PageUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

	@Resource
	private BlogMapper blogMapper;

	@Resource
	private BlogTypeMapper blogTypeMapper;
	
	@Resource
	private BlogCommentMapper blogCommentMapper;
	
	@Resource
	private BlogLucene blogLucene;

	public Page<Blog> getPageByCondition(Map<String, Object> queryMap) {
		PageUtil.isEmptySetValue(queryMap);
		Page<Blog> page = new Page<Blog>(
				(Integer)queryMap.get("currentPage"),
				(Integer)queryMap.get("rows"));
		queryMap.put("start", page.getStart());
		List<Blog> blogList = blogMapper.selectByCondition(queryMap);
		for (Blog tBlog : blogList) {
			// 解析文本
			Document doc = Jsoup.parse(tBlog.getContent());
			// 按指定格式搜索文本,查询该文本doc中包含图片的文本
			Elements el = doc.select("img[src$=.jpg]");
			for (int i = 0; i < el.size(); i++) {
				if (i <= 2) {
					// 首页显示博客图片数量最多3条
					tBlog.getImgList().add(el.get(i).toString());
				} else {
					break;
				}
			}
		}
		page.setResultData(blogList);
		page.setTotalCount(this.getCountByCondition(queryMap));
		StringBuilder sb = new StringBuilder();
		//拼接分页路径
		if (UtilFuns.isNotEmpty(queryMap.get("releaseDate"))) {
			sb.append("releaseDate=" + queryMap.get("releaseDate") + "&");
		}
		if (UtilFuns.isNotEmpty(queryMap.get("typeId"))) {
			sb.append("typeId=" + queryMap.get("typeId") + "&");
		}
		String url = "/blog";
		page.setUrl(PageUtil.getPagination(url + "/index",
				page.getTotalCount(), page.getCurrentPage(), page.getRows(),
				sb.toString()));
		return page;
	}
	
	public Page<BlogVo> getPageByConditionToBack(Map<String, Object> queryMap) {
		PageUtil.isEmptySetValue(queryMap);
		Page<BlogVo> page = new Page<BlogVo>(
				(Integer)queryMap.get("currentPage"),
				(Integer)queryMap.get("rows"));
		queryMap.put("start", page.getStart());
		List<BlogVo> blogList = blogMapper.selectByConditionToBack(queryMap);
		int totalCount = blogMapper.selectCountByConditionToBack(queryMap);
		page.setTotalCount(totalCount);
		page.setResultData(blogList);
		return page;
	}

	public List<Map<String, Object>> getDateCountByReleaseDate() {
		return blogMapper.selectDateCountByReleaseDate();
	}

	
	public int getCountByCondition(Map<String, Object> queryMap) {

		return blogMapper.selectCountByCondition(queryMap);
	}

	public List<Blog> getAll() {
		return blogMapper.selectAll();
	}

	public Page<BlogLuceneVo> getPageBykeyword(Map<String, Object> queryMap)
			throws IOException, ParseException, InvalidTokenOffsetsException {
		PageUtil.isEmptySetValue(queryMap);
		Page<BlogLuceneVo> page = new Page<BlogLuceneVo>(
				(Integer) queryMap.get("currentPage"),
				(Integer) queryMap.get("rows"));
		String keyword = (String) queryMap.get("keyword");
		// 拿到搜索分页后的数据
		List<BlogLuceneVo> blogLuceneVoList = blogLucene.getIndexSearcher(keyword,
				page.getStart(), page.getRows());
		page.setResultData(blogLuceneVoList);
		// 拿到搜索到的总记录数
		int totalCount = blogLucene.getCountByCondition(keyword);
		page.setTotalCount(totalCount);
		StringBuilder sb = new StringBuilder();
		String url = "/blog";
		sb.append("keyword=" + keyword);
		page.setUrl(PageUtil.getPagination(url + "/blog/search", totalCount,
				page.getCurrentPage(), page.getRows(), sb.toString()));
		return page;
	}

	public BlogVo getVoById(Integer id) {
		Blog blog = blogMapper.selectVoById(id);
		blog.setClickHit(blog.getClickHit() + 1);
		BlogVo blogVo =new BlogVo();
		BeanUtils.copyProperties(blog, blogVo);
		blogVo.setBlogType(blogTypeMapper.selectByPrimaryKey(blog.getTypeId()));
		blogVo.setCommentList(blogCommentMapper.selectByBlogId(blog.getId()));
		blogMapper.updateByPrimaryKeySelective(blog);
		return blogVo;
	}

	public int deleteLogicBatch(String[] idArray) {
		int[] ids=new int[idArray.length];
		for (int i = 0; i < idArray.length; i++) {
			ids[i]=Integer.parseInt(idArray[i]);
		}
		return blogMapper.deleteLogicBatch(ids);
	}

	public int restoreBatch(String[] idArray,Integer userId) {
		int[] ids=new int[idArray.length];
		for (int i = 0; i < idArray.length; i++) {
			ids[i]=Integer.parseInt(idArray[i]);
		}
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("ids", ids);
		paramMap.put("userId", userId);
		return blogMapper.restoreBatch(paramMap);
	}

	public Blog getById(Integer id) {
		return blogMapper.selectByPrimaryKey(id);
	}

	public int modify(Blog blog) {
		return blogMapper.updateByPrimaryKeySelective(blog);
	}

	public int save(Blog blog) {
		return blogMapper.insertSelectiveReturn(blog);
	}
}



















