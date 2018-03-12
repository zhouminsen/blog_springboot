
package org.zjw.blog.deal.link.service;

import java.util.List;
import java.util.Map;

import org.zjw.blog.deal.link.entity.Link;
import org.zjw.blog.util.page.Page;

public interface LinkService {
	
	/**
	 * 获得全部
	 * @return
	 */
	List<Link> getAll();
	
	/**
	 * 获得分页对象
	 * @return
	 */
	Page<Link> getPage(Map<String, Object> queryMap);
	
	/**
	 * 逻辑删除
	 * @param arrayId
	 * @return
	 */
	int deleteLogic(String[] arrayId);

	/**
	 * 添加
	 * @param link
	 * @return
	 */
	int save(Link link);
	
	/**
	 * 修改
	 * @param link
	 * @return
	 */
	int modify(Link link);

	
	
}
