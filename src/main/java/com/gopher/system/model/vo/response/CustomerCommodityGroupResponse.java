package com.gopher.system.model.vo.response;

import java.util.List;

import com.gopher.system.model.Commodity;

public class CustomerCommodityGroupResponse {
	private String name;
	private String remark;
	private int sort;
	private int customerId;
	private int customerName;
	private List<Commodity> commodityList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getCustomerName() {
		return customerName;
	}
	public void setCustomerName(int customerName) {
		this.customerName = customerName;
	}
	public List<Commodity> getCommodityList() {
		return commodityList;
	}
	public void setCommodityList(List<Commodity> commodityList) {
		this.commodityList = commodityList;
	}
	
}
