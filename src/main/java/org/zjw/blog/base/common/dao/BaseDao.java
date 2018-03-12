package org.zjw.blog.base.common.dao;

import java.util.List;
import java.util.Map;
public interface BaseDao<T>  {
	
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	T selectByPrimaryKey(Object id);
	
	/**
	 * 查询全部
	 * @return
	 */
	List<T> selectAll();
	
	/**
	 * 查询全部数据记录数
	 * @return
	 */
	int selectCountAll();

	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Object id);
	
	/**
	 * 逻辑批量删除
	 * @param 主键集合ids
	 * @return
	 */
	int deleteLogicBatch(Object ids);
	
	
	/**
	 * 逻辑删除
	 * @param 主键id
	 * @return
	 */
	int deleteLogic(Object id);
	
	/**
	 * 复原数据
	 * @param id
	 * @return
	 */
	int restore(Map<?, ?> paramMap);
	
	/**
	 * 复原批数据
	 * @param idArray
	 * @return
	 */
	int restoreBatch(Map<?, ?> paramMap);

	/**
	 * 插入
	 * @param record
	 * @return
	 */
	int insert(T record);

	/**
	 * 根据参数选择性插入
	 * @param record
	 * @return
	 */
	int insertSelective(T record);

	/**
	 * 根据参数选择性更新
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(T record);

	/**
	 * 更新
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(T record);

	/**
	 * 根据多条件查询数据记录数
	 * @param queryMap
	 * @return
	 */
	int selectCountByCondition(Map<?, ?> queryMap);
	
	/**
	 * 根据多条件查询数据
	 * @param queryMap
	 * @return
	 */
	List<T> selectByCondition(Map<?, ?> queryMap);

	/**
	 * 批量删除
	 * @return
     */
	int deleteBatch(Object ids);
}
