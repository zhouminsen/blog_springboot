package org.zjw.blog.deal.blog.dao;

import org.springframework.stereotype.Repository;
import org.zjw.blog.base.common.dao.BaseDao;
import org.zjw.blog.base.vo.blog.BlogVo;
import org.zjw.blog.deal.blog.entity.Blog;

import java.util.List;
import java.util.Map;
@Repository
public interface BlogMapper extends BaseDao<Blog>{

    /**
	 * 查询日期下当前博客数量
	 * @return
	 */
	List<Map<String, Object>> selectDateCountByReleaseDate();
	

	/**
	 * 根据id查询并且获得对象实体属性
	 * @param id
	 * @return
	 */
	Blog selectVoById(Integer id);
	
	/**
	 * 插入博文返回
	 * @param blog
	 * @return
	 */
	int insertSelectiveReturn(Blog blog);
	
	/**
	 * 多条件查询博文
	 * @param queryMap
	 * @return
	 */
	List<BlogVo> selectByConditionToBack(Map<?, ?> queryMap);

	/**
	 * 多条件查询博文记录数
	 * @param queryMap
	 * @return
	 */
	int selectCountByConditionToBack(Map<?, ?> queryMap);
}