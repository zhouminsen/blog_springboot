package org.zjw.blog.deal.log.dao;


import org.springframework.stereotype.Repository;
import org.zjw.blog.base.common.dao.BaseDao;
import org.zjw.blog.base.vo.log.LogOperationVo;
import org.zjw.blog.deal.log.entity.LogOperation;

import java.util.List;
import java.util.Map;
@Repository
public interface LogOperationMapper extends BaseDao<LogOperation>{
	/**
	 * 多条件查询vo
	 * @param queryMap
	 * @return
	 */
	List<LogOperationVo> selectVoByCondition(Map<String, Object> queryMap);
	
	/**
	 * 多条件查询vo记录数
	 * @param queryMap
	 * @return
	 */
	int selectVoCountByCondition(Map<String, Object> queryMap);
}