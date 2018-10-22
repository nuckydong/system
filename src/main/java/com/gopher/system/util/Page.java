package com.gopher.system.util;

import java.util.List;

public class Page<T> {
	/**
	 * 最大页容
	 */
	public static final int MAX_PAGE_SIZE = 100; 
	/**
	 * 默认页容
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;

	private List<T> list;

	private T query;

	private int pageSize = DEFAULT_PAGE_SIZE;

	private int pageNumber = 1;

	private int totalCount;

	private int totalPage;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public T getQuery() {
		return query;
	}

	public void setQuery(T query) {
		this.query = query;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		pageSize = pageSize > MAX_PAGE_SIZE ? pageSize : pageSize;
		pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE:pageSize;
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		totalPage = pageSize <= 0 ? 0 : (totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);
		return totalPage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Page [list=");
		builder.append(list);
		builder.append(", query=");
		builder.append(query);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", pageNumber=");
		builder.append(pageNumber);
		builder.append(", totalCount=");
		builder.append(totalCount);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) {
		Page<String> page = new Page<>();
		page.setPageSize(0);
		page.setTotalCount(101);
		System.out.println(page.getTotalPage());
	}
}
