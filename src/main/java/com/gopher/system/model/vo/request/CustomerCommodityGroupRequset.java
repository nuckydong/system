package com.gopher.system.model.vo.request;

public class CustomerCommodityGroupRequset {
	private int id;

	private String name;

	private int customerId;

	private int sort;
	/**
	 * 当前分组下的商品ID 多个,分割
	 */
	private String commodityIds;
	
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getCommodityIds() {
		return commodityIds;
	}

	public void setCommodityIds(String commodityIds) {
		this.commodityIds = commodityIds;
	}

}
