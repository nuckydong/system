package com.gopher.system.model.vo.request;

import java.util.List;

import com.gopher.system.model.OrderCommodity;

public class OrderRequst {
	
	private int id;
	
	private String commodityListJson;
	
	private List<OrderCommodity> comodityList;

	public List<OrderCommodity> getComodityList() {
		return comodityList;
	}

	public void setComodityList(List<OrderCommodity> comodityList) {
		this.comodityList = comodityList;
	}

	public String getCommodityListJson() {
		return commodityListJson;
	}

	public void setCommodityListJson(String commodityListJson) {
		this.commodityListJson = commodityListJson;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    
	
}
