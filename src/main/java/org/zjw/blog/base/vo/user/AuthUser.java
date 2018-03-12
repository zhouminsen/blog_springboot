package org.zjw.blog.base.vo.user;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;
import org.zjw.blog.deal.blog.entity.Blogger;
import org.zjw.blog.deal.permission.entity.Menu;
import org.zjw.blog.deal.permission.entity.Operation;
import org.zjw.blog.deal.permission.entity.Role;

import java.util.ArrayList;
import java.util.List;
@Data
public class AuthUser extends BaseEntity {


	private static final long serialVersionUID = 8435307745380523736L;
	private Integer id;

	private String username;

	private String password;


	private Role role;

	/**

	 * 当前登录的用户
	 */
	private Blogger blogger;
	/**
	 * 拥有的权限
	 */
	private List<Operation> operationList = new ArrayList<Operation>();
	/**
	 * 拥有的菜单
	 */
	private List<Menu> menuList = new ArrayList<Menu>();


}
