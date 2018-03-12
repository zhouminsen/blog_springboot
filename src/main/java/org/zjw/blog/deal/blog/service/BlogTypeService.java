
package org.zjw.blog.deal.blog.service;

import java.util.List;
import java.util.Map;

import org.zjw.blog.base.vo.blog.BlogTypeVo;
import org.zjw.blog.deal.blog.entity.BlogType;
import org.zjw.blog.util.page.Page;

public interface BlogTypeService {
	
	/**
	 * 获得博客类型以及该类型下博客的数量
	 * @return
	 */
	List<BlogTypeVo> getBlogCountByType();

	/**
	 * 多条件查询分页对象VO
	 * @param queryMap
	 * @return
	 */
	Page<BlogTypeVo> getPageVoByCondition(Map<String, Object> queryMap);

	/**
	 * 修改
	 * @param blogType
	 * @return
	 */
	int modify(BlogType blogType);
	
	/**
	 * 保存
	 * @param blogType
	 * @return
	 */
	int save(BlogType blogType);

	/**
	 * 逻辑删除
	 * @param idArray
	 * @return
	 */
	int deleteLogic(String[] idArray);
}
