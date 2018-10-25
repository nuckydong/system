package com.gopher.system.model;

public class Commodity extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6330664052557570603L;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 相同名称的商品 可能有不同的种类(等级)
	 */
	private int commodityTypeId;
	/**
	 * 商品默认价格 新入客户未特别定价,默认此价格
	 */
	private int price;
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getCommodityTypeId() {
		return commodityTypeId;
	}
	public void setCommodityTypeId(int commodityTypeId) {
		this.commodityTypeId = commodityTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
