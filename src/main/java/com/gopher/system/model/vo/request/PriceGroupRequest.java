package com.gopher.system.model.vo.request;

public class PriceGroupRequest {
	
	private int id;
	
	private String name;
	
	private String number;
	
	private String remark;
	/**
	 * @see com.gopher.system.model.CommodityPrice
	 */
	private String commodityPriceListJson;
	
	
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCommodityPriceListJson() {
		return commodityPriceListJson;
	}
	public void setCommodityPriceListJson(String commodityPriceListJson) {
		this.commodityPriceListJson = commodityPriceListJson;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

}
