package org.zjw.blog.deal.permission.dao;

import org.springframework.stereotype.Repository;
import org.zjw.blog.base.common.dao.BaseDao;
import org.zjw.blog.deal.permission.entity.Menu;

import java.util.List;
import java.util.Map;
@Repository
public interface MenuMapper extends BaseDao<Menu>{

	/**
	 * 根据多个menuId查询
	 * @param queryMap
	 * @return
	 */
	List<Menu> selectByMenuIds(Map<String, Object> queryMap);
	
	/**
	 * 根据父id查询
	 * @param parentId
	 * @return
	 */
	List<Menu> selectChildrenByParentId(Object parentId);
	
	/**
	 * 更新状态
	 * @param paramMap
	 * @return
	 */
	int updateState(Map<String, Object> paramMap);

}