
package org.zjw.blog.deal.blog.service;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.zjw.blog.base.vo.blog.BlogLuceneVo;
import org.zjw.blog.base.vo.blog.BlogVo;
import org.zjw.blog.deal.blog.entity.Blog;
import org.zjw.blog.util.page.Page;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BlogService {
	
	
	/**
	 * 查旭首页博客列表
	 * @param queryMap 查询条件
	 * @return
	 */
	Page<Blog> getPageByCondition(Map<String, Object> queryMap);
	  //
	/**
	 * 查询日期下当前博客数量
	 * @return
	 */
	List<Map<String, Object>> getDateCountByReleaseDate();

	int getCountByCondition(Map<String, Object> queryMap);

	/**
	 * 获得全部的博客
	 * @return
	 */
	List<Blog> getAll();

	/**
	 * 根据关键字从lucene中搜索
	 * @param queryMap
	 * @return
	 * @throws InvalidTokenOffsetsException 
	 * @throws ParseException 
	 * @throws IOException 
	 */
	Page<BlogLuceneVo> getPageBykeyword(Map<String, Object> queryMap) throws IOException, ParseException, InvalidTokenOffsetsException;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	BlogVo getVoById(Integer id);

	/**
	 * 多条件查询给后台用
	 * @param queryMap
	 * @return
	 */
	Page<BlogVo> getPageByConditionToBack(Map<String, Object> queryMap);

	/**
	 * 逻辑删除
	 * @param idArray 博文数组
	 * @return
	 */
	int deleteLogicBatch(String[] idArray);
	
	/**
	 * 复原批数据
	 * @param idArray 博文id数组
	 * @param userId 
	 * @return
	 */
	int restoreBatch(String[] idArray, Integer userId);
	
	/**
	 * 根据博文idd查询
	 * @param id
	 * @return
	 */
	Blog getById(Integer id);

	/**
	 * 修改博文
	 * @param blog 
	 * @return
	 */
	int modify(Blog blog);
	
	/**
	 * 保存博文
	 * <br>@Transactional(propagation=Propagation.NOT_SUPPORTED)指定不支持事务
	 * @param blog
	 * @return
	 */
	//@Transactional(propagation=Propagation.NOT_SUPPORTED)
	int save(Blog blog);
	
}
