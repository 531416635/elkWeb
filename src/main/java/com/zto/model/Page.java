package com.zto.model;

import java.util.List;

public class Page {
	private int records;
	private int total;
	private int page;
	private int pageSize;
	private int startIndex;
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	@Override
	public String toString() {
		return "Page [records=" + records + ", total=" + total + ", page="
				+ page + ", pageSize=" + pageSize + ", startIndex="
				+ startIndex + "]";
	}

}
