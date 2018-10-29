package com.gopher.system.model;

/**
 * 订单对应的商品
 * 
 * @author dongyangyang
 *
 */
public class OrderCommodity {
	
	private int id;
	/**
	 * 订单ID
	 */
	private int orderId;
	/**
	 * 商品ID
	 */
	private int commodityId;
	/**
	 * 订单产生时 商品的价格
	 */
	private int price;
	/**
	 * 计价单位
	 */
	private String unit;
	
	private int amount;
	

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


}
