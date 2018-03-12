package org.zjw.blog.deal.permission.dao;


import org.springframework.stereotype.Repository;
import org.zjw.blog.base.common.dao.BaseDao;
import org.zjw.blog.deal.permission.entity.Role;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleMapper extends BaseDao<Role> {
	/**
	 * 根据用户id查询
	 * @param queryMap
	 * @return
	 */
	List<Role> selectByUserId(Map<?, ?> queryMap);
}