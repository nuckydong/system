package com.gopher.system.model.vo.response;

import java.util.Date;
import java.util.List;

public class OrderDetailResponse {
	private String number;
	private int id;
	private Date createTime;
	private Date updateTime;
	private List<CommodityResponse> commodityList;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public List<CommodityResponse> getCommodityList() {
		return commodityList;
	}
	public void setCommodityList(List<CommodityResponse> commodityList) {
		this.commodityList = commodityList;
	}

}
