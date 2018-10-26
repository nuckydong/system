package com.gopher.system.model.vo.response;

import java.util.List;

import com.gopher.system.model.Commodity;

public class OrderResponse {
	private int id;
	private String number;
	private List<Commodity> commodityList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public List<Commodity> getCommodityList() {
		return commodityList;
	}
	public void setCommodityList(List<Commodity> commodityList) {
		this.commodityList = commodityList;
	} 
    
}
