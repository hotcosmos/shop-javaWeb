package com.hotcosmos.domain;

import java.util.List;

/**
 * 列表分页
 * 
 * @author Administrator
 * @date 2018年5月17日
 */
public class PageBean<T> {

	

	private int currentPage; // 当前页
	private int currentCount; // 当前页显示条数
	private int totalCount; // 此分类总条数
	private int totalPage; // 此分类总页数
	private List<T> list;  //商品信息

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
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

}
