package org.zjw.blog.deal.permission.dao;


import org.springframework.stereotype.Repository;
import org.zjw.blog.base.common.dao.BaseDao;
import org.zjw.blog.deal.permission.entity.Operation;

import java.util.List;

@Repository
public interface OperationMapper extends BaseDao<Operation> {
	
	/**
	 * 根据菜单id获得操作权限
	 * @param menuId
	 * @return
	 */
	List<Operation> selectByMenuId(Integer menuId);
	
	/**
	 * 根据菜单id查询记录数
	 * @param menuId
	 * @return
	 */
	int selectCountByMenuId(Integer menuId);
	
}