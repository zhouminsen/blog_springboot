package org.zjw.blog.deal.permission.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.zjw.blog.deal.permission.dao.OperationMapper;
import org.zjw.blog.deal.permission.entity.Operation;
import org.zjw.blog.deal.permission.service.OperationService;
import org.zjw.blog.util.page.Page;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 周家伟
 * @date 2016-7-19
 *
 */
@Service("operationService")
public class OperationServiceImpl implements OperationService {

	@Resource
	private OperationMapper operationMapper;

	public Operation getById(int id) {
		return operationMapper.selectByPrimaryKey(id);
	}

	public List<Operation> getByMenuId(Integer menuId) {
		return operationMapper.selectByMenuId(menuId);
	}

	public Page<Operation> getPageByMenuId(Integer menuId) {
		Page<Operation> page=new Page<Operation>();
		List<Operation> operationList=operationMapper.selectByMenuId(menuId);
		int totalCount=operationMapper.selectCountByMenuId(menuId);
		page.setTotalCount(totalCount);
		page.setResultData(operationList);
		return page;
	}

	public int modify(Operation operation) {
		return operationMapper.updateByPrimaryKeySelective(operation);
	}

	public int save(Operation operation) {
		return operationMapper.insertSelective(operation);
	}

	public int delete(String[] idArray) {
		List<Integer> ids=new ArrayList<Integer>();
		for (int i = 0; i < idArray.length; i++) {
			ids.add(Integer.parseInt(idArray[i]));
		}
		return operationMapper.deleteLogicBatch(ids);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
