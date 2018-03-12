package org.zjw.blog.base.common.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable{
	/**
	 * 创建日期
	 */
	private Date createDate;
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
