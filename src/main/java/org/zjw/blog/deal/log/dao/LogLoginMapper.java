package org.zjw.blog.deal.log.dao;

import org.springframework.stereotype.Repository;
import org.zjw.blog.base.common.dao.BaseDao;
import org.zjw.blog.deal.log.entity.LogLogin;

import java.util.List;

/**
 * 
 * @author 周家伟
 * @date 2016-7-18
 * @description 登录日志Mapper
 */
@Repository
public interface LogLoginMapper extends BaseDao<LogLogin>{
	/**
	 * 根据ip地址查询login日志
	 * 
	 * @param ip
	 * @return
	 */
	List<LogLogin> selectByIp(String ip);

}