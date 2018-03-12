
package org.zjw.blog.deal.blog.service;


import org.zjw.blog.deal.blog.entity.Blogger;

public interface BloggerService {
	
	/**
	 * 根据id查旭
	 * @param id
	 * @return
	 */
	Blogger getById(Integer id);
	
	/**
	 * 修改密码
	 * @param newPassword 新密码
	 * @param blogger 用户
	 * @return
	 */
	int modifyPassword(String newPassword, Blogger blogger);

	/**
	 * 修改用户
	 * @param blogger
	 * @param isChangePic
     * @return
	 */
	int modify(Blogger blogger, Integer isChangePic);
	
}
