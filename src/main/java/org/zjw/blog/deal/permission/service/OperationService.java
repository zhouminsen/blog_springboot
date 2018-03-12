
package org.zjw.blog.deal.permission.service;

import java.util.List;

import org.zjw.blog.deal.permission.entity.Operation;
import org.zjw.blog.util.page.Page;

public interface OperationService {

	/**
	 * 根据id查询
	 * @param parseInt
	 * @return
	 */
	Operation getById(int id);
	
	/**
	 * 根据菜单id获得操作权限
	 * @param menuId
	 * @return
	 */
	List<Operation> getByMenuId(Integer menuId);
	
	/**
	 * 根据菜单id获得分页对象
	 * @param menuId
	 * @return
	 */
	Page<Operation> getPageByMenuId(Integer menuId);
	
	/**
	 * 修改操作
	 * @param menu
	 * @return
	 */
	int modify(Operation operation);

	/**
	 * 添加
	 * @param operation
	 * @return
	 */
	int save(Operation operation);
	
	/**
	 * 逻辑删除数据
	 * @param idArray
	 * @return
	 */
	int delete(String[] idArray);
	
}
