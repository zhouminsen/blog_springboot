package org.zjw.blog.util.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable{
	private List<T> resultData = new ArrayList<T>();
	/**
	 *  第几页
	 */
	private int currentPage;
	/**
	 *  每页记录数
	 */
	private int rows; 
	/**
	 *  起始索引
	 */
	private int start; 
	/**
	 * 总记录数
	 */
	private int totalCount;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 分页地址
	 */
	private String url;
	/**
	 * 分页是否成功 1:成功 0:失败
	 */
	private int success=1;
	
	
	public Page() {
		super();
	}

	public Page(int currentPage, int rows) {
		super();
		this.currentPage = currentPage;
		this.rows = rows;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStart() {
		return (currentPage - 1) * rows;
	}

	public List<T> getResultData() {
		return resultData;
	}

	public void setResultData(List<T> resultDate) {
		this.resultData = resultDate;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}
	

}
