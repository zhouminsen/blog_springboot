package org.zjw.blog.deal.blog.dao;

import org.springframework.stereotype.Repository;
import org.zjw.blog.base.common.dao.BaseDao;
import org.zjw.blog.base.vo.blog.BlogTypeVo;
import org.zjw.blog.deal.blog.entity.BlogType;

import java.util.List;
import java.util.Map;
@Repository
public interface BlogTypeMapper extends BaseDao<BlogType>{
    /**
     * 查询博客类型以及该类型下博客的数量 
     * @return
     */
	List<BlogTypeVo> selectBlogCountByType();
	
	/**
	 * 多条件查询VO
	 * @param queryMap
	 * @return
	 */
	List<BlogTypeVo> selectVoByCondition(Map<String, Object> queryMap);

	/**
	 * 多条件查询VO的记录数
	 * @param queryMap
	 * @return
	 */
	int selectVoCountByCondition(Map<String, Object> queryMap);

}