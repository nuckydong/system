package com.gopher.system.model.vo.response;

public class CommodityResponse {
	
     private int id;
     
     private String name;
     
     private String commodityTypeName;
     
     private int commodityTypeId;
     
     private String unit;
     
     private int price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommodityTypeName() {
		return commodityTypeName;
	}

	public void setCommodityTypeName(String commodityTypeName) {
		this.commodityTypeName = commodityTypeName;
	}

	public int getCommodityTypeId() {
		return commodityTypeId;
	}

	public void setCommodityTypeId(int commodityTypeId) {
		this.commodityTypeId = commodityTypeId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
     
     

}
