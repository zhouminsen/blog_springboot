
package org.zjw.blog.deal.permission.service;

import org.zjw.blog.deal.permission.entity.Menu;

import java.util.List;
import java.util.Map;


/**
 * 
 * @author 周家伟
 * @date 2016-7-20
 */
public interface MenuService {
	
	/**
	 * 得到树形菜单
	 * @param queryMap
	 * @return
	 * @throws Exception
	 */
	String getTree(Map<String, Object> queryMap) throws Exception;
	
	/**
	 * 获得用户用的有树形菜单
	 * @param queryMap
	 * @return
	 */
	String getTreeToModify(Map<String, Object> queryMap);
	
	/**
	 * 根据父id获得子菜单
	 * @param id
	 * @return
	 */
	List<Menu> getChildrenByParentId(Integer id);
	
	/**
	 * 根据id获得
	 * @param id
	 * @return
	 */
	Menu getById(int id);

	/**
	 * 获得treeGrid
	 * @param queryMap
	 * @return
	 */
	String getTreeGrid(Map<String, Object> queryMap);
	
	/**w
	 * 添加菜单
	 * @param menu
	 * @return
	 */
	int save(Menu menu);
	
	/**
	 * 修改菜单
	 * @param menu
	 * @return
	 */
	int modify(Menu menu);
	
	/**
	 * 删除菜单
	 * @param menuId
	 * @param parentId 
	 * @return
	 */
	int delete(Integer menuId, Integer parentId);
	
	String getTree2(Map<String, Object> queryMap) throws Exception;

	/**
	 * 根据父id获得树形菜单列表
	 * @param parentId
	 * @return
     */
	String getTreeGridByParentId(Integer parentId);
}
