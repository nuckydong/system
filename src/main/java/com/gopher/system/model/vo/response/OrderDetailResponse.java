package com.gopher.system.model.vo.response;

import java.util.List;

public class OrderDetailResponse {
	private String number;
	private String remark;
	private String company;
	private String priceNumber;
	private String phone;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPriceNumber() {
		return priceNumber;
	}
	public void setPriceNumber(String priceNumber) {
		this.priceNumber = priceNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	private int id;
	private List<CommodityResponse> commodityList;
	private long createTime;
	private long updateTime;
	private String updateUser;
	private String createUser;
	
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public List<CommodityResponse> getCommodityList() {
		return commodityList;
	}
	public void setCommodityList(List<CommodityResponse> commodityList) {
		this.commodityList = commodityList;
	}

}
