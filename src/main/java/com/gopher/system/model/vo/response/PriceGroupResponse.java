package com.gopher.system.model.vo.response;

import java.util.List;

public class PriceGroupResponse {
	private String name;
	private String number;
	private int id;
	private String remark;
	private List<CommodityPriceResponse> commodityPriceList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<CommodityPriceResponse> getCommodityPriceList() {
		return commodityPriceList;
	}
	public void setCommodityPriceList(List<CommodityPriceResponse> commodityPriceList) {
		this.commodityPriceList = commodityPriceList;
	}

}
